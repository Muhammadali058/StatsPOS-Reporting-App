package com.example.statspos.Models;

import androidx.annotation.NonNull;

public class Users {
    String id, username;

    public Users() {

    }

    public Users(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NonNull
    @Override
    public String toString() {
        return username;
    }
}
