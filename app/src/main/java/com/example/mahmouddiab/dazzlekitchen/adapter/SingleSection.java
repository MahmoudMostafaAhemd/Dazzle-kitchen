package com.example.mahmouddiab.dazzlekitchen.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.mahmouddiab.dazzlekitchen.R;
import com.example.mahmouddiab.dazzlekitchen.model.Dish;
import com.example.mahmouddiab.dazzlekitchen.userManegment.UserManager;
import com.example.mahmouddiab.dazzlekitchen.views.TimePickerView;
import com.example.mahmouddiab.dazzlekitchen.views.TimePickerView.OnOkClicked;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

public class SingleSection extends StatelessSection implements OnOkClicked {
    private Context context;
    private List<Dish> dishes;
    private String id;
    private OnActionclick onActionclick;


    public interface OnActionclick {
        void onCloseOrder(int i, int i2);

        void onDoneClicked(int i, int i2);

        void onInprogressClicked(int i, int i2);

        void onSubmitWaitingTime(int i, String str);
    }

    private class FooterViewHolder extends ViewHolder {
        private final TextView close;
        private final TextView done;
        private final TextView inProgress;

        FooterViewHolder(View view) {
            super(view);
            this.done = view.findViewById(R.id.done);
            this.inProgress = view.findViewById(R.id.in_progress);
            this.close = view.findViewById(R.id.close);
        }
    }

    private class HeaderViewHolder extends ViewHolder {
        private final TextView settime;
        private final TextView tvTitle;

        HeaderViewHolder(View view) {
            super(view);
            this.tvTitle = view.findViewById(R.id.order_num);
            this.settime = view.findViewById(R.id.settime);
        }
    }

    class MyItemViewHolder extends ViewHolder {
        private final TextView comments;
        private final TextView itemName;
        private final TextView itemsNum;
        private final TextView additionTitle;
        private final TextView addition;
        private final TextView removalTitle;
        private final TextView removal;

        public MyItemViewHolder(View itemView) {
            super(itemView);
            this.itemsNum = itemView.findViewById(R.id.items_num);
            this.itemName = itemView.findViewById(R.id.item_name);
            this.comments = itemView.findViewById(R.id.comments);
            additionTitle = itemView.findViewById(R.id.additions_title);
            addition = itemView.findViewById(R.id.additions);
            removalTitle = itemView.findViewById(R.id.removal_title);
            removal = itemView.findViewById(R.id.removal);
        }
    }

    public SingleSection(Context context, List<Dish> dishes, String id, OnActionclick onActionclick) {
        super(SectionParameters.builder().itemResourceId(R.layout.section_item).headerResourceId(R.layout.section_header).footerResourceId(R.layout.section_footer).build());
        this.dishes = dishes;
        this.id = id;
        this.context = context;
        this.onActionclick = onActionclick;
    }

    public int getContentItemsTotal() {
        return this.dishes.size();
    }

    public ViewHolder getItemViewHolder(View view) {
        return new MyItemViewHolder(view);
    }

    public void onBindItemViewHolder(ViewHolder holder, int position) {
        MyItemViewHolder itemHolder = (MyItemViewHolder) holder;
        itemHolder.itemName.setText((dishes.get(position)).getName());
        itemHolder.itemsNum.setText(String.valueOf((dishes.get(position)).getQuantity()));
        if (TextUtils.isEmpty((dishes.get(position)).getComment())) {
            itemHolder.comments.setVisibility(View.GONE);
        } else {
            itemHolder.comments.setVisibility(View.VISIBLE);
            itemHolder.comments.setText((dishes.get(position)).getComment());
        }

        if (dishes.get(position).getAdditions().isEmpty()) {
            itemHolder.addition.setVisibility(View.GONE);
            itemHolder.additionTitle.setVisibility(View.GONE);
        } else {
            itemHolder.addition.setVisibility(View.VISIBLE);
            itemHolder.additionTitle.setVisibility(View.VISIBLE);
            ArrayList<String> additions= new ArrayList<>();
            for(int i = 0; i < dishes.get(position).getAdditions().size(); i++){
                if(!additions.contains(dishes.get(position).getAdditions().get(i).getName())){
                    additions.add(dishes.get(position).getAdditions().get(i).getName());
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            for (int i = 0; i < additions.size(); i++) {
                sb.append(additions.get(i));
                if (i != additions.size() - 1) {
                    String prefix = ", ";
                    sb.append(prefix);
                }

            }
            sb.append(")");
            itemHolder.addition.setText(sb.toString());
        }

        if (dishes.get(position).getRemovals().isEmpty()) {
            itemHolder.removal.setVisibility(View.GONE);
            itemHolder.removalTitle.setVisibility(View.GONE);
        } else {
            itemHolder.removal.setVisibility(View.VISIBLE);
            itemHolder.removalTitle.setVisibility(View.VISIBLE);
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            for (int i = 0; i < dishes.get(position).getRemovals().size(); i++) {
                sb.append(dishes.get(position).getRemovals().get(i).getName());
                sb.append(" ");
                sb.append(dishes.get(position).getRemovals().get(i).getPrice());
                if (i != dishes.get(position).getRemovals().size() - 1) {
                    String prefix = " , ";
                    sb.append(prefix);
                }
            }
            sb.append(")");
            itemHolder.removal.setText(sb.toString());
        }
    }

    public void onBindHeaderViewHolder(ViewHolder holder) {
        HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
        headerHolder.tvTitle.setText(String.format("%s%s", this.context.getText(R.string.order_id), this.id));
        if (UserManager.getInstance().getUser().getData().getRole().equalsIgnoreCase("ROLE_WAITER")) {
            headerHolder.settime.setVisibility(View.VISIBLE);
        } else {
            headerHolder.settime.setVisibility(View.GONE);
        }
        headerHolder.settime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerView(SingleSection.this.context, SingleSection.this).show();
            }
        });
    }

    public ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    public ViewHolder getFooterViewHolder(View view) {
        return new FooterViewHolder(view);
    }

    public void onBindFooterViewHolder(final ViewHolder holder) {
        FooterViewHolder footerHolder = (FooterViewHolder) holder;
        if (UserManager.getInstance().getUser().getData().getRole().equalsIgnoreCase("ROLE_WAITER")) {
            footerHolder.close.setVisibility(View.VISIBLE);
            footerHolder.done.setVisibility(View.GONE);
            footerHolder.inProgress.setVisibility(View.GONE);
        } else {
            footerHolder.close.setVisibility(View.GONE);
            footerHolder.done.setVisibility(View.VISIBLE);
            footerHolder.inProgress.setVisibility(View.VISIBLE);
        }
        if (this.dishes.isEmpty() || !this.dishes.get(0).getIsDone().equalsIgnoreCase("In Progress")) {
            footerHolder.inProgress.setBackgroundResource(R.drawable.in_progress_bg);
            footerHolder.inProgress.setTextColor(ContextCompat.getColor(this.context, R.color.colorAccent));
            footerHolder.inProgress.setEnabled(true);
        } else {
            footerHolder.inProgress.setBackgroundResource(R.drawable.rounded_edittext);
            footerHolder.inProgress.setTextColor(ContextCompat.getColor(this.context, R.color.white));
            footerHolder.inProgress.setEnabled(false);
        }
        footerHolder.done.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SingleSection.this.onActionclick.onDoneClicked(Integer.parseInt(SingleSection.this.id), (holder.getAdapterPosition() - SingleSection.this.dishes.size()) - 1);
            }
        });
        footerHolder.inProgress.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SingleSection.this.onActionclick.onInprogressClicked(Integer.parseInt(SingleSection.this.id), (holder.getAdapterPosition() - SingleSection.this.dishes.size()) - 1);
            }
        });
        footerHolder.close.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SingleSection.this.onActionclick.onCloseOrder(Integer.parseInt(SingleSection.this.id), (holder.getAdapterPosition() - SingleSection.this.dishes.size()) - 1);
            }
        });
    }

    public void onOkClicked(String mins) {
        this.onActionclick.onSubmitWaitingTime(Integer.parseInt(this.id), mins);
    }
}
