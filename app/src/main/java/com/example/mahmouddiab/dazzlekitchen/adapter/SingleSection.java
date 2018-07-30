package com.example.mahmouddiab.dazzlekitchen.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.mahmouddiab.dazzlekitchen.R;
import com.example.mahmouddiab.dazzlekitchen.model.Dish;
import com.example.mahmouddiab.dazzlekitchen.userManegment.UserManager;
import com.example.mahmouddiab.dazzlekitchen.views.TimePickerView;

import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

public class SingleSection extends StatelessSection {
    private List<Dish> dishes;
    private String id;
    private Context context;
    private OnActionclick onActionclick;

    // role ROLE_WAITER

    public SingleSection(Context context, List<Dish> dishes, String id, OnActionclick onActionclick) {
        super(SectionParameters.builder()
                .itemResourceId(R.layout.section_item)
                .headerResourceId(R.layout.section_header)
                .footerResourceId(R.layout.section_footer)
                .build());
        this.dishes = dishes;
        this.id = id;
        this.context = context;
        this.onActionclick = onActionclick;
    }


    @Override
    public int getContentItemsTotal() {
        return dishes.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new MyItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyItemViewHolder itemHolder = (MyItemViewHolder) holder;

        // bind your view here
        itemHolder.itemName.setText(dishes.get(position).getName());
        itemHolder.itemsNum.setText(String.valueOf(dishes.get(position).getQuantity()));

        if (TextUtils.isEmpty(dishes.get(position).getComment())) {
            itemHolder.comments.setVisibility(View.GONE);
        } else {
            itemHolder.comments.setVisibility(View.VISIBLE);
            itemHolder.comments.setText(dishes.get(position).getComment());
        }
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
        headerHolder.tvTitle.setText(String.format("%s%s", context.getText(R.string.order_id), id));
        if (UserManager.getInstance().getUser().getData().getRole().equalsIgnoreCase("ROLE_WAITER")) {
            headerHolder.settime.setVisibility(View.VISIBLE);
        } else {
            headerHolder.settime.setVisibility(View.GONE);
        }

        headerHolder.settime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerView timePickerView = new TimePickerView(context);
                timePickerView.show();
            }
        });

    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder getFooterViewHolder(View view) {
        return new FooterViewHolder(view);
    }


    @Override
    public void onBindFooterViewHolder(final RecyclerView.ViewHolder holder) {
        final FooterViewHolder footerHolder = (FooterViewHolder) holder;
//        footerHolder.comments.setText(dishes.get(holder.getAdapterPosition()).getComment());
        if (UserManager.getInstance().getUser().getData().getRole().equalsIgnoreCase("ROLE_WAITER")) {
            footerHolder.close.setVisibility(View.VISIBLE);
            footerHolder.done.setVisibility(View.GONE);
            footerHolder.inProgress.setVisibility(View.GONE);
        } else {
            footerHolder.close.setVisibility(View.GONE);
            footerHolder.done.setVisibility(View.VISIBLE);
            footerHolder.inProgress.setVisibility(View.VISIBLE);
        }

        if (!dishes.isEmpty() && dishes.get(0).getIsDone().equalsIgnoreCase("In Progress")) {
            footerHolder.inProgress.setBackgroundResource(R.drawable.rounded_edittext);
            footerHolder.inProgress.setTextColor(ContextCompat.getColor(context, R.color.white));
            footerHolder.inProgress.setEnabled(false);
        } else {
            footerHolder.inProgress.setBackgroundResource(R.drawable.in_progress_bg);
            footerHolder.inProgress.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            footerHolder.inProgress.setEnabled(true);
        }


        footerHolder.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionclick.onDoneClicked(Integer.parseInt(id), holder.getAdapterPosition() - dishes.size() - 1);
            }
        });

        footerHolder.inProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionclick.onInprogressClicked(Integer.parseInt(id), holder.getAdapterPosition() - dishes.size() - 1);
            }
        });

        footerHolder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionclick.onCloseOrder(Integer.parseInt(id), holder.getAdapterPosition() - dishes.size() - 1);
            }
        });
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {

        private final TextView done, inProgress, close;

        FooterViewHolder(View view) {
            super(view);
            done = view.findViewById(R.id.done);
            inProgress = view.findViewById(R.id.in_progress);
            close = view.findViewById(R.id.close);

        }
    }


    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle, settime;

        HeaderViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.order_num);
            settime = view.findViewById(R.id.settime);
        }
    }

    class MyItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemsNum, itemName, comments;

        public MyItemViewHolder(View itemView) {
            super(itemView);
            itemsNum = itemView.findViewById(R.id.items_num);
            itemName = itemView.findViewById(R.id.item_name);
            comments = itemView.findViewById(R.id.comments);
        }
    }

    public interface OnActionclick {
        void onDoneClicked(int id, int position);

        void onInprogressClicked(int id, int position);

        void onCloseOrder(int id, int position);
    }

}
