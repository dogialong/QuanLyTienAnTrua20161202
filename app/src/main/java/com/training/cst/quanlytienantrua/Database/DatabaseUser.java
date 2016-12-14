package com.training.cst.quanlytienantrua.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.training.cst.quanlytienantrua.DataManager.Object.Account;
import com.training.cst.quanlytienantrua.DataManager.Object.Food;
import com.training.cst.quanlytienantrua.DataManager.Object.History;
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
    public static final String COLUMN_AVATAR = "avatarpath";
    private static final String CREATE_ACCOUNT_TABLE = "CREATE TABLE " + TABLE_ACCOUNT + " ( " +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_USERNAME + " TEXT," + COLUMN_PASSWORD + " TEXT," +
            COLUMN_REPASSWORD + " TEXT," +
            COLUMN_AVATAR + "TEXT);";
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
    public static final String COLUMN_SUMPAY = "sumpay";
    public static final String COLUMN_DATE = "date";

    private static final String CREATE_PERSON_TABLE = "CREATE TABLE " + TABLE_PERSON + " (" +
            COLUMN_ID_PERSON + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAMEPERSON + " TEXT," +
            COLUMN_DEPARTMENT + " TEXT," +
            COLUMN_NOTE + " TEXT," +
            COLUMN_PATHAVATAR + " TEXT," +
            COLUMN_PAY + " REAL," +
            COLUMN_PARCHE + " REAL," +
            COLUMN_AMOUNT + " REAL," +
            COLUMN_SUMPAY + " REAL," +
            COLUMN_DATE + " DATE);";
    // Cac thong tin ve mon an
    public static final String TABLE_FOOD = "food";
    public static final String COLUMN_ID_FOOD = "_idfood";
    public static final String COLUMN_NAMEFOOD = "namefood";
    public static final String COLUMN_PRICE = "pricefood";
    private static final String CREATE_FOOD_TABLE = "CREATE TABLE " + TABLE_FOOD + " (" +
            COLUMN_ID_FOOD + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAMEFOOD + " TEXT," +
            COLUMN_PRICE + " REAL);";
//     Cac thong tin de luu lich su
    public static final String TABLE_HISTORY = "history";
    public static final String COLUMN_ID_HISTORY = "_idhistory";
    public static final String COLUMN_NAME_HISTORY = "namehistory";
    public static final String COLUMN_PAY_HISTORY = "payhistory";
    public static final String COLUMN_AMOUNT_HISTORY = "amounthistory";
    public static final String COLUMN_DAY = "_day";
    private static final String CREATE_HISTORY_TABLE = "CREATE TABLE " + TABLE_HISTORY + " (" +
            COLUMN_ID_HISTORY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME_HISTORY + " TEXT," +
            COLUMN_PAY_HISTORY + " REAL," +
            COLUMN_AMOUNT_HISTORY + " REAL," +
            COLUMN_DAY +" DATE);";

    public DatabaseUser(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ACCOUNT_TABLE);
        db.execSQL(CREATE_PERSON_TABLE);
        db.execSQL(CREATE_FOOD_TABLE);
        db.execSQL(CREATE_HISTORY_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_ACCOUNT);
        db.execSQL("drop table if exists " + TABLE_PERSON);
        db.execSQL("drop table if exists " + TABLE_FOOD);
        db.execSQL("drop table if exists " + TABLE_HISTORY);
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
//        values.put(COLUMN_AVATAR,account.getmAvatar());
        rowId = db.insert(TABLE_ACCOUNT, null, values);
        return rowId;
    }
    // update account
    public int updateAccount(Account account, String where, String... whereArgs) {
        int rowCount = 0;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, account.getUserName());
        values.put(COLUMN_PASSWORD, account.getPassword());
        values.put(COLUMN_REPASSWORD, account.getRePassword());
        values.put(COLUMN_PATHAVATAR,account.getmAvatar());
        rowCount = db.update(TABLE_PERSON, values, where, whereArgs);
        return rowCount;
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
        values.put(COLUMN_SUMPAY,String.valueOf(person.getmSumPay()));
        values.put(COLUMN_DATE,String.valueOf(person.getmDate()));
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
        values.put(COLUMN_SUMPAY, String.valueOf(person.getmSumPay()));
        values.put(COLUMN_DATE, String.valueOf(person.getmDate()));
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
            long sumpay = cursor.getLong(cursor.getColumnIndex(COLUMN_SUMPAY));
            String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
            Person person = new Person(nameperson, department, note,path, pay,parche,amount,sumpay,date);
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
    // lay ra cac mon an
    public List<Food> getFood() {
        List<Food> listFood = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_FOOD, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String nameFood = cursor.getString(cursor.getColumnIndex(COLUMN_NAMEFOOD));
            long priceFood = cursor.getLong(cursor.getColumnIndex(COLUMN_PRICE));
            Food food = new Food(nameFood, priceFood);
            listFood.add(food);

        }
        return listFood;
    }
    // Check xem đã trùng tên nhân viên chưa
    public int checkFood(String where, String... whereArgs) {
        int rowCount = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_FOOD, null, where, whereArgs, null, null, null);
        rowCount = cursor.getCount();
        return rowCount;
    }
    // lay ra price cua food
    public long getPriceFood(String nameFood) {
        long nameFood1 = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        //String selectquery="SELECT * FROM TABLE_REGISTER";
        Cursor cursor = db.query(TABLE_FOOD, null, COLUMN_NAMEFOOD + "= ?", new String[]{nameFood}, null, null, null, null);

        if (cursor.getCount() < 1) {
            cursor.close();
            return nameFood1;
        } else if (cursor.getCount() >= 1 && cursor.moveToFirst()) {

            nameFood1 = cursor.getLong(cursor.getColumnIndex(COLUMN_PRICE));
        }
        return nameFood1;
    }
    // luu lai 1 lich su
    public long insertHistory (History history) {
        long rowId = 0;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_HISTORY, history.getNamePerson());
        values.put(COLUMN_PAY_HISTORY, history.getmPay());
        values.put(COLUMN_AMOUNT_HISTORY, history.getmAmount());
        values.put(COLUMN_DAY, history.getmDate());
        rowId = db.insert(TABLE_HISTORY, null, values);
        return rowId;
    }
    // lay ra cac lich su
    // lay ra cac mon an
    public List<History> getHistory() {
        List<History> listHistory = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_HISTORY, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String nameHistory = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_HISTORY));
            long sumPay = cursor.getLong(cursor.getColumnIndex(COLUMN_PAY_HISTORY));
            long amount = cursor.getLong(cursor.getColumnIndex(COLUMN_AMOUNT_HISTORY));
            String date = cursor.getString(cursor.getColumnIndex(COLUMN_DAY));
            History history = new History(nameHistory, sumPay,amount,date);
            listHistory.add(history);

        }
        return listHistory;
    }
    //lay ra cac ngay thang
    public List<String> getAllDate () {
        List<String> listString = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select " + COLUMN_DAY + " from " + TABLE_HISTORY + " group by " + COLUMN_DAY;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst() != true) {
            listString.add("Nothing");
        } else {
            while (cursor.isAfterLast() == false) {
                listString.add(cursor.getString(cursor.getColumnIndex(COLUMN_DAY)));
                cursor.moveToNext();
            }
        }
        return listString;
    }
    // lay ra lich su theo tung ngay

    public List<History> getHistoryByDate(String date) {
        List<History> listHistory = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectquery="SELECT namehistory,amounthistory,SUM(payhistory) AS payhistory,_day" +
                " FROM history where _day = " + "\"" + date + "\""  +" GROUP BY _day, namehistory ORDER BY _day, namehistory ";
        Cursor cursor = db.rawQuery(selectquery,null);
        while (cursor.moveToNext()) {
            String nameHistory = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_HISTORY));
            long sumPay = cursor.getLong(cursor.getColumnIndex(COLUMN_PAY_HISTORY));
            long amount = cursor.getLong(cursor.getColumnIndex(COLUMN_AMOUNT_HISTORY));
            String datehis = cursor.getString(cursor.getColumnIndex(COLUMN_DAY));
            History history = new History(nameHistory, sumPay,amount,datehis);
            listHistory.add(history);
            Log.d("ahi", "getHistoryByDate: " + selectquery);
        }
        return listHistory;

    }
}
