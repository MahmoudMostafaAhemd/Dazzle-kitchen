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
import com.example.mahmouddiab.dazzlekitchen.adapter.SingleSection.OnActionclick;
import com.example.mahmouddiab.dazzlekitchen.model.Error;
import com.example.mahmouddiab.dazzlekitchen.model.OrderDoneModel;
import com.example.mahmouddiab.dazzlekitchen.model.OrdersModel;
import com.example.mahmouddiab.dazzlekitchen.orders.OrderPresenter;
import com.example.mahmouddiab.dazzlekitchen.orders.OrderView;
import com.example.mahmouddiab.dazzlekitchen.orders.OrdersPresenterImpl;
import com.example.mahmouddiab.dazzlekitchen.userManegment.UserManager;
import com.example.mahmouddiab.dazzlekitchen.utils.App;
import com.example.mahmouddiab.dazzlekitchen.utils.OnDialogClicked;
import com.example.mahmouddiab.dazzlekitchen.utils.Utils;
import com.example.mahmouddiab.dazzlekitchen.utils.WrapContentLinearLayoutManager;
import com.example.mahmouddiab.dazzlekitchen.views.CompleteOrderConfirmation;
import com.example.mahmouddiab.dazzlekitchen.views.ProgressDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class OrdersFragment extends Fragment implements OrderView, OnActionclick, OnDialogClicked {
    private CompleteOrderConfirmation completeOrder;
    private CompleteOrderConfirmation completeOrderConfirmation;
    @BindView(R.id.empty_text)
    TextView emptyText;
    private int id;
    private OrderPresenter orderPresenter;
    private OrdersModel ordersModel;
    private int position;
    private ProgressDialog progressDialog;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private SectionedRecyclerViewAdapter sectionAdapter;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;


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

    public static OrdersFragment getInstance() {
        return new OrdersFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.sectionAdapter = new SectionedRecyclerViewAdapter();
        this.recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getContext()));
        this.recyclerView.setAdapter(this.sectionAdapter);
        this.orderPresenter = new OrdersPresenterImpl(this);
        this.progressDialog = new ProgressDialog(getContext());
        this.progressDialog.show();
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                orderPresenter.getOrders(UserManager.getInstance().getUser().getData().getToken());
            }
        });
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                orderPresenter.onRegisterToken(UserManager.getInstance().getUser().getData().getToken(), App.getClientId(), (task.getResult()).getToken());
            }
        });
    }

    public void onResume() {
        super.onResume();
        orderPresenter.getOrders(UserManager.getInstance().getUser().getData().getToken());
    }

    @Override
    public void onDoneSuccess(OrderDoneModel orderDoneModel) {
        sectionAdapter.removeSection(sectionAdapter.getSectionForPosition(position));
        sectionAdapter.notifyDataSetChanged();
        if (this.sectionAdapter.getItemCount() == 0) {
            this.emptyText.setVisibility(View.VISIBLE);
        } else {
            this.emptyText.setVisibility(View.GONE);
        }
        this.completeOrderConfirmation.dismiss();
    }

    public void onInProgressSuccess(OrderDoneModel orderDoneModel) {
        this.ordersModel.getData().get(this.position).getDishes().get(0).setIsDone("In Progress");
        this.sectionAdapter.notifyDataSetChanged();
    }

    public void onCloseSuccess(OrderDoneModel orderDoneModel) {
        this.completeOrder.dismiss();
        this.sectionAdapter.removeSection(this.sectionAdapter.getSectionForPosition(this.position));
        this.sectionAdapter.notifyDataSetChanged();
        if (this.sectionAdapter.getItemCount() == 0) {
            this.emptyText.setVisibility(View.VISIBLE);
        } else {
            this.emptyText.setVisibility(View.GONE);
        }
    }

    public void onTakenRigestered(OrderDoneModel msg) {
    }

    public void onGetOrderFailed(Error error) {
        this.progressDialog.dismiss();
        Utils.showLongToast(getContext(), error.getTitle());
    }

    public void onDoneClicked(int id, int position) {
        this.id = id;
        this.position = position;
        this.completeOrderConfirmation = new CompleteOrderConfirmation(getContext(), this);
        this.completeOrderConfirmation.show();
    }

    public void onInprogressClicked(int id, int position) {
        this.position = position;
        this.orderPresenter.onInProgressClicked(UserManager.getInstance().getUser().getData().getToken(), id);
    }

    public void onCloseOrder(final int ids, int position) {
        this.position = position;
        this.completeOrder = new CompleteOrderConfirmation(getContext(), new OnDialogClicked() {
            public void onYesClicked() {
                orderPresenter.onCloseClicked(UserManager.getInstance().getUser().getData().getToken(), ids);
            }

            public void onNoClicked() {
                completeOrder.dismiss();
            }
        });
        this.completeOrder.show();
    }

    public void onSubmitWaitingTime(int id, String mins) {
        this.orderPresenter.onSubmitWaitingTime(UserManager.getInstance().getUser().getData().getToken(), id, mins);
    }

    public void onYesClicked() {
        this.orderPresenter.onDoneClicked(UserManager.getInstance().getUser().getData().getToken(), id);
        this.completeOrderConfirmation.dismiss();
    }

    public void onNoClicked() {
        this.completeOrderConfirmation.dismiss();
    }
}
