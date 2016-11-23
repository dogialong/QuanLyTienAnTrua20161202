package com.training.cst.quanlytienantrua.DataManager.Object;

/**
 * Created by longdg123 on 11/22/2016.
 */

public class Account {
    private String mUserName;
    private String mPassword;
    private String mRePassword;
    public Account(String mUserName, String mPassword,String mRePassword) {
        this.mUserName = mUserName;
        this.mPassword = mPassword;
        this.mRePassword = mRePassword;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getRePassword() {
        return mRePassword;
    }
}
