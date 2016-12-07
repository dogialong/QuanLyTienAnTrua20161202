package com.training.cst.quanlytienantrua.DataManager.Object;

/**
 * Created by longdg on 06/12/2016.
 */

public class History {
    private String namePerson;
    private long mPay;
    private long mRecharge;
    private long mAmount;
    private String mDate;

    public History(String namePerson, long mPay, long mRecharge, long mAmount, String mDate) {
        this.namePerson = namePerson;
        this.mPay = mPay;
        this.mRecharge = mRecharge;
        this.mAmount = mAmount;
        this.mDate = mDate;
    }

    public long getmPay() {
        return mPay;
    }

    public void setmPay(long mPay) {
        this.mPay = mPay;
    }

    public long getmRecharge() {
        return mRecharge;
    }

    public void setmRecharge(long mRecharge) {
        this.mRecharge = mRecharge;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public long getmAmount() {
        return mAmount;
    }

    public void setmAmount(long mAmount) {
        this.mAmount = mAmount;
    }

    public String getNamePerson() {
        return namePerson;
    }

    public void setNamePerson(String namePerson) {
        this.namePerson = namePerson;
    }
}
