package com.example.splash.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Apply_password_on_AppDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "logg.db";
    public static final String TABLE_NAME = "apps";
    public static final String col1 = "package_name";
    public static final String col2 = "password";

    public Apply_password_on_AppDatabase(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE apps( package_name TEXT PRIMARY KEY , password TEXT )  ");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS apps");
    }

    public boolean insertData(String str, String str2) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col1, str);
        contentValues.put(col2, str2);
        long insert = writableDatabase.insert(TABLE_NAME, (String) null, contentValues);
        writableDatabase.close();
        return insert != -1;
    }

    public Cursor getAllData() {
        return getWritableDatabase().rawQuery("Select * from apps", (String[]) null);
    }

    public boolean updateData(String str, String str2) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", str2);
        writableDatabase.update(TABLE_NAME, contentValues, "package_name =?", new String[]{str});
        return true;
    }

    public Integer deleteData(String str) {
        return Integer.valueOf(getWritableDatabase().delete(TABLE_NAME, "package_name =?", new String[]{str}));
    }
}
