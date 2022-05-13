package com.example.splash.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Password_Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "pass_data.db";
    public static final String TABLE_NAME = "password_table";
    public static final String col1 = "password";

    public Password_Database(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE password_table( password TEXT  )  ");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS password_table");
    }

    public boolean insertData(String str) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col1, str);
        long insert = writableDatabase.insert(TABLE_NAME, (String) null, contentValues);
        writableDatabase.close();
        return insert != -1;
    }

    public Cursor getAllData() {
        return getWritableDatabase().rawQuery("Select * from password_table", (String[]) null);
    }

    public boolean updateData(String str, String str2) {
        getWritableDatabase().update(TABLE_NAME, new ContentValues(), "password =?", new String[]{str});
        return true;
    }

    public Integer deleteData(String str) {
        return Integer.valueOf(getWritableDatabase().delete(TABLE_NAME, "password =?", new String[]{str}));
    }
}
