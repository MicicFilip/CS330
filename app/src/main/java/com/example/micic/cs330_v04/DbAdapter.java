package com.example.micic.cs330_v04;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Micic on 29-Mar-17.
 */

public class DbAdapter {

    static final String KEY_ID = "id";
    static final String KEY_NAME = "username";
    static final String KEY_EMAIL = "email";
    static final String TAG = "DbAdapter";

    static final String DATABASE_NAME = "cs330db";
    static final String DATABASE_TABLE = "kontakti";
    static final int DATABASE_VERSION = 3;

    static final String DATABASE_CREATE = "CREATE TABLE kontakti (id integer primary key autoincrement, " + "username text not null, email text not null";

    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DbAdapter(Context context) {
        this.context = context;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Ažuriranje verzije baze podataka sa " + oldVersion + " na verziju " + newVersion + ", a to će uništiti postojeće podatke");
            db.execSQL("DROP TABLE IF EXISTS kontakti");
            onCreate(db);
        }
    }

    //---otvaranje baze podataka--
    public DbAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---zatvaranje baze podataka
    public void close() {
        DBHelper.close();
    }

    public long insertContact(String username, String email) {
        ContentValues initalValues = new ContentValues();
        initalValues.put(KEY_NAME, username);
        initalValues.put(KEY_EMAIL, email);

        return db.insert(DATABASE_TABLE, null, initalValues);
    }

    //---brisanje konkretnog kontakta
    public boolean deleteContact(long rowId) {
        return db.delete(DATABASE_TABLE, KEY_ID + "=" + rowId, null) > 0;
    }

    // listanje svih kontakta iz baze
    public Cursor getAllContacts() {
        return db.query(DATABASE_TABLE, new String[]{KEY_ID, KEY_NAME, KEY_EMAIL}, null, null, null, null, null);
    }

    // get-ovanje jednog kontakta
    public Cursor getContact(long rowId) throws SQLException {
        Cursor mCursor = db.query(true, DATABASE_TABLE, new String[]{KEY_ID, KEY_NAME, KEY_EMAIL}, KEY_ID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    // upate-ovanje kontakta
    public boolean updateContact(long rowId, String username, String email) {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, username);
        args.put(KEY_EMAIL, email);
        return db.update(DATABASE_TABLE, args, KEY_ID + "=" + rowId, null) > 0;
    }


}
