package com.example.mahmouddiab.dazzlekitchen.home.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mahmouddiab.dazzlekitchen.R;
import com.example.mahmouddiab.dazzlekitchen.adapter.SingleSection;
import com.example.mahmouddiab.dazzlekitchen.model.Error;
import com.example.mahmouddiab.dazzlekitchen.model.OrderDoneModel;
import com.example.mahmouddiab.dazzlekitchen.model.OrdersModel;
import com.example.mahmouddiab.dazzlekitchen.orders.OrderPresenter;
import com.example.mahmouddiab.dazzlekitchen.orders.OrderView;
import com.example.mahmouddiab.dazzlekitchen.orders.OrdersPresenterImpl;
import com.example.mahmouddiab.dazzlekitchen.userManegment.UserManager;
import com.example.mahmouddiab.dazzlekitchen.utils.OnDialogClicked;
import com.example.mahmouddiab.dazzlekitchen.utils.Utils;
import com.example.mahmouddiab.dazzlekitchen.utils.WrapContentLinearLayoutManager;
import com.example.mahmouddiab.dazzlekitchen.views.CompleteOrderConfirmation;
import com.example.mahmouddiab.dazzlekitchen.views.ProgressDialog;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends Fragment implements OrderView, SingleSection.OnActionclick, OnDialogClicked {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.empty_text)
    TextView emptyText;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    private SectionedRecyclerViewAdapter sectionAdapter;
    private OrderPresenter orderPresenter;
    private ProgressDialog progressDialog;
    private int id;
    private CompleteOrderConfirmation completeOrderConfirmation;
    private int position;
    private OrdersModel ordersModel;
    private CompleteOrderConfirmation completeOrder;
    public OrdersFragment() {
        // Required empty public constructor
    }

    public static OrdersFragment getInstance() {
        return new OrdersFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sectionAdapter = new SectionedRecyclerViewAdapter();
        WrapContentLinearLayoutManager layoutManager = new WrapContentLinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(sectionAdapter);
        orderPresenter = new OrdersPresenterImpl(this);
        orderPresenter.getOrders(UserManager.getInstance().getUser().getData().getToken());
        progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                orderPresenter.getOrders(UserManager.getInstance().getUser().getData().getToken());
            }
        });

    }

    @Override
    public void onGetOrderSuccess(OrdersModel ordersModel) {
        sectionAdapter.removeAllSections();
        this.ordersModel = ordersModel;
        progressDialog.dismiss();
        swipeRefreshLayout.setRefreshing(false);
        if (ordersModel.getData().isEmpty()) {
            emptyText.setVisibility(View.VISIBLE);
        } else {
            emptyText.setVisibility(View.GONE);
        }
        for (int i = 0; i < ordersModel.getData().size(); i++) {
            sectionAdapter.addSection(new SingleSection(getContext(), ordersModel.getData().get(i).getDishes(),
                    String.valueOf(ordersModel.getData().get(i).getId()), this));
        }

        sectionAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDoneSuccess(OrderDoneModel orderDoneModel) {

        sectionAdapter.removeSection(sectionAdapter.getSectionForPosition(position));
        sectionAdapter.notifyDataSetChanged();
        completeOrderConfirmation.dismiss();

    }

    @Override
    public void onInProgressSuccess(OrderDoneModel orderDoneModel) {
        ordersModel.getData().get(position).getDishes().get(0).setIsDone("In Progress");
        sectionAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCloseSuccess(OrderDoneModel orderDoneModel) {
        completeOrder.dismiss();
        sectionAdapter.removeSection(sectionAdapter.getSectionForPosition(position));
        sectionAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetOrderFailed(Error error) {
        progressDialog.dismiss();
        Utils.showLongToast(getContext(), error.getTitle());
    }

    @Override
    public void onDoneClicked(int id, int position) {
        this.id = id;
        this.position = position;
        completeOrderConfirmation = new CompleteOrderConfirmation(getContext(), this);
        completeOrderConfirmation.show();
    }

    @Override
    public void onInprogressClicked(int id, int position) {
        this.position = position;
        orderPresenter.onInProgressClicked(UserManager.getInstance().getUser().getData().getToken(), id);
    }

    @Override
    public void onCloseOrder(final int ids, int position) {
        this.position = position;
        completeOrder = new CompleteOrderConfirmation(getContext(), new OnDialogClicked() {
            @Override
            public void onYesClicked() {
                orderPresenter.onCloseClicked(UserManager.getInstance().getUser().getData().getToken(), ids);
            }

            @Override
            public void onNoClicked() {
                completeOrder.dismiss();
            }
        });
        completeOrder.show();
    }

    @Override
    public void onYesClicked() {
        orderPresenter.onDoneClicked(UserManager.getInstance().getUser().getData().getToken(),
                id);
        completeOrderConfirmation.dismiss();
    }

    @Override
    public void onNoClicked() {
        completeOrderConfirmation.dismiss();
    }
}
