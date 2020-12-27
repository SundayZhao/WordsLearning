package com.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/*
本地数据库的类，主要是存一些缓存
 */
public class LocalDatabase extends SQLiteOpenHelper {

    private Context context=null;

    private static final String DB_NAME="LocalDatebase.db";
    private  static LocalDatabase localDatabase=null;
    private static final String csv_file = "EnWords.csv";

    public static synchronized LocalDatabase getInstance(Context context){
        if(localDatabase==null){
            localDatabase=new LocalDatabase(context);
        }
        return localDatabase;
    }
    private LocalDatabase(Context context){
        //创建数据库
        super(context,DB_NAME,null,2);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //super(context,"LocalDatebase.db",null,2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void copydatabase() throws IOException {
        //Open your local db as the input stream
        InputStream myinput = this.context.getAssets().open(csv_file);
    }

    public void updateSqlite(String table,String id,String[] keys,String[] value){
        SQLiteDatabase sqliteDatabase = localDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        int step=0;
        for(String key:keys){
            values.put(key, value[step]);
            step++;
        }

        sqliteDatabase.update(table,values,"id=?",new String[]{id});
    }
}
