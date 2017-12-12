package com.niket.ToDoNiket;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static com.niket.ToDoNiket.DatabaseHelper.DESC;
import static com.niket.ToDoNiket.DatabaseHelper.DUEDATE;
import static com.niket.ToDoNiket.DatabaseHelper.STATUS;
import static com.niket.ToDoNiket.DatabaseHelper.TABLE_NAME;

/**
 * DBManager class to handle the CURD operation on database table
 */
public class DBManager {
    //Declaring objects to perform CURD operations.

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * Inserting all data into the table
     * @param name
     * @param desc
     * @param dueDate
     * @param status
     */
    public void insert(String name, String desc, String dueDate, String status) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.SUBJECT, name);
        contentValue.put(DESC, desc);
        contentValue.put(DatabaseHelper.DUEDATE, dueDate);
        contentValue.put(DatabaseHelper.STATUS, status);
        database.insert(TABLE_NAME, null, contentValue);
    }

    /**
     * Fetching data using fetch() method order by date in ascending order
     * @return
     */
    public Cursor fetch() {
        String[] columns = new String[]
                { DatabaseHelper._ID, DatabaseHelper.SUBJECT,
                        DESC, DatabaseHelper.DUEDATE, DatabaseHelper.STATUS };

        String selectQuery = ("SELECT * FROM "+ TABLE_NAME + " ORDER BY "
                + DUEDATE + " ASC " ) ;
       // Cursor cursor = database.rawquerry(selectQuery,null);//database.query(TABLE_NAME, columns, null, null, null, null, null);
        Cursor cursor =  database.rawQuery(selectQuery, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    // To fetch the completed task list

    public Cursor taskCompletedFetch() {
        String[] columns = new String[]
                { DatabaseHelper._ID, DatabaseHelper.SUBJECT,
                        DESC, DatabaseHelper.DUEDATE, DatabaseHelper.STATUS };


        Cursor cursor = database.rawQuery(" SELECT * FROM " + TABLE_NAME +
        " WHERE " + STATUS + "= ? ", new String[] { "1" });
       // Cursor cursor =  database.rawQuery(selectQuery, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    /**
     * Updating table method to perform update operations
     * @param _id
     * @param name
     * @param desc
     * @param dueDate
     * @param status
     * @return
     */
    public int update(long _id, String name, String desc, String dueDate, String status) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.SUBJECT, name);
        contentValues.put(DESC, desc);
        contentValues.put(DatabaseHelper.DUEDATE, dueDate);
        contentValues.put(DatabaseHelper.STATUS, status);
        int i = database.update(TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    /**
     * handling deletion of records
     * @param _id
     */
    public void delete(long _id) {
        database.delete(TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }

}
