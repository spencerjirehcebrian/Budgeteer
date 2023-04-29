package com.example.budgeteer;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.budgeteer.User;
import com.example.budgeteer.Budget;
import com.example.budgeteer.Transactions;
import com.example.budgeteer.Notifications;
import java.util.ArrayList;
import java.util.List;

public class NotificationDatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "UserManager.db";
    // User table name
    private static final String TABLE_NOTIFICATION = "notifications";
    // User Table Columns names
    private static final String COLUMN_NOTIFICATION_ID = "notification_id";

    private static final String COLUMN_NOTIFICATION_BUDGET_ID = "notification_budget_id";
    private static final String COLUMN_NOTIFICATION_USER_EMAIL = "notification_user_email";
    private static final String COLUMN_NOTIFICATION_CLASS = "notification_class";
    private static final String COLUMN_NOTIFICATION_PERCENT= "notification_percent";
    private static final String COLUMN_NOTIFICATION_DATE= "notification_date";
    private static final String COLUMN_NOTIFICATION_TIME= "notification_time";


    // create table sql query
    private String CREATE_NOTIFICATION_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NOTIFICATION + "("
            + COLUMN_NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NOTIFICATION_BUDGET_ID + " INTEGER," + COLUMN_NOTIFICATION_USER_EMAIL + " TEXT," + COLUMN_NOTIFICATION_CLASS + " TEXT,"+ COLUMN_NOTIFICATION_PERCENT + " INTEGER," + COLUMN_NOTIFICATION_DATE + " TEXT," + COLUMN_NOTIFICATION_TIME + " TEXT" + ")";
    // drop table sql query
    private String DROP_NOTIFICATION_TABLE = "DROP TABLE IF EXISTS " + TABLE_NOTIFICATION;
    /**
     * Constructor
     *
     * @param context
     */
    public NotificationDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NOTIFICATION_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist
        db.execSQL(DROP_NOTIFICATION_TABLE);
        // Create tables again
        onCreate(db);
    }
    /**
     * This method is to create user record
     *
     * @param notifications
     */
    public void addNotification(Notifications notifications) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.execSQL(CREATE_NOTIFICATION_TABLE);
        values.put(COLUMN_NOTIFICATION_BUDGET_ID, notifications.getBudgetID());
        values.put(COLUMN_NOTIFICATION_USER_EMAIL, notifications.getUserEmail());
        values.put(COLUMN_NOTIFICATION_CLASS, notifications.get_Class());
        values.put(COLUMN_NOTIFICATION_PERCENT, notifications.getPercent());
        values.put(COLUMN_NOTIFICATION_DATE, notifications.getDate());
        values.put(COLUMN_NOTIFICATION_TIME, notifications.getTime());
        // Inserting Row
        db.insert(TABLE_NOTIFICATION, null, values);
        //db.close();
    }
    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    @SuppressLint("Range")
    public ArrayList<Notifications> getAllNotifications(String emailSearch) {
        String[] emailString = new String[]{String.valueOf(emailSearch)};
        // array of columns to fetch
        String[] columns = {
                COLUMN_NOTIFICATION_ID,
                COLUMN_NOTIFICATION_BUDGET_ID,
                COLUMN_NOTIFICATION_USER_EMAIL,
                COLUMN_NOTIFICATION_CLASS,
                COLUMN_NOTIFICATION_PERCENT,
                COLUMN_NOTIFICATION_DATE,
                COLUMN_NOTIFICATION_TIME
        };
//        // sorting orders
//        String sortOrder =
//                COLUMN_BUDGET_USER_EMAIL + " ASC";
        ArrayList<Notifications> notificationsList = new ArrayList<Notifications>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(CREATE_NOTIFICATION_TABLE);

        String selection =  COLUMN_NOTIFICATION_USER_EMAIL + "=?";
        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor1 = db.query(TABLE_NOTIFICATION, //Table to query
                columns,    //columns to return
                selection,        //columns for the WHERE clause
                emailString,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order

        String querySel = "SELECT * FROM notifications WHERE notification_user_email = '"+emailSearch+"' AND notification_percent >= 50;";
        Cursor cursor = db.rawQuery(querySel, null);
        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Notifications notifications = new Notifications();
                notifications.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_NOTIFICATION_ID))));
                notifications.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_NOTIFICATION_BUDGET_ID))));
                notifications.setUserEmail(cursor.getString(cursor.getColumnIndex(COLUMN_NOTIFICATION_USER_EMAIL)));
                notifications.set_Class(cursor.getString(cursor.getColumnIndex(COLUMN_NOTIFICATION_CLASS)));
                notifications.setPercent(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_NOTIFICATION_PERCENT))));
                notifications.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_NOTIFICATION_DATE)));
                notifications.setTime(cursor.getString(cursor.getColumnIndex(COLUMN_NOTIFICATION_TIME)));
                // Adding user record to list
                notificationsList.add(notifications);
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();
        // return user list
        return notificationsList;
    }
    /**
     * This method to update user record
     *
     * @param notifications
     */
    public void updateNotifications(Notifications notifications, Integer notifID) {
        String[] notifIDString = new String[]{String.valueOf(notifID)};

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTIFICATION_USER_EMAIL, notifications.getUserEmail());
        values.put(COLUMN_NOTIFICATION_BUDGET_ID, notifications.getBudgetID());
        values.put(COLUMN_NOTIFICATION_CLASS, notifications.get_Class());
        values.put(COLUMN_NOTIFICATION_PERCENT, notifications.getPercent());
        values.put(COLUMN_NOTIFICATION_DATE, notifications.getDate());
        values.put(COLUMN_NOTIFICATION_TIME, notifications.getTime());
        // updating row
        db.update(TABLE_NOTIFICATION, values, COLUMN_NOTIFICATION_BUDGET_ID + " = ? ", notifIDString);
        //db.close();
    }
    /**
     * This method is to delete user record
     *
     * @param transactions
     */

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_NOTIFICATION_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_NOTIFICATION_USER_EMAIL
                + " = ?";
        // selection argument
        String[] selectionArgs = {email};
        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_NOTIFICATION, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        //db.close();
        //TODO: add the return budgets here
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }
}
