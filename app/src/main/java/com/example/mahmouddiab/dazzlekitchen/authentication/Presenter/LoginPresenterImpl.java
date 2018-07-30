package com.example.mahmouddiab.dazzlekitchen.authentication.Presenter;

import com.example.mahmouddiab.dazzlekitchen.api.ApiFactory;
import com.example.mahmouddiab.dazzlekitchen.model.Error;
import com.example.mahmouddiab.dazzlekitchen.model.UserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView loginView;

    public LoginPresenterImpl(LoginView loginView){
        this.loginView = loginView;
    }
    @Override
    public void doLogin(final String username, String password) {
        ApiFactory.createInstance().login(username,password).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful() && response.body() != null){
                    if(response.body().getError() == null){
                        loginView.onLoginSuccess(response.body());
                    }else {
                        loginView.onLoginFail(response.body().getError());
                    }
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Error error = new Error();
                error.setCode(0);
                error.setTitle(t.getLocalizedMessage());
                loginView.onLoginFail(error);
            }
        });
    }
}
