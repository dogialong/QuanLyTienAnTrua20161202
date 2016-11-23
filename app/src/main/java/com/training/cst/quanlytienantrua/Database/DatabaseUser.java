package com.training.cst.quanlytienantrua.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.training.cst.quanlytienantrua.DataManager.Object.Account;
import com.training.cst.quanlytienantrua.DataManager.Object.Person;

import java.util.ArrayList;
import java.util.List;

import static android.accounts.AccountManager.KEY_PASSWORD;

/**
 * Created by longdg123 on 11/21/2016.
 */

public class DatabaseUser extends SQLiteOpenHelper {
    private Context mContext;
    private static final String DATABASE_NAME = "account.db";
    private static final int DATABASE_VERSION = 1;

    // Cac thong tin tai khoan dang nhap
    public static final String TABLE_ACCOUNT = "acount";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_REPASSWORD = "repassword";
    private static final String CREATE_ACCOUNT_TABLE = "CREATE TABLE " + TABLE_ACCOUNT + " ( " +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_USERNAME + " TEXT," + COLUMN_PASSWORD + " TEXT," +
            COLUMN_REPASSWORD + " TEXT" +
            ");";
    // Cac thong tin ve person
    public static final String TABLE_PERSON = "person";
    public static final String COLUMN_ID_PERSON = "_idper";
    public static final String COLUMN_NAMEPERSON = "nameperson";
    public static final String COLUMN_DEPARTMENT = "department";
    public static final String COLUMN_NOTE = "note";
    private static final String CREATE_PERSON_TABLE = "CREATE TABLE " + TABLE_PERSON + " (" +
            COLUMN_ID_PERSON + " INTEGER PRIMARY KEY AUTOCREMENT, " +
            COLUMN_NAMEPERSON + " TEXT," +
            COLUMN_DEPARTMENT + " TEXT," +
            COLUMN_NOTE + "TEXT);";

    public DatabaseUser(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ACCOUNT_TABLE);
        db.execSQL(CREATE_PERSON_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_ACCOUNT);
        db.execSQL("drop table if exists " + TABLE_PERSON);
        onCreate(db);
    }

    // them account
    public long insertAccount(Account account) {
        long rowId = 0;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, account.getUserName());
        values.put(COLUMN_PASSWORD, account.getPassword());
        values.put(COLUMN_REPASSWORD, account.getRePassword());
        rowId = db.insert(TABLE_ACCOUNT, null, values);
        return rowId;
    }

    // them person
    public long insertPerson(Person person) {
        long rowId = 0;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAMEPERSON, person.getNamePerson());
        values.put(COLUMN_DEPARTMENT, person.getDepartment());
        values.put(COLUMN_NOTE, person.getNote());
        rowId = db.insert(TABLE_PERSON, null, values);
        return rowId;
    }

    public int updateAccount(Account account, String where, String... whereArgs) {
        int rowCount = 0;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, account.getUserName());
        values.put(COLUMN_PASSWORD, account.getPassword());
        values.put(COLUMN_REPASSWORD, account.getRePassword());
        rowCount = db.update(TABLE_ACCOUNT, values, where, whereArgs);
        return rowCount;
    }

    public int checkAccount(String where, String... whereArgs) {
        int rowCount = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_ACCOUNT, null, where, whereArgs, null, null, null);
        rowCount = cursor.getCount();
        return rowCount;
    }

    // lay ra list person
    public List<Person> getPerson(String where, String... whereArgs) {
        List<Person> listPerson = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_PERSON, null, where, whereArgs, null, null, null);
        while (cursor.moveToNext()) {
            String nameperson = cursor.getString(cursor.getColumnIndex(COLUMN_NAMEPERSON));
            String department = cursor.getString(cursor.getColumnIndex(COLUMN_DEPARTMENT));
            String note = cursor.getString(cursor.getColumnIndex(COLUMN_NOTE));
            Person person = new Person(nameperson, department, note);
            listPerson.add(person);

        }
        return listPerson;
    }

    // Check password
    public String getRegister(String username) {
        String password = null;
        SQLiteDatabase db = this.getReadableDatabase();
        //String selectquery="SELECT * FROM TABLE_REGISTER";
        Cursor cursor = db.query(TABLE_ACCOUNT, null, COLUMN_USERNAME + "=?", new String[]{username}, null, null, null, null);

        if (cursor.getCount() < 1) {
            cursor.close();
            return "Not Exist";
        } else if (cursor.getCount() >= 1 && cursor.moveToFirst()) {

            password = cursor.getString(cursor.getColumnIndex(KEY_PASSWORD));
            cursor.close();

        }
        return password;
    }
}
