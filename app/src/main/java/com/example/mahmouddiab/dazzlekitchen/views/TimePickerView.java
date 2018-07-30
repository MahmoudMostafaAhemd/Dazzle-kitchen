package com.example.mahmouddiab.dazzlekitchen.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Window;

import com.example.mahmouddiab.dazzlekitchen.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.bugadani.circlepickerlib.CirclePickerView;

public class TimePickerView extends Dialog {
    @BindView(R.id.steps)
    CirclePickerView circlePickerView;
    private OnOkClicked okClicked;

    public interface OnOkClicked {
        void onOkClicked(String str);
    }

    public TimePickerView(@NonNull Context context, OnOkClicked onOkClicked) {
        super(context);
        this.okClicked = onOkClicked;
    }

    public TimePickerView(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected TimePickerView(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.time_picker);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.submit)
    void onSubmitClicked() {
        this.okClicked.onOkClicked(getMins());
        dismiss();
    }

    public String getMins() {
        return String.valueOf(this.circlePickerView.getValue());
    }
}
