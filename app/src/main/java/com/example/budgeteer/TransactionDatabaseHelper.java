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
import java.util.ArrayList;
import java.util.List;

public class TransactionDatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "UserManager.db";
    // User table name
    private static final String TABLE_TRANSACTION = "transactions";
    // User Table Columns names
    private static final String COLUMN_TRANSACTION_ID = "transaction_id";
    private static final String COLUMN_TRANSACTION_USER_EMAIL = "transaction_user_email";
    private static final String COLUMN_TRANSACTION_CLASS = "transaction_class";
    private static final String COLUMN_TRANSACTION_AMOUNT = "transaction_amount";
    private static final String COLUMN_TRANSACTION_DATE= "transaction_date";

    private static final String COLUMN_TRANSACTION_TIME= "transaction_time";

    // create table sql query
    private String CREATE_TRANSACTION_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_TRANSACTION + "("
            + COLUMN_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TRANSACTION_USER_EMAIL + " TEXT," + COLUMN_TRANSACTION_CLASS + " TEXT," + COLUMN_TRANSACTION_AMOUNT + " INTEGER,"+ COLUMN_TRANSACTION_TIME + " TEXT," + COLUMN_TRANSACTION_DATE + " TEXT" + ")";
    // drop table sql query
    private String DROP_TRANSACTION_TABLE = "DROP TABLE IF EXISTS " + TABLE_TRANSACTION;
    /**
     * Constructor
     *
     * @param context
     */
    public TransactionDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TRANSACTION_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist
        db.execSQL(DROP_TRANSACTION_TABLE);
        // Create tables again
        onCreate(db);
    }
    /**
     * This method is to create user record
     *
     * @param transactions
     */
    public void addTransaction(Transactions transactions) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.execSQL(CREATE_TRANSACTION_TABLE);

        values.put(COLUMN_TRANSACTION_USER_EMAIL, transactions.getUserEmail());
        values.put(COLUMN_TRANSACTION_CLASS, transactions.get_Class());
        values.put(COLUMN_TRANSACTION_AMOUNT, transactions.getAmount());
        values.put(COLUMN_TRANSACTION_DATE, transactions.getDate());
        values.put(COLUMN_TRANSACTION_TIME, transactions.getTime());
        // Inserting Row
        db.insert(TABLE_TRANSACTION, null, values);
        db.close();
    }
    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    @SuppressLint("Range")
    public ArrayList<Transactions> getAllTransaction(String emailSearch) {
        String[] emailString = new String[]{String.valueOf(emailSearch)};
        // array of columns to fetch
        String[] columns = {
                COLUMN_TRANSACTION_ID,
                COLUMN_TRANSACTION_USER_EMAIL,
                COLUMN_TRANSACTION_CLASS,
                COLUMN_TRANSACTION_AMOUNT,
                COLUMN_TRANSACTION_DATE,
                COLUMN_TRANSACTION_TIME
        };
//        // sorting orders
//        String sortOrder =
//                COLUMN_BUDGET_USER_EMAIL + " ASC";
        ArrayList<Transactions> transactionsList = new ArrayList<Transactions>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(CREATE_TRANSACTION_TABLE);

        String selection =  COLUMN_TRANSACTION_USER_EMAIL + "=?";
        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_TRANSACTION, //Table to query
                columns,    //columns to return
                selection,        //columns for the WHERE clause
                emailString,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order

        String querySel = "SELECT * FROM transactions WHERE transaction_user_email = '"+emailSearch+"';";
        Cursor cursor1 = db.rawQuery(querySel, null);
        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Transactions transactions = new Transactions();
                transactions.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_ID))));
                transactions.setUserEmail(cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_USER_EMAIL)));
                transactions.set_Class(cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_CLASS)));
                transactions.setAmount(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_AMOUNT))));
                transactions.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_DATE)));
                transactions.setTime(cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_TIME)));
                // Adding user record to list
                transactionsList.add(transactions);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return transactionsList;
    }
    /**
     * This method to update user record
     *
     * @param transactions
     */
    public void updateBudget(Transactions transactions, Integer budgetID) {
        String[] budgetIDString = new String[]{String.valueOf(budgetID)};

        //Log.d("wapapapa", budgetID.toString() + " " + budgetIDString.toString());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TRANSACTION_USER_EMAIL, transactions.getUserEmail());
        values.put(COLUMN_TRANSACTION_CLASS, transactions.get_Class());
        values.put(COLUMN_TRANSACTION_AMOUNT, transactions.getAmount());
        values.put(COLUMN_TRANSACTION_DATE, transactions.getDate());
        values.put(COLUMN_TRANSACTION_TIME, transactions.getTime());
        // updating row
        db.update(TABLE_TRANSACTION, values, COLUMN_TRANSACTION_ID + " = ? ", budgetIDString);
        db.close();
    }
    /**
     * This method is to delete user record
     *
     * @param transactions
     */
    public void deleteBudget(Transactions transactions) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_TRANSACTION, COLUMN_TRANSACTION_ID + " = ?",
                new String[]{String.valueOf(transactions.getId())});
        db.close();
    }
    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_TRANSACTION_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_TRANSACTION_USER_EMAIL + " = ?";
        // selection argument
        String[] selectionArgs = {email};
        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_TRANSACTION, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        //TODO: add the return budgets here
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }
}
