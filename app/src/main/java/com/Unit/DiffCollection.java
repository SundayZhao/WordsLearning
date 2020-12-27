package com.Unit;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dao.RemotoDatabase;

import java.util.ArrayList;

public class DiffCollection extends  Collection{
    private  final String TABLE_NAME ="DiffCollection";

    private final int INIT_SUCCESS=0;
    private final int INITE_FAIL=1;
    @Override
    public int initCollection() {
        RemotoDatabase remotoDatabase=RemotoDatabase.getInstance(getAppContext());
        SQLiteDatabase sqliteDatabase = remotoDatabase.getReadableDatabase();
        Cursor cursor = sqliteDatabase.query(TABLE_NAME,
                new String[]{"CollectionVersion","uuid","word"},
                "CollectionVersion=?",
                new String[]{String.valueOf(getCollectionVersion())},
                null,null,null);
        if(cursor.getColumnCount()!=0){
            while (cursor.moveToNext()) {
                addWord(new Word(cursor.getString(cursor.getColumnIndex("word"))));
            }

        }else{
            return INITE_FAIL;
        }
        return INIT_SUCCESS;
    }

    @Override
    public int flushCollection(User user) {
        return 0;
    }


    @Override
    public int delWord(String word) {
        return 0;
    }


    public DiffCollection(Context AppContext,int collectionId){
        setCollectionVersion(collectionId);
        setAppContext(AppContext);
        initCollection();
    }
}
