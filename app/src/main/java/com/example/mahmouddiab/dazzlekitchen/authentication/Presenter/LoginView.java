package com.example.mahmouddiab.dazzlekitchen.authentication.Presenter;

import com.example.mahmouddiab.dazzlekitchen.model.Error;
import com.example.mahmouddiab.dazzlekitchen.model.UserModel;

public interface LoginView {

    void onLoginSuccess(UserModel userModel);

    void onLoginFail(Error error);
}
