package com.sitcom.sreejithm.helper;

/**
 * Created by SreejithM on 3/3/2016.
 * SQLiteHandler.java and paste the below code. This class takes care of storing the user data in SQLite database.
 * Whenever we needs to get the logged in user information, we fetch from SQLite instead of making request to server.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "saltnpepper";

    // Login table name
    private static final String TABLE_LOGIN = "login";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";

    // order data table name
    private static final String TABLE_ORDER_DATA = "orderdata";

    // order data Table Columns names
    private static final String KEY_ORDER_ID = "OrderId";
    private static final String KEY_SERVER_NAME = "ServerName";
    private static final String KEY_TABLE_NO = "TableNo";
    private static final String KEY_GUEST_COUNT = "GuestCount";
    private static final String KEY_WAITER_NAME = "WaiterName";
    private static final String KEY_ORDER_CONFIRMATION_TIME = "OrderConfirmationTime";
    private static final String KEY_ORDER_SERVED_TIME = "OrderServedTime";
    private static final String KEY_ORDER_COMPLETED = "OrderCompleted";

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);


    // order data table name
    private static final String TABLE_DISH_DATA = "dishdata";

    // order data Table Columns names
    private static final String KEY_DISH_ID = "DishOrdId";
    private static final String KEY_DISH_NAME = "DishName";
    private static final String KEY_DISH_PRICE = "Price";

    // Login table name
    private static final String TABLE_USER = "users";

    // Login Table Columns names
    private static final String KEY_PASSWORD = "userpassword";
    private static final String KEY_UPDATED_AT = "updated_at";



    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE," + KEY_UID + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        String CREATE_ORDER_DATA= "CREATE TABLE " + TABLE_ORDER_DATA + "("
                + KEY_ORDER_ID + " INTEGER PRIMARY KEY ," + KEY_SERVER_NAME + " TEXT,"
                + KEY_TABLE_NO + " TEXT ," + KEY_GUEST_COUNT + " TEXT,"
                + KEY_WAITER_NAME + " TEXT ," + KEY_ORDER_CONFIRMATION_TIME + " TEXT," +
                KEY_ORDER_SERVED_TIME + " TEXT," + KEY_ORDER_COMPLETED + " INTEGER)";
        db.execSQL(CREATE_ORDER_DATA);

        String CREATE_DISH_DATA = "CREATE TABLE " + TABLE_DISH_DATA + "("
                + KEY_DISH_ID + " INTEGER PRIMARY KEY," + KEY_ORDER_ID + " INTEGER,"
                + KEY_DISH_NAME + " TEXT, " + KEY_DISH_PRICE + " TEXT)";
        db.execSQL(CREATE_DISH_DATA);
        /**
         * Table users created
         * */
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE," + KEY_PASSWORD + " TEXT,"
                + KEY_UPDATED_AT + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_DATA);
        // Drop Dish data table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISH_DATA);
        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String name, String email, String uid, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put(KEY_EMAIL, email); // username is email
        values.put(KEY_UID, uid); // Email
        values.put(KEY_CREATED_AT, created_at); // Created At

        // Inserting Row
        long id = db.insert(TABLE_LOGIN, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("uid", cursor.getString(3));
            user.put("created_at", cursor.getString(4));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_LOGIN, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

    /**
     *
     * @param name
     * @param email
     * @param password
     */
    public long registerUser(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Date current = new Date();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put(KEY_EMAIL, email); // username is email
        values.put(KEY_PASSWORD, password);
        values.put(KEY_CREATED_AT, dateFormat.format(current).toString());

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user registered into sqlite: " + id);

        return id;
    }

    /**
     *
     * @param email
     * @param password
     * @return
     */
    public Contact checkLogin(String email , String password){
        String selectQuery = "SELECT id,name, email FROM " + TABLE_USER + " WHERE " + KEY_EMAIL + "='" + email + "'"
                + " and " + KEY_PASSWORD + " = '" + password + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Contact loggedIn = null;
        Cursor c = db.rawQuery(selectQuery, null);
        // Move to first row
        c.moveToFirst();
        String output = "Invalid login";
        if (c.getCount() > 0) {
            loggedIn = new Contact(c.getString((c.getColumnIndex(KEY_NAME))),c.getString((c.getColumnIndex(KEY_EMAIL))));
            output= loggedIn.loggedInUser;
        }
        c.close();
        db.close();
        Log.d(TAG, "Fetching user from Sqlite: " + output);
        return loggedIn;
    }

    /**
     * Storing order details in database
     * */
    public long addOrder(String serverName, String waiterName, String tableNo, String guestCount) {
        SQLiteDatabase db = this.getWritableDatabase();
        Date current = new Date();
        ContentValues values = new ContentValues();
        values.put(KEY_SERVER_NAME, serverName); // Name
        values.put(KEY_WAITER_NAME, waiterName); // username is email
        values.put(KEY_TABLE_NO, tableNo); // Email
        values.put(KEY_GUEST_COUNT, guestCount); // Created At
        values.put(KEY_ORDER_CONFIRMATION_TIME,dateFormat.format(current).toString());
        values.put(KEY_ORDER_COMPLETED,0);
        // Inserting Row
        long id = db.insert(TABLE_ORDER_DATA, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New order inserted into sqlite: " + id);
        return id;
    }

    /**
     * Getting order data from database
     * */
    public HashMap<String, OrderData> getOrderDetails() {
        HashMap<String, OrderData> orderMapping = new HashMap<String, OrderData>();
        String selectQuery = "SELECT  * FROM " + TABLE_ORDER_DATA + " WHERE " + KEY_ORDER_COMPLETED + " < 1" + " ORDER BY " + KEY_TABLE_NO;
        Log.println(Log.INFO, "SQL Query user : ", selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // Move to first row
        c.moveToFirst();
        OrderData orderData;
        Log.println(Log.INFO, "Result Count : " + c.getCount(), " items");
        if (c.getCount() > 0) {
            try {
                do{
                    orderData = new OrderData();
                    orderData.OrderId = c.getInt((c.getColumnIndex(KEY_ORDER_ID)));
                    orderData.TableNo = c.getString((c.getColumnIndex(KEY_TABLE_NO)));
                    orderData.GuestCount = c.getString((c.getColumnIndex(KEY_GUEST_COUNT)));
                    orderData.WaiterName = c.getString((c.getColumnIndex(KEY_WAITER_NAME)));
                    orderData.ServerName = c.getString((c.getColumnIndex(KEY_SERVER_NAME)));
                    orderData.OrderConfirmationTime = c.getString((c.getColumnIndex(KEY_ORDER_CONFIRMATION_TIME)));
                    orderData.OrderServedTime = c.getString((c.getColumnIndex(KEY_ORDER_SERVED_TIME)));
                    orderMapping.put(orderData.TableNo, orderData);
                }while (c.moveToNext());
            }
            catch (Exception ex){
                Log.println(Log.ERROR,"Error detected:", ex.getMessage());
            }
        }
        c.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching order from from Sqlite");

        return orderMapping;
    }

    /**
     * Re create database Delete all tables and create them again
     * */
    public void deleteOrderData() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_ORDER_DATA, null, null);
        db.close();
        Log.d(TAG, "Deleted all user info from sqlite");
    }

    /**
     * function to add dish items for a given order
     * @param dishList
     * @param OrderId
     * @return
     */
    public long addDishData(List<String> dishList, long OrderId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values;
        long dishId = 0;
        for (String dish:dishList){
            values = new ContentValues();
            values.put(KEY_ORDER_ID, OrderId);
            values.put(KEY_DISH_NAME, dish); // Name
            values.put(KEY_DISH_PRICE, 0);
            // Inserting Row
            dishId = db.insert(TABLE_DISH_DATA, null, values);
            Log.d(TAG, "New dish inserted into sqlite: " + dishId);
        }
        db.close(); // Closing database connection
        Log.d(TAG, "Completed dish insertion " + dishId);
        return dishId;
    }

    /**
     * function to get the dishes ordered for a given order id
     * @param orderId
     * @return
     */
    public List<String> GetDishList(long orderId){
        List<String> dishList = new ArrayList<String>();
        String selectQuery = "SELECT DishName FROM " + TABLE_DISH_DATA + " WHERE " + KEY_ORDER_ID + "=" + orderId;
        Log.println(Log.INFO, "SQL Query user : ", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        c.moveToFirst();
        if (c.getCount() > 0) {
            try {
                 do{
                    dishList.add(c.getString((c.getColumnIndex(KEY_DISH_NAME))));
                }while (c.moveToNext());
            }
            catch(Exception ex){
                Log.println(Log.ERROR,"Error occured !",ex.getMessage().toString());
            }
        }
        db.close();
        return dishList;
    }

    public int SetOrderServed(long orderId){
        SQLiteDatabase db = this.getWritableDatabase();
        Date current = new Date();
        ContentValues values = new ContentValues();
        values.put(KEY_ORDER_SERVED_TIME, dateFormat.format(current).toString());
        int rwCnt = db.update(TABLE_ORDER_DATA,values,KEY_ORDER_ID + "=?",new String[]{Integer.toString((int)orderId)});
        db.close();
        return rwCnt;
    }

    public int SetOrderCompleted(long orderId){
        SQLiteDatabase db = this.getWritableDatabase();
        Date current = new Date();
        ContentValues values = new ContentValues();
        values.put(KEY_ORDER_COMPLETED,1 );
        int rwCnt = db.update(TABLE_ORDER_DATA,values,KEY_ORDER_ID + "=?",new String[]{Integer.toString((int) orderId)});
        db.close();
        return rwCnt;
    }
}