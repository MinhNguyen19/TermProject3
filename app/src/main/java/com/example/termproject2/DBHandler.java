package com.example.termproject2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.termproject2.MainActivity;
import com.example.termproject2.User;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Project.db";

    public static final String USER = "Users";
    public static final String USERID = "userID";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String ISINSTRUCTOR = "isInstructor";

    public static final String CLASS = "Classes";
    public static final String CLASSID = "classID";
    public static final String INSTRUCTOR = "instructor";
    public static final String DAY = "day";
    public static final String TIME_START = "timeStart";
    public static final String TIME_END = "timeEnd";
    public static final String DIFFICULTY = "difficulty";
    public static final String TYPE = "type";
    public static final String CAPACITY = "capacity";

    public static final String ENROL_AND_CHOOSE = "EnrolAndChoose";
    public static final String EAC_ID = "id";
    public static final String EAC_USERID = "userID";
    public static final String EAC_CLASSID = "classID";

    public DBHandler (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_USER = "CREATE TABLE " + USER + "("
                + USERID + " INTEGER PRIMARY KEY,"
                + USERNAME + " TEXT,"
                + PASSWORD + " TEXT,"
                + ISINSTRUCTOR + " INTEGER"
                + ")";
        db.execSQL(CREATE_USER);

        String CREATE_CLASS = "CREATE TABLE " + CLASS + "("
                + CLASSID + " INTEGER PRIMARY KEY,"
                + INSTRUCTOR + " INTEGER,"
                + DAY + " TEXT,"
                + TIME_START + " TEXT,"
                + TIME_END + " TEXT,"
                + DIFFICULTY + " TEXT,"
                + TYPE + " TEXT,"
                + CAPACITY + " INTEGER"
                + ")";
        db.execSQL(CREATE_CLASS);

        String CREATE_ENROL_AND_CHOOSE = "CREATE TABLE " + ENROL_AND_CHOOSE + "("
                + EAC_ID + " INTEGER PRIMARY KEY,"
                + EAC_USERID + " INTEGER,"
                + EAC_CLASSID  + " INTEGER,"
                + "FOREIGN KEY (" + EAC_USERID + ") REFERENCES " + USER + "(" + USERID + "),"
                + "FOREIGN KEY (" + EAC_CLASSID + ") REFERENCES " + CLASS + "(" + CLASSID + ")"
                + ")";
        db.execSQL(CREATE_ENROL_AND_CHOOSE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + USER);
        db.execSQL("DROP TABLE IF EXISTS " + CLASS);
        db.execSQL("DROP TABLE IF EXISTS " + ENROL_AND_CHOOSE);

        onCreate(db);
    }

    public void addUser (User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USERID, user.getId());
        values.put(USERNAME, user.getUsername());
        values.put(PASSWORD, user.getPassword());
        values.put(ISINSTRUCTOR, user.isInstructor());

        db.insert(USER, null, values);
        db.close();
    }

    public int verifyUser (String username, String password) {
        String query = "Select * FROM " + USER + " Where " +  USERNAME + " =\"" + username + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        User user = new User();
        if(cursor.moveToFirst()){
            user.setId(Integer.parseInt(cursor.getString(0)));
            user.setUsername(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            user.setIsInstructor(Integer.parseInt(cursor.getString(3)));
            cursor.close();
        } else
        {
            user = null;
        }
        if (user.getPassword().equals(password)) {
            return 1;
        } else
            return 0;
    }




//    public void addProduct (Product product){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_PRODUCTNAME, product.getProductName());
//        values.put(COLUMN_PRICE, product.getPrice());
//
//        db.insert(TABLE_PRODUCTS, null, values);
//        db.close();
//    }
//    public Product findProduct(String productname){
//        String query = "Select * FROM " + TABLE_PRODUCTS + " Where " + COLUMN_PRODUCTNAME + " =\"" + productname + "\"";
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        Product product = new Product();
//        if(cursor.moveToFirst()){
//            product.setID(Integer.parseInt(cursor.getString(0)));
//            product.setProductName(cursor.getString(1));
//            product.setPrice(Integer.parseInt(cursor.getString(2)));
//            cursor.close();
//        }
//        else
//        {
//            product = null;
//        }
//        db.close();
//        return product;
//    }
//
//    public boolean deleteProduct(String productname) {
//        boolean result = false;
//        String query = "Select * FROM " + TABLE_PRODUCTS + " Where " + COLUMN_PRODUCTNAME + "=\"" + productname + "\"";
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//
//        if(cursor.moveToFirst()) {
//            String idStr = cursor.getString(0);
//            db.delete(TABLE_PRODUCTS, COLUMN_ID+ " = " + idStr, null);
//            cursor.close();
//            result = true;
//        }
//
//        db.close();
//        return result;
//    }
//    public Cursor viewData() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String query = "SELECT * FROM " + TABLE_PRODUCTS;
//        Cursor cursor = db.rawQuery(query, null);
//        return cursor;
//    }
}