package com.example.mahmouddiab.dazzlekitchen.userManegment;

import android.support.annotation.Nullable;

import com.example.mahmouddiab.dazzlekitchen.model.UserModel;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;

/**
 * Created by mahmoud.diab on 4/18/2018.
 */

public class UserManager {
    private static UserManager instance;
    private final UserCache userCache;
    private UserModel currentUser;

    private UserManager() {
        userCache = new UserCache();
    }

    public void setIsSkip(boolean isSkip) {

    }

    @Nullable
    public UserModel getUser() {
        if (currentUser == null) {
            currentUser = getUserFromCache();
        }
        return currentUser;
    }

    public UserModel getUserData() {
        return getUser();
    }


    public void logout() {
        // reset user data

        try {
            userCache.deleteUser();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FirebaseInstanceId.getInstance().deleteInstanceId();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        currentUser = null;
    }

    public static UserManager getInstance() {
        if (instance == null) {
            synchronized (UserManager.class) {
                instance = new UserManager();
            }
        }
        return instance;
    }

    private UserModel getUserFromCache() {
        try {
            return userCache.getUser();
        } catch (Throwable e) {

            return null;
        }
    }

    public void addUser(UserModel user) {
        if (user != null) {
            try {
                userCache.save(user);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            currentUser = user;
        }
    }
}
