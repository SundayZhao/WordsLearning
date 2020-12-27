package com.Unit;

import android.content.ContentValues;
import android.content.Context;

import com.dao.RemotoDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class Collection {
    //一些常量
    public static final int WORD_DELETE_NOT_FOUND = 0;
    public static final int WORD_DELETE_SUCCESS = 1;
    public static final int WORD_ADD_REPEAT = 0;
    public static final int WORD_ADD_SUCCESS = 1;

    //对应的SQLITE表明
    private String TABLENAME="";
    private String TABLE_COLUMN_ID="";

    //窗口context，必须是appcontext
    private Context AppContext = null;
    //单词书编号
    private int CollectionVersion = 0;

    //单词表
    HashMap<String, Word> collectionList = new HashMap<String, Word>();

    //初始化单词书
    public abstract int initCollection();

    //刷新单词书，用来刷新列表
    public  int flushCollection(){
        collectionList.clear();
        return 0;
    }

    //获取当前单词书的列表，因为一次一般不会获取全部（下拉获取更多）就需要输入获取的起点和终点，如果offsit为0的话就代表获取全部
    public HashMap<String, Word> getCollectionList(int startSit, int offSit) {
        startSit = (offSit == 0) ? 0 : startSit;
        offSit = (offSit == 0) ? collectionList.size() : offSit;
        HashMap<String, Word> list = new HashMap<String, Word>();

        Iterator iter = collectionList.entrySet().iterator();
        while (iter.hasNext() && list.size() <= offSit) {
            Map.Entry entry = (Map.Entry) iter.next();
            if (startSit == 0) {
                String key = (String)entry.getKey();
                Word val = (Word)entry.getValue();
                collectionList.put(key,val);
            } else {
                startSit--;
            }
        }
        return collectionList;
    }

    //删除某个单词
    public int delWord(String word) {
        if (collectionList.remove(word) == null) {
            String whereClause=getTABLE_COLUMN_NAME()+"=? and word=?";
            String[] whereArgs=new String[]{String.valueOf(getCollectionVersion()),word};
            RemotoDatabase.getInstance(getAppContext()).delSqlite(getTABLENAME(),whereClause,whereArgs);
            return WORD_DELETE_NOT_FOUND;
        }
        else
            return WORD_DELETE_SUCCESS;
    }

    ;

    //删除很多个单词
    public void delWords(ArrayList<String> words) {
        for (String word : words) {
            delWord(word);
        }
    }

    //添加一个单词，要保证单词在单词书内的唯一性
    public int addWord(Word word) {
        if (collectionList.put(word.getEnglish(), word) == null) {
            ContentValues values = new ContentValues();
            values.put("CollectionVersion",getCollectionVersion());
            values.put("uuid",User.getInstance(getAppContext()).getUuid());
            values.put("word",word.getEnglish());
            RemotoDatabase.getInstance(getAppContext()).addSqllite(getTABLENAME(),values);
            return WORD_ADD_SUCCESS;
        }
        else
            return WORD_ADD_REPEAT;
    }

    //加若干个单词
    public void addWords(ArrayList<Word> words) {
        for (Word word : words) {
           addWord(word);
        }
    }

    public int getCollectionVersion() {
        return CollectionVersion;
    }

    public void setCollectionVersion(int collectionVersion) {
        CollectionVersion = collectionVersion;
    }

    public Context getAppContext() {
        return AppContext;
    }

    public void setAppContext(Context appContext) {
        AppContext = appContext;
    }

    public String getTABLENAME() {
        return TABLENAME;
    }

    public void setTABLENAME(String TABLENAME) {
        this.TABLENAME = TABLENAME;
    }

    public String getTABLE_COLUMN_NAME() {
        return TABLE_COLUMN_ID;
    }

    public void setTABLE_COLUMN_ID(String TABLE_COLUMN_ID) {
        this.TABLE_COLUMN_ID = TABLE_COLUMN_ID;
    }
}
