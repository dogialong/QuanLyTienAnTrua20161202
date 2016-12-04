package com.training.cst.quanlytienantrua.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.training.cst.quanlytienantrua.DataManager.Object.Account;
import com.training.cst.quanlytienantrua.DataManager.Object.Food;
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
    public static final String COLUMN_PATHAVATAR = "path";
    public static final String COLUMN_PAY = "pay";
    public static final String COLUMN_PARCHE = "parche";
    public static final String COLUMN_AMOUNT = "amout";
    private static final String CREATE_PERSON_TABLE = "CREATE TABLE " + TABLE_PERSON + " (" +
            COLUMN_ID_PERSON + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAMEPERSON + " TEXT," +
            COLUMN_DEPARTMENT + " TEXT," +
            COLUMN_NOTE + " TEXT," +
            COLUMN_PATHAVATAR + " TEXT," +
            COLUMN_PAY + " REAL," +
            COLUMN_PARCHE + " REAL," +
            COLUMN_AMOUNT + " REAL);";
    // Cac thong tin ve mon an
    public static final String TABLE_FOOD = "food";
    public static final String COLUMN_ID_FOOD = "_idfood";
    public static final String COLUMN_NAMEFOOD = "namefood";
    public static final String COLUMN_PRICE = "pricefood";
    private static final String CREATE_FOOD_TABLE = "CREATE TABLE " + TABLE_FOOD + " (" +
            COLUMN_ID_FOOD + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAMEFOOD + " TEXT," +
            COLUMN_PRICE + " REAL);";
    public DatabaseUser(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ACCOUNT_TABLE);
        db.execSQL(CREATE_PERSON_TABLE);
        db.execSQL(CREATE_FOOD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_ACCOUNT);
        db.execSQL("drop table if exists " + TABLE_PERSON);
        db.execSQL("drop table if exists " + TABLE_FOOD);
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
        values.put(COLUMN_PATHAVATAR,person.getmPathAvatar());
        values.put(COLUMN_PAY, String.valueOf(person.getPay()));
        values.put(COLUMN_PARCHE, String.valueOf(person.getParche()));
        values.put(COLUMN_AMOUNT, String.valueOf(person.getmAmount()));
        rowId = db.insert(TABLE_PERSON, null, values);
        return rowId;
    }

    public int updatePerson(Person person, String where, String... whereArgs) {
        int rowCount = 0;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAMEPERSON, person.getNamePerson());
        values.put(COLUMN_DEPARTMENT, person.getDepartment());
        values.put(COLUMN_NOTE, person.getNote());
        values.put(COLUMN_PATHAVATAR,person.getmPathAvatar());
        values.put(COLUMN_PAY, String.valueOf(person.getPay()));
        values.put(COLUMN_PARCHE, String.valueOf(person.getParche()));
        values.put(COLUMN_AMOUNT, String.valueOf(person.getmAmount()));
        rowCount = db.update(TABLE_PERSON, values, where, whereArgs);
        return rowCount;
    }

    // Xoa person
    public int deletePerson(String where, String... whereArgs) {
        int rowCount = 0;
        SQLiteDatabase db = getWritableDatabase();
        rowCount = db.delete(TABLE_PERSON, where, whereArgs);
        return rowCount;
    }
    // Check xem đã trùng tên nhân viên chưa
    public int checkPerson(String where, String... whereArgs) {
        int rowCount = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_PERSON, null, where, whereArgs, null, null, null);
        rowCount = cursor.getCount();
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
    public List<Person> getPerson() {
        List<Person> listPerson = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_PERSON, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String nameperson = cursor.getString(cursor.getColumnIndex(COLUMN_NAMEPERSON));
            String department = cursor.getString(cursor.getColumnIndex(COLUMN_DEPARTMENT));
            String note = cursor.getString(cursor.getColumnIndex(COLUMN_NOTE));
            String path = cursor.getString(cursor.getColumnIndex(COLUMN_PATHAVATAR));
            long pay = cursor.getLong(cursor.getColumnIndex(COLUMN_PAY));
            long parche =cursor.getLong(cursor.getColumnIndex(COLUMN_PARCHE));
            long amount = cursor.getLong(cursor.getColumnIndex(COLUMN_AMOUNT));
            Person person = new Person(nameperson, department, note,path, pay,parche,amount);
            listPerson.add(person);

        }
        return listPerson;
    }
    // tinh tong so tien cua moi nguoi trong overview fragment
    public long getTotalMoney(List<Person> listPerson) {
        long totalMoney = 0;
            for (int i = 0; i < listPerson.size(); i++) {
                totalMoney += listPerson.get(i).getmAmount();
            }
        return totalMoney;
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
    // check if password right , user wrong
    public int getRegisterPass(String password1) {
        int password = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        //String selectquery="SELECT * FROM TABLE_REGISTER";
        Cursor cursor = db.query(TABLE_ACCOUNT, null, COLUMN_PASSWORD + "= ?", new String[]{password1}, null, null, null, null);

        if (cursor.getCount() < 1) {
            cursor.close();
            return password;
        } else if (cursor.getCount() >= 1 && cursor.moveToFirst()) {

            password = 1;
        }
        return password;
    }

    // them 1 mon an
    public long insertFood (Food food) {
        long rowId = 0;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAMEFOOD, food.getNameFood());
        values.put(COLUMN_PRICE, food.getPriceFood());
        rowId = db.insert(TABLE_FOOD, null, values);
        return rowId;
    }

    // xoa 1 mon an
    public int deleteFood (String where, String... whereArgs) {
        int rowCount = 0;
        SQLiteDatabase db = getWritableDatabase();
        rowCount = db.delete(TABLE_FOOD, where, whereArgs);
        return rowCount;
    }

    // sua 1 mon an
    public int updateFood (Food food, String where, String... whereArgs) {
        int rowCount = 0;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAMEFOOD, food.getNameFood());
        values.put(COLUMN_PRICE, food.getPriceFood());
        rowCount = db.update(TABLE_PERSON, values, where, whereArgs);
        return rowCount;
    }
}
