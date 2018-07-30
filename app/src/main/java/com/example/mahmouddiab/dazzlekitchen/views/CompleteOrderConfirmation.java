package com.example.mahmouddiab.dazzlekitchen.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mahmouddiab.dazzlekitchen.R;
import com.example.mahmouddiab.dazzlekitchen.utils.OnDialogClicked;

/**
 * Created by mahmoud.diab on 5/19/2018.
 */

public class CompleteOrderConfirmation extends Dialog {

    private OnDialogClicked onDialogClicked;

    public CompleteOrderConfirmation(@NonNull Context context, OnDialogClicked onDialogClicked) {
        super(context);
        this.onDialogClicked = onDialogClicked;
    }

    public CompleteOrderConfirmation(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CompleteOrderConfirmation(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    TextView yes, no;
    public ProgressBar progressBar;
    View container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.complete_order_dialog);
        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);
        progressBar = findViewById(R.id.loading);
        container = findViewById(R.id.container);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDialogClicked.onYesClicked();
                container.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDialogClicked.onNoClicked();
                dismiss();
            }
        });
    }
}
