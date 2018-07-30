package com.example.mahmouddiab.dazzlekitchen.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mahmouddiab.dazzlekitchen.R;
import com.example.mahmouddiab.dazzlekitchen.model.NotificationData;
import com.example.mahmouddiab.dazzlekitchen.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationAdapter extends Adapter<NotificationAdapter.MyViewHolder> {
    private Context context;
    private List<NotificationData> notificationModel;

    public class MyViewHolder extends ViewHolder {
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.order_num)
        TextView orderNum;
        @BindView(R.id.notification)
        TextView title;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public NotificationAdapter(Context context, List<NotificationData> notificationModel) {
        this.notificationModel = notificationModel;
        this.context = context;
    }

    @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notificaion_item, parent, false));
    }

    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(String.format("%s %s %s", this.context.getString(R.string.notification), this.notificationModel.get(position).getTableName(), this.context.getString(R.string.notificaion_complete_msg)));
        holder.orderNum.setText(String.format("%s%s", this.context.getText(R.string.order_id), String.valueOf(this.notificationModel.get(position).getOrderId())));
        holder.date.setText(Utils.getTimeAgo(Long.parseLong(this.notificationModel.get(position).getCreatedAt())));
    }

    public int getItemCount() {
        return this.notificationModel.size();
    }
}
