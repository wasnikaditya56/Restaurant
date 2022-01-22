package com.aditya.restaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.sql.Types.BOOLEAN;

public class DBController extends SQLiteOpenHelper
{
    // All Static variables
    public Context context;

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "RestaurantDatabase.db";


    //******** table NAme for user credential Register Activity ********************
    private static final String TABLE_USER_CREDENTIAL = "table_user_credential";
    private static final String USER_CREDENTIAL_ID    = "user_credential_id";
    private static final String USERNAME              = "username";
    private static final String EMAIL                 = "email";
    private static final String MOBILE_NUMBER         = "mobile_number";
    private static final String PASSWORD              = "password";


    public DBController(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) //PRIMARY KEY
    {

        String CREATE_TABLE_USER_CREDENTIAL = "CREATE TABLE " + TABLE_USER_CREDENTIAL+ "(" + USER_CREDENTIAL_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," + EMAIL + " TEXT ," + USERNAME + " TEXT, " + MOBILE_NUMBER + " TEXT," + PASSWORD + " TEXT )";
        db.execSQL(CREATE_TABLE_USER_CREDENTIAL);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_CREDENTIAL);

        // Create tables again
        onCreate(db);
    }


    //************** To store data in sqlite database of User Credential signup activity********************
    public void insertUserCredential( String email , String username, String mobileNumber, String password)
    {
        System.out.println("IN HERE::::1128 ");
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        ContentValues values = new ContentValues();
        values.put(EMAIL, email);
        values.put(USERNAME, username);
        values.put(MOBILE_NUMBER, mobileNumber);
        values.put(PASSWORD, password);
        db.insert(TABLE_USER_CREDENTIAL, null, values);
        System.out.println("TABLE_USER_CREDENTIAL::::: "+ values);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close(); // Closing database connection
    }


    public String getUserCredential()
    {
        String email, password;
        String userCredential="", saveData = "";

        int cnt=0;
        String selectQuery = "SELECT * FROM " + TABLE_USER_CREDENTIAL;
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor      = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do
            {
                email = (cursor.getString(1));
                password= (cursor.getString(4));
                userCredential += "\n"+ email  + ">" +  password ;
                cnt++;
            }
            while (cursor.moveToNext());
        }
        saveData = userCredential +"#:#:#" + cnt ;
        cursor.close();
        return saveData;
    }

}




