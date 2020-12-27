package com.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/*
服务器数据库的类（虽然没有后端，假装有）
 */
public class RemotoDatabase extends SQLiteOpenHelper {

    private Context context = null;

    private static final String DB_NAME = "RemotoDatebase.db";
    private static RemotoDatabase remotoDatabase = null;

    public static synchronized RemotoDatabase getInstance(Context context) {
        if (remotoDatabase == null) {
            remotoDatabase = new RemotoDatabase(context);
        }
        return remotoDatabase;
    }

    private RemotoDatabase(Context context) {
        //创建数据库
        super(context, DB_NAME, null, 2);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //super(context,"RemotoDatebase.db",null,2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void updateSqlite(String table, String IdClonmName, String id, String[] keys, String[] value) {
        SQLiteDatabase sqliteDatabase = remotoDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        int step = 0;
        for (String key : keys) {
            values.put(key, value[step]);
            step++;
        }
        sqliteDatabase.update(table, values, IdClonmName + "=?", new String[]{id});
        sqliteDatabase.close();
    }

    public void delSqlite(String table, String whereClause, String []whereArgs) {
        SQLiteDatabase sqliteDatabase = remotoDatabase.getWritableDatabase();
        sqliteDatabase.delete(table, whereClause, whereArgs);
        sqliteDatabase.close();
    }

    public void addSqllite(String table, ArrayList<ContentValues> contentValues) {
        SQLiteDatabase sqliteDatabase = remotoDatabase.getWritableDatabase();
        for (ContentValues contentValue : contentValues) {
            sqliteDatabase.insert(table, null, contentValue);
        }
        sqliteDatabase.close();
    }

    public void addSqllite(String table, ContentValues contentValue) {
        SQLiteDatabase sqliteDatabase = remotoDatabase.getWritableDatabase();
        sqliteDatabase.insert(table, null, contentValue);
        sqliteDatabase.close();
    }
}
