package com.example.mahmouddiab.dazzlekitchen.authentication;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.mahmouddiab.dazzlekitchen.MainActivity;
import com.example.mahmouddiab.dazzlekitchen.R;
import com.example.mahmouddiab.dazzlekitchen.authentication.Presenter.LoginPresenter;
import com.example.mahmouddiab.dazzlekitchen.authentication.Presenter.LoginPresenterImpl;
import com.example.mahmouddiab.dazzlekitchen.authentication.Presenter.LoginView;
import com.example.mahmouddiab.dazzlekitchen.home.WaiterUserActivity;
import com.example.mahmouddiab.dazzlekitchen.model.Error;
import com.example.mahmouddiab.dazzlekitchen.model.UserModel;
import com.example.mahmouddiab.dazzlekitchen.userManegment.UserManager;
import com.example.mahmouddiab.dazzlekitchen.utils.Utils;
import com.example.mahmouddiab.dazzlekitchen.views.ProgressDialog;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthenticationActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.email)
    TextInputLayout email;
    @BindView(R.id.password)
    TextInputLayout password;
    private LoginPresenter loginPresenter;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        ButterKnife.bind(this);
        FirebaseInstanceId.getInstance().getToken();
    }

    @OnClick(R.id.login)
    void onLoginClicked() {
        email.setErrorEnabled(false);
        password.setErrorEnabled(false);
        if (!Utils.isValidEmail(email.getEditText().getText().toString())) {
            email.setErrorEnabled(true);
            email.setError(getString(R.string.invalid_email_msg));
            return;
        }
        if (TextUtils.isEmpty(password.getEditText().getText().toString())) {
            password.setErrorEnabled(true);
            password.setError(getString(R.string.empty_password_msg));
            return;
        }
        loginPresenter = new LoginPresenterImpl(this);
        loginPresenter.doLogin(email.getEditText().getText().toString(),
                password.getEditText().getText().toString());
        progressDialog = new ProgressDialog(this);
        progressDialog.show();

    }

    public static void start(Context context) {
        Intent starter = new Intent(context, AuthenticationActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void onLoginSuccess(UserModel userModel) {
        UserManager.getInstance().addUser(userModel);
        progressDialog.cancel();
        if (userModel.getData().getRole().equalsIgnoreCase("ROLE_WAITER"))
            WaiterUserActivity.start(this);
        else
            MainActivity.start(this);
        finish();

    }

    @Override
    public void onLoginFail(Error error) {
        Utils.showLongToast(this, error.getTitle());
        progressDialog.cancel();
    }
}
