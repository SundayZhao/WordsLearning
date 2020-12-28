package com.Unit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dao.RemotoDatabase;

public class Preference {
    private static final int INITE_FAIL = 0;
    private static final int INIT_SUCCESS = 1;
    private Context appContext;
    private static final String TABLE_NAME = "Preference";
    private static final String TABLE_PRIMARY_KEY="preferenceId";
    private int preferenceId=0;
    //学习模式
    private  int studyMode;
    public  static final int MODE_SPELL=0;
    public  static int MODE_RECOGNIZE=1;

    //是否乱序
    private  int studyInOrder;
    public  static int STUDY_ORDER_DISOREDER=0;
    public  static int STUDY_OREDER_INORDER=1;

    //发音
    private  int pronunciation;
    public  static int PRONUNCIATION_ENGLISH=0;
    public  static int PRONUNCIATION_US=1;

    //自动发音
    private  int autoPlay;
    public  static int AUTOPLAY_OPEN=0;
    public  static  int AUTOPLAY_CLOSE=1;

    public Preference(Context appContext,int preferenceId){
        this.appContext=appContext;
        this.preferenceId=preferenceId;
        initPreference();
    }

    //这个函数是用来登录后初始化个人配置的
    private int initPreference() {
        if(preferenceId==0) return INITE_FAIL;
        RemotoDatabase remotoDatabase=RemotoDatabase.getInstance(appContext);
        SQLiteDatabase sqliteDatabase = remotoDatabase.getReadableDatabase();
        Cursor cursor = sqliteDatabase.query(TABLE_NAME,
                new String[]{"preferenceId","studyMode","studyInOrder","pronunciation","autoPlay"},
                TABLE_PRIMARY_KEY+"=?",
                new String[]{String.valueOf(preferenceId)},
                null,null,null);
        if(cursor.moveToFirst()){
            studyMode=(cursor.getInt(cursor.getColumnIndex("studyMode"))==MODE_RECOGNIZE)?MODE_RECOGNIZE:MODE_SPELL;
            studyInOrder=(cursor.getInt(cursor.getColumnIndex("studyInOrder"))==STUDY_ORDER_DISOREDER)?STUDY_ORDER_DISOREDER:STUDY_OREDER_INORDER;
            pronunciation=(cursor.getInt(cursor.getColumnIndex("pronunciation"))==PRONUNCIATION_ENGLISH)?PRONUNCIATION_ENGLISH:PRONUNCIATION_US;
            autoPlay=(cursor.getInt(cursor.getColumnIndex("autoPlay"))==AUTOPLAY_OPEN)?AUTOPLAY_OPEN:AUTOPLAY_CLOSE;
        }else{
            sqliteDatabase.close();
            return INITE_FAIL;
        }
        return INIT_SUCCESS;
    }

    public int getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(int preferenceId) {
        this.preferenceId = preferenceId;
    }

    public int getStudyMode() {
        return studyMode;
    }

    public void setStudyMode(int studyMode) {
        RemotoDatabase.getInstance(appContext).
                updateSqlite(TABLE_NAME,
                        TABLE_PRIMARY_KEY,
                        String.valueOf(getPreferenceId()),
                        new String[]{"studyMode"},
                        new String[]{String.valueOf(studyMode)});
        this.studyMode = studyMode;
    }

    public int getStudyInOrder() {
        return studyInOrder;
    }

    public void setStudyInOrder(int studyInOrder) {
        RemotoDatabase.getInstance(appContext).
                updateSqlite(TABLE_NAME,
                        TABLE_PRIMARY_KEY,
                        String.valueOf(getPreferenceId()),
                        new String[]{"studyInOrder"},
                        new String[]{String.valueOf(studyInOrder)});
        this.studyInOrder = studyInOrder;
    }

    public int getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(int pronunciation) {
        RemotoDatabase.getInstance(appContext).
                updateSqlite(TABLE_NAME,
                        TABLE_PRIMARY_KEY,
                        String.valueOf(getPreferenceId()),
                        new String[]{"pronunciation"},
                        new String[]{String.valueOf(pronunciation)});
        this.pronunciation = pronunciation;
    }

    public int getAutoPlay() {
        return autoPlay;
    }

    public void setAutoPlay(int autoPlay) {
        RemotoDatabase.getInstance(appContext).
                updateSqlite(TABLE_NAME,
                        TABLE_PRIMARY_KEY,
                        String.valueOf(getPreferenceId()),
                        new String[]{"autoPlay"},
                        new String[]{String.valueOf(autoPlay)});

        this.autoPlay = autoPlay;
    }

    public static void createNewPreference(Context appContext,int id){
        ContentValues contentValues=new ContentValues();
        //"preferenceId","studyMode","studyInOrder","pronunciation","autoPlay"
        contentValues.put("preferenceId",id);
        contentValues.put("studyMode", MODE_SPELL);
        contentValues.put("studyInOrder",STUDY_ORDER_DISOREDER);
        contentValues.put("pronunciation",PRONUNCIATION_ENGLISH);
        contentValues.put("autoPlay",AUTOPLAY_OPEN);
        RemotoDatabase.getInstance(appContext).addSqllite(TABLE_NAME,contentValues);
    }
}
