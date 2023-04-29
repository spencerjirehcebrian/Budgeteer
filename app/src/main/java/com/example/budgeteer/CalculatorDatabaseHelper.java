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
import java.util.ArrayList;
import java.util.List;

public class CalculatorDatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "UserManager.db";
    // User table name
    private static final String TABLE_BUDGET = "budget";
    // User Table Columns names
    private static final String COLUMN_BUDGET_ID = "budget_id";
    private static final String COLUMN_BUDGET_USER_EMAIL = "budget_user_email";
    private static final String COLUMN_BUDGET_CLASS = "budget_class";
    private static final String COLUMN_BUDGET_MAX = "budget_max";
    private static final String COLUMN_BUDGET_EXPENSES = "budget_expenses";

    // create table sql query
    private String CREATE_BUDGET_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_BUDGET + "("
            + COLUMN_BUDGET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_BUDGET_USER_EMAIL + " TEXT," + COLUMN_BUDGET_CLASS + " TEXT," + COLUMN_BUDGET_MAX + " INTEGER," + COLUMN_BUDGET_EXPENSES + " INTEGER" + ")";
    // drop table sql query
    private String DROP_BUDGET_TABLE = "DROP TABLE IF EXISTS " + TABLE_BUDGET;
    /**
     * Constructor
     *
     * @param context
     */
    public CalculatorDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BUDGET_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist
        db.execSQL(DROP_BUDGET_TABLE);
        // Create tables again
        onCreate(db);
    }
    /**
     * This method is to create user record
     *
     * @param budget
     */
    public void addBudget(Budget budget) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(CREATE_BUDGET_TABLE);

        ContentValues values = new ContentValues();
        values.put(COLUMN_BUDGET_USER_EMAIL, budget.getUserEmail());
        values.put(COLUMN_BUDGET_CLASS, budget.get_Class());
        values.put(COLUMN_BUDGET_MAX, budget.getMax());
        values.put(COLUMN_BUDGET_EXPENSES, budget.getExpenses());
        // Inserting Row
        db.insert(TABLE_BUDGET, null, values);
        //db.close();
    }
    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    @SuppressLint("Range")
    public ArrayList<Budget> getAllBudget(String emailSearch) {
        String[] emailString = new String[]{String.valueOf(emailSearch)};
        // array of columns to fetch
        String[] columns = {
                COLUMN_BUDGET_ID,
                COLUMN_BUDGET_USER_EMAIL,
                COLUMN_BUDGET_CLASS,
                COLUMN_BUDGET_MAX,
                COLUMN_BUDGET_EXPENSES,
        };
//        // sorting orders
//        String sortOrder =
//                COLUMN_BUDGET_USER_EMAIL + " ASC";
        ArrayList<Budget> budgetList = new ArrayList<Budget>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(CREATE_BUDGET_TABLE);

        String selection =  COLUMN_BUDGET_USER_EMAIL + "=?";
        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_BUDGET, //Table to query
                columns,    //columns to return
                selection,        //columns for the WHERE clause
                emailString,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order

        String querySel = "SELECT * FROM budget WHERE budget_user_email = '"+emailSearch+"';";
        Cursor cursor1 = db.rawQuery(querySel, null);
        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Budget budget = new Budget();
                budget.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BUDGET_ID))));
                budget.setUserEmail(cursor.getString(cursor.getColumnIndex(COLUMN_BUDGET_USER_EMAIL)));
                budget.set_Class(cursor.getString(cursor.getColumnIndex(COLUMN_BUDGET_CLASS)));
                budget.setMax(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BUDGET_MAX))));
                budget.setExpenses(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BUDGET_EXPENSES))));
                // Adding user record to list
                budgetList.add(budget);
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();
        // return user list
        return budgetList;
    }

    public Integer getTopID () {
        // array of columns to fetch
        String[] columns = {
                COLUMN_BUDGET_ID,
                COLUMN_BUDGET_USER_EMAIL,
                COLUMN_BUDGET_CLASS,
                COLUMN_BUDGET_MAX,
                COLUMN_BUDGET_EXPENSES,
        };
//        // sorting orders
//        String sortOrder =
//                COLUMN_BUDGET_USER_EMAIL + " ASC";
        ArrayList<Budget> budgetList = new ArrayList<Budget>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(CREATE_BUDGET_TABLE);

        String selection =  COLUMN_BUDGET_USER_EMAIL + "=?";
        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */

        String querySel = "SELECT MAX(budget_id) FROM budget;";
        Cursor cursor = db.rawQuery(querySel, null);

        Integer returnValue = 0;

        if (cursor.moveToFirst()) returnValue = cursor.getInt(0);

        cursor.close();
        //db.close();
        // return user list
        return returnValue;
    }
    /**
     * This method to update user record
     *
     * @param budget
     */
    public void updateBudget(Budget budget, Integer budgetID) {
        String[] budgetIDString = new String[]{String.valueOf(budgetID)};

        //Log.d("wapapapa", budgetID.toString() + " " + budgetIDString.toString());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BUDGET_USER_EMAIL, budget.getUserEmail());
        values.put(COLUMN_BUDGET_CLASS, budget.get_Class());
        values.put(COLUMN_BUDGET_MAX, budget.getMax());
        values.put(COLUMN_BUDGET_EXPENSES, budget.getExpenses());
        // updating row
        db.update(TABLE_BUDGET, values, COLUMN_BUDGET_ID + " = ? ", budgetIDString);
        //db.close();
    }
    /**
     * This method is to delete user record
     *
     * @param budget
     */
    public void deleteBudget(Budget budget) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_BUDGET, COLUMN_BUDGET_ID + " = ?",
                new String[]{String.valueOf(budget.getId())});
        //db.close();
    }
    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkBudgetExist(String email) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_BUDGET_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(CREATE_BUDGET_TABLE);
        // selection criteria
        String selection = COLUMN_BUDGET_USER_EMAIL + " = ?";
        // selection argument
        String[] selectionArgs = {email};
        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_BUDGET, //Table to query
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
