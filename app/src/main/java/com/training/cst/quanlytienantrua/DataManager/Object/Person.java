package com.training.cst.quanlytienantrua.DataManager.Object;

import java.math.BigDecimal;

/**
 * Created by longdg123 on 11/23/2016.
 */

public class Person {
    private String mNamePerson;
    private String mDepartment;
    private String mNote;
    private String mPathAvatar;
    private long mAmount;
    private long mPay;
    private long mParche ;
    public Person(String mName,  String mDepartment,String mNote,String mPathAvatar,long mPay,
                  long mParche,long amout) {
        this.mNamePerson = mName;
        this.mNote = mNote;
        this.mPathAvatar = mPathAvatar;
        this.mDepartment = mDepartment;
        this.mPay = mPay;
        this.mParche = mParche;
        this.mAmount = amout;
    }


    public long getmAmount() {
        return this.mAmount;
    }

    public long getPay() {
        return mPay;
    }

    public long getParche() {
        return mParche;
    }

    public String getmPathAvatar() {
        return mPathAvatar;
    }

    public String getNamePerson() {
        return mNamePerson;
    }

    public String getDepartment() {
        return mDepartment;
    }

    public String getNote() {
        return mNote;
    }
}
