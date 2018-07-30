package com.example.mahmouddiab.dazzlekitchen;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mahmouddiab.dazzlekitchen.adapter.SingleSection;
import com.example.mahmouddiab.dazzlekitchen.authentication.AuthenticationActivity;
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
import com.example.mahmouddiab.dazzlekitchen.views.DividerItemDecoration;
import com.example.mahmouddiab.dazzlekitchen.views.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity implements OrderView, SingleSection.OnActionclick, OnDialogClicked {

    private SectionedRecyclerViewAdapter sectionAdapter;
    private OrderPresenter orderPresenter;
    private ProgressDialog progressDialog;
    private int id;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.empty_text)
    TextView emptyText;
    private CompleteOrderConfirmation completeOrderConfirmation;
    private int position;
    private OrdersModel ordersModel;
    private CompleteOrderConfirmation completeOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        sectionAdapter = new SectionedRecyclerViewAdapter();
        WrapContentLinearLayoutManager layoutManager = new WrapContentLinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(sectionAdapter);
        orderPresenter = new OrdersPresenterImpl(this);
        orderPresenter.getOrders(UserManager.getInstance().getUser().getData().getToken());
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,R.drawable.sk_line_divider);
//        recyclerView.addItemDecoration(dividerItemDecoration);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                orderPresenter.getOrders(UserManager.getInstance().getUser().getData().getToken());
            }
        });
    }

    @OnClick(R.id.logout)
    void onLogoutClicked() {
        UserManager.getInstance().logout();
        AuthenticationActivity.start(this);
        finish();
    }

    @Override
    public void onGetOrderSuccess(OrdersModel ordersModel) {
        sectionAdapter.removeAllSections();
        swipeRefreshLayout.setRefreshing(false);
        this.ordersModel = ordersModel;
        if (ordersModel.getData().isEmpty()) {
            emptyText.setVisibility(View.VISIBLE);
        } else {
            emptyText.setVisibility(View.GONE);
        }
        progressDialog.dismiss();
        for (int i = 0; i < ordersModel.getData().size(); i++) {
            sectionAdapter.addSection(new SingleSection(this, ordersModel.getData().get(i).getDishes(),
                    String.valueOf(ordersModel.getData().get(i).getId()), this));
        }

        sectionAdapter.notifyDataSetChanged();

    }

    @Override
    public void onDoneSuccess(OrderDoneModel orderDoneModel) {
        Utils.showLongToast(this, orderDoneModel.getData().getMessage());
//        ordersModel.getData().get(position).getDishes().clear();
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
        Utils.showLongToast(this, orderDoneModel.getData().getMessage());
//        ordersModel.getData().get(position).getDishes().clear();
        completeOrder.dismiss();
        sectionAdapter.removeSection(sectionAdapter.getSectionForPosition(position));
        sectionAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetOrderFailed(Error error) {
        progressDialog.dismiss();
        Utils.showLongToast(this, error.getTitle());
        swipeRefreshLayout.setRefreshing(false);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void onDoneClicked(int id, int position) {
        this.id = id;
        this.position = position;
        completeOrderConfirmation = new CompleteOrderConfirmation(this, this);
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
        completeOrder = new CompleteOrderConfirmation(this, new OnDialogClicked() {
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
