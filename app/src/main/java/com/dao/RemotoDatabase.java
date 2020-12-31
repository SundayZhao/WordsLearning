package com.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
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


    private static final String CREATE_DiffCollection="CREATE TABLE DiffCollection" +
            " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " CollectionVersion INT (32) , " +
            "uuid INT (30), " +
            "word STRING (10));";

    private static final String CREATE_WrongCollection="CREATE TABLE WrongCollection" +
            " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "CollectionVersion INT , " +
            "uuid INT, " +
            "word STRING);";

    private static final String CREATE_LearnPlan="CREATE TABLE LearnPlan " +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "createTime STRING, " +
            "LearnPlanId INT UNIQUE, " +
            "wordBookId INTEGER, " +
            "hasLearned INT, " +
            "learndaily INT, "+
            "weChartOrderId STRING, " +
            "last_Signin TEXT," +
            "studylog TEXT );";

    private static final String CREATE_Preference="CREATE TABLE Preference " +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "preferenceId INT NOT NULL, " +
            "studyMode INT, " +
            "studyInOrder INT, " +
            "pronunciation INT, " +
            "autoPlay INT);";

    private static final String CREATE_USER="CREATE TABLE user " +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "uuid INT UNIQUE, " +
            "username STRING UNIQUE, "+
            "password STRING, "+
            "nickname STRING, " +
            "LearnPlanId INT, " +
            "WrongCollectionId INT, " +
            "DiffCollectionID INT, " +
            "phoneNum INT, " +
            "email STRING, " +
            "preferenceId INT, " +
            "headImage STRING);";

    public static synchronized RemotoDatabase getInstance(Context context) {
        if (remotoDatabase == null) {
            remotoDatabase = new RemotoDatabase(context);
            SQLiteDatabase db=remotoDatabase.getWritableDatabase();
            try {
                db.execSQL(CREATE_DiffCollection);
                db.execSQL(CREATE_LearnPlan);
                db.execSQL(CREATE_Preference);
                db.execSQL(CREATE_USER);
                db.execSQL(CREATE_WrongCollection);
            }catch (SQLException e){
                //e.printStackTrace();
            }
            db.close();
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
