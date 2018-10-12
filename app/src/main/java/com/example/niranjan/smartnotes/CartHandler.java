package com.example.niranjan.smartnotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niranjan on 11/4/18.
 */

public class CartHandler extends SQLiteOpenHelper {
    // All Static variables
public static CartHandler instance;
// Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "cartDb";

    // Contacts table name
    private static final String TABLE_orders = "orders";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "subjectname";
    private static final String KEY_TITLE = "title";
    private static final String KEY_LINK = "link";
    private static final String KEY_PRICE = "price";
    public static synchronized CartHandler getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (instance == null) {
            instance = new CartHandler(context.getApplicationContext());
        }
        return instance;
    }



    public CartHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ORDERS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_orders + "("
                + KEY_ID + " TEXT PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_TITLE + " TEXT," + KEY_PRICE + " TEXT," + KEY_LINK + " TEXT" + ")";
        db.execSQL(CREATE_ORDERS_TABLE);
        Log.d("CREATE TABLE","Create Table Successfully");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_orders);

        // Create tables again
        onCreate(db);
    }


   public void addtocart(DataModel dataModel) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_PRICE, dataModel.getPrice());
            values.put(KEY_TITLE, dataModel.getNotes_title());
            values.put(KEY_LINK, dataModel.getLink());

            values.put(KEY_ID, dataModel.getId_());
            values.put(KEY_NAME, dataModel.getSubject_name());


            // Inserting Row
            db.insert(TABLE_orders, null, values);
            db.close(); // Closing database connection
        }catch (SQLException e){
            Log.e("DB Error", e.toString());
            e.printStackTrace();
        }


    }




    public ArrayList<DataModel> getAllOrders() {
        ArrayList<DataModel> orderList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_orders;

        SQLiteDatabase db =   getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
               String notes_title=cursor.getString(cursor.getColumnIndex(KEY_TITLE));
                String sub_name=cursor.getString(cursor.getColumnIndex(KEY_NAME));
                String id=cursor.getString(cursor.getColumnIndex(KEY_ID));
                String link=cursor.getString(cursor.getColumnIndex(KEY_LINK));
              String price=cursor.getString(cursor.getColumnIndex(KEY_PRICE));

                orderList.add( new DataModel(notes_title,sub_name,id,price,link));

            } while (cursor.moveToNext());

        }
        Log.d("data heee","done");
        cursor.close();
        return orderList;

    }

public ArrayList<String> getAllOrderId(){
        ArrayList<String> str=new ArrayList<String>();
    String selectQuery = "SELECT "+KEY_ID+"FROM " + TABLE_orders;

    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);

    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
        do {

            String id=cursor.getString(0);
            str.add(id);
        } while (cursor.moveToNext());
    }


        return  str;
}



    public void deleteorder(DataModel dataModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_orders, KEY_ID + " = ?",
                new String[] {dataModel.getId_() });
        db.close();
    }
public  void deleteTable(){
    SQLiteDatabase db=this.getWritableDatabase();
    db.delete(TABLE_orders,null,null);
    db.close();
}


    public int getProductsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_orders;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);


        // return count
       int count= cursor.getCount();
        cursor.close();
        return count;
    }}