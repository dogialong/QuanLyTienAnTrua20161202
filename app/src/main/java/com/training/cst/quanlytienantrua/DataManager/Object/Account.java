package com.training.cst.quanlytienantrua.DataManager.Object;

/**
 * Created by longdg123 on 11/22/2016.
 */

public class Account {
    private String mUserName;
    private String mPassword;
    private String mRePassword;
    private String mAvatar;
    public Account(String mUserName, String mPassword,String mRePassword) {
        this.mUserName = mUserName;
        this.mPassword = mPassword;
        this.mRePassword = mRePassword;
//        this.mAvatar = mAvatar;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getPassword() {
        return mPassword;
    }

    public String getRePassword() {
        return mRePassword;
    }

    public String getmAvatar() {
        return mAvatar;
    }

    public void setmAvatar(String mAvatar) {
        this.mAvatar = mAvatar;
    }
}
