package com.training.cst.quanlytienantrua.DataManager.Object;

/**
 * Created by longdg123 on 11/23/2016.
 */

public class Person {
    private String mNamePerson;
    private String mDepartment;
    private String mNote;

    public Person(String mName, String mNote, String mDepartment) {
        this.mNamePerson = mName;
        this.mNote = mNote;
        this.mDepartment = mDepartment;
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
