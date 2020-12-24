package com.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
服务器数据库的类（虽然没有后端，假装有）
 */
public class RemotoDatabase extends SQLiteOpenHelper {

    private Context context=null;

    private static final String DB_NAME="RemotoDatebase.db";
    private  static RemotoDatabase remotoDatabase=null;

    public static synchronized RemotoDatabase getInstance(Context context){
        if(remotoDatabase==null){
            remotoDatabase=new RemotoDatabase(context);
        }
        return remotoDatabase;
    }
    private RemotoDatabase(Context context){
        //创建数据库
        super(context,DB_NAME,null,2);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //super(context,"RemotoDatebase.db",null,2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void updateSqlite(String table,String id,String[] keys,String[] value){
        SQLiteDatabase sqliteDatabase = remotoDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        int step=0;
        for(String key:keys){
            values.put(key, value[step]);
            step++;
        }

        sqliteDatabase.update(table,values,"id=?",new String[]{id});
    }
}
