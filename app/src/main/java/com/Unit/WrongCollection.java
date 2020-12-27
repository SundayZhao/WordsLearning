package com.Unit;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dao.RemotoDatabase;

import java.util.ArrayList;

public class WrongCollection extends Collection{

    private final int INIT_SUCCESS=0;
    private final int INITE_FAIL=1;
    @Override
    public int initCollection() {
        RemotoDatabase remotoDatabase=RemotoDatabase.getInstance(getAppContext());
        SQLiteDatabase sqliteDatabase = remotoDatabase.getReadableDatabase();
        Cursor cursor = sqliteDatabase.query(getTABLENAME(),
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
    public int flushCollection() {
        super.flushCollection();
        return initCollection();
    }


    @Override
    public int delWord(String word) {
        return 0;
    }


    public WrongCollection(Context AppContext, int collectionId){
        setTABLE_COLUMN_ID("CollectionVersion");
        setTABLENAME("DiffCollection");
        setCollectionVersion(collectionId);
        setAppContext(AppContext);
        initCollection();
    }
}
