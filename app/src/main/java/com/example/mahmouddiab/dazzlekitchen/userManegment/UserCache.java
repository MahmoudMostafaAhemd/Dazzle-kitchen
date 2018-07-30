package com.example.mahmouddiab.dazzlekitchen.userManegment;

import com.example.mahmouddiab.dazzlekitchen.model.UserModel;

import io.paperdb.Book;
import io.paperdb.Paper;

/**
 * Created by mahmoud.diab on 4/18/2018.
 */

public class UserCache {
    private static final String Map_NAME = "_user";
    private static final String USER_KEY = "_user_key";

    private final Book book;

    public UserCache() {
        book = Paper.book(Map_NAME);
    }


    public UserModel getUser() {
        return book.read(USER_KEY, null);
    }

    public void save(UserModel user) {
        book.write(USER_KEY, user);
    }


    public void deleteUser() {
        book.delete(USER_KEY);
    }
}
