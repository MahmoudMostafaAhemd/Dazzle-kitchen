package com.example.mahmouddiab.dazzlekitchen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mahmouddiab.dazzlekitchen.adapter.SingleSection;
import com.example.mahmouddiab.dazzlekitchen.adapter.SingleSection.OnActionclick;
import com.example.mahmouddiab.dazzlekitchen.authentication.AuthenticationActivity;
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
import butterknife.OnClick;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity implements OrderView, OnActionclick, OnDialogClicked {
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


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        this.sectionAdapter = new SectionedRecyclerViewAdapter();
        this.recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this));
        this.recyclerView.setAdapter(this.sectionAdapter);
        this.orderPresenter = new OrdersPresenterImpl(this);
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.show();
        this.swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
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

    @OnClick({R.id.logout})
    void onLogoutClicked() {
        UserManager.getInstance().logout();
        AuthenticationActivity.start(this);
        finish();
    }

    protected void onResume() {
        super.onResume();
        this.orderPresenter.getOrders(UserManager.getInstance().getUser().getData().getToken());
    }

    public void onGetOrderSuccess(OrdersModel ordersModel) {
        this.sectionAdapter.removeAllSections();
        int i = 0;
        this.swipeRefreshLayout.setRefreshing(false);
        this.ordersModel = ordersModel;
        if (ordersModel.getData().isEmpty()) {
            this.emptyText.setVisibility(View.VISIBLE);
        } else {
            this.emptyText.setVisibility(View.GONE);
        }
        this.progressDialog.dismiss();
        while (true) {
            int i2 = i;
            if (i2 < ordersModel.getData().size()) {
                this.sectionAdapter.addSection(new SingleSection(this, (ordersModel.getData().get(i2)).getDishes(), String.valueOf((ordersModel.getData().get(i2)).getId()), this));
                i = i2 + 1;
            } else {
                this.sectionAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    public void onDoneSuccess(OrderDoneModel orderDoneModel) {
        Utils.showLongToast(this, orderDoneModel.getData().getMessage());
        this.sectionAdapter.removeSection(this.sectionAdapter.getSectionForPosition(this.position));
        this.sectionAdapter.notifyDataSetChanged();
        this.completeOrderConfirmation.dismiss();
        if (this.sectionAdapter.getItemCount() == 0) {
            this.emptyText.setVisibility(View.VISIBLE);
        } else {
            this.emptyText.setVisibility(View.GONE);
        }
    }

    public void onInProgressSuccess(OrderDoneModel orderDoneModel) {
        this.ordersModel.getData().get(this.position).getDishes().get(0).setIsDone("In Progress");
        this.sectionAdapter.notifyDataSetChanged();
    }

    public void onCloseSuccess(OrderDoneModel orderDoneModel) {
        Utils.showLongToast(this, orderDoneModel.getData().getMessage());
        this.completeOrder.dismiss();
        this.sectionAdapter.removeSection(this.sectionAdapter.getSectionForPosition(this.position));
        this.sectionAdapter.notifyDataSetChanged();
    }

    public void onTakenRigestered(OrderDoneModel msg) {
    }

    public void onGetOrderFailed(Error error) {
        this.progressDialog.dismiss();
        Utils.showLongToast(this, error.getTitle());
        this.swipeRefreshLayout.setRefreshing(false);
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    public void onDoneClicked(int id, int position) {
        this.id = id;
        this.position = position;
        this.completeOrderConfirmation = new CompleteOrderConfirmation( this,  this);
        this.completeOrderConfirmation.show();
    }

    public void onInprogressClicked(int id, int position) {
        this.position = position;
        this.orderPresenter.onInProgressClicked(UserManager.getInstance().getUser().getData().getToken(), id);
    }

    public void onCloseOrder(final int ids, int position) {
        this.position = position;
        this.completeOrder = new CompleteOrderConfirmation( this, new OnDialogClicked() {
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
    }

    public void onYesClicked() {
        this.orderPresenter.onDoneClicked(UserManager.getInstance().getUser().getData().getToken(), this.id);
        this.completeOrderConfirmation.dismiss();
    }

    public void onNoClicked() {
        this.completeOrderConfirmation.dismiss();
    }
}
