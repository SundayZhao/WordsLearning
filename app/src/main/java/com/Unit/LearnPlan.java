package com.Unit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dao.RemotoDatabase;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class LearnPlan {
    public  static final int NO_PLAN=0;
    private static final int INITE_FAIL = 0;
    private static final int INIT_SUCCESS = 1;
    private Context appContext;
    private static final String TABLE_NAME = "LearnPlan";
    private static final String TABLE_PRIMARY_KEY="LearnPlanId";

    //计划的唯一编号
    private int LearnPlanId=0;

    //创建日期
    private String createTime=null;

    //单词书
    private  WordBook wordBook=null;

    //学习进度，当前学了多少个单词
    private  int hasLearned = 0;

    //每天学多少个单词
    private int learndaily=30;

    //微信的某种编号，负责退款
    private  String weChartOrderId=null;

    //已经打卡的日期
    private ArrayList<String> last_Signin=null;

    //学习记录
    private ArrayList<String> study_log=null;


    public LearnPlan(Context appContext,int LearnPlanId){
        this.appContext=appContext;
        this.LearnPlanId=LearnPlanId;
        initLearnPlan();
    }
    private int initLearnPlan(){
        if(LearnPlanId==NO_PLAN)return INITE_FAIL;
        else{
            //TODO:从sqlite里面拿到learnplan
            RemotoDatabase remotoDatabase=RemotoDatabase.getInstance(appContext);
            SQLiteDatabase sqliteDatabase = remotoDatabase.getReadableDatabase();
            Cursor cursor = sqliteDatabase.query(TABLE_NAME,
                    new String[]{"LearnPlanId","createTime","wordBookId","hasLearned","weChartOrderId","last_Signin","studylog","learndaily"},
                    TABLE_PRIMARY_KEY+"=?",
                    new String[]{String.valueOf(LearnPlanId)},
                    null,null,null);
            if(cursor.moveToFirst()){
                LearnPlanId=cursor.getInt(cursor.getColumnIndex("LearnPlanId"));
                createTime=cursor.getString(cursor.getColumnIndex("createTime"));
                wordBook=new WordBook(appContext,String.valueOf(cursor.getInt(cursor.getColumnIndex("wordBookId"))));
                hasLearned=cursor.getInt(cursor.getColumnIndex("hasLearned"));
                weChartOrderId=cursor.getString(cursor.getColumnIndex("weChartOrderId"));
                learndaily=(cursor.getInt(cursor.getColumnIndex("learndaily"))==0)?30:cursor.getInt(cursor.getColumnIndex("learndaily"));


                String temp=cursor.getString(cursor.getColumnIndex("last_Signin"));
                if(temp.trim().equals("")){
                    last_Signin = new ArrayList<String>();
                }else {
                    String[] checkInDates = cursor.getString(cursor.getColumnIndex("last_Signin")).split("\\|\\|");
                    last_Signin = new ArrayList<String>(Arrays.asList(checkInDates));
                }

                temp=cursor.getString(cursor.getColumnIndex("studylog"));
                if(temp.trim().equals("")){
                    study_log = new ArrayList<String>();
                }else {
                    String[] studyLogs = cursor.getString(cursor.getColumnIndex("studylog")).split("\\|\\|");
                    study_log = new ArrayList<String>(Arrays.asList(studyLogs));
                }

            }else{
                sqliteDatabase.close();
                return INITE_FAIL;
            }
            return INIT_SUCCESS;
        }
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        RemotoDatabase.getInstance(appContext).
                updateSqlite(TABLE_NAME,
                        TABLE_PRIMARY_KEY,
                        String.valueOf(LearnPlanId),
                        new String[]{"createTime"},
                        new String[]{createTime});
        this.createTime = createTime;
    }

    public WordBook getWordBook() {
        return wordBook;
    }

    public void setWordBook(String wordBookId) {
        RemotoDatabase.getInstance(appContext).
                updateSqlite(TABLE_NAME,
                        TABLE_PRIMARY_KEY,
                        String.valueOf(LearnPlanId),
                        new String[]{"wordBookId"},
                        new String[]{wordBookId});
        this.wordBook = new WordBook(appContext,wordBookId);
    }

    public int getHasLearned() {
        return hasLearned;
    }

    //传入今天学习过的单词数
    public void setHasLearned(int hasLearned) {
        RemotoDatabase.getInstance(appContext).
                updateSqlite(TABLE_NAME,
                        TABLE_PRIMARY_KEY,
                        String.valueOf(LearnPlanId),
                        new String[]{"hasLearned"},
                        new String[]{String.valueOf(hasLearned)});
        this.hasLearned = hasLearned;
    }

    public String getWeChartOrderId() {
        return weChartOrderId;
    }

    public void setWeChartOrderId(String weChartOrderId) {
        RemotoDatabase.getInstance(appContext).
                updateSqlite(TABLE_NAME,
                        TABLE_PRIMARY_KEY,
                        String.valueOf(LearnPlanId),
                        new String[]{"weChartOrderId"},
                        new String[]{weChartOrderId});
        this.weChartOrderId = weChartOrderId;
    }

    public ArrayList<String> getClockInDate() {
        return last_Signin;
    }

    public ArrayList<String> getStudyLog(){
        return study_log;
    }
    //打卡，今日日期，今天学习的总数，正确的数
    public int clockIn(Date date,int allWords,int correctWords){
        SimpleDateFormat  format= new SimpleDateFormat("yyyy年MM月dd日");
        last_Signin.add(format.format(date));
        study_log.add(format.format(date)+","+String.valueOf(allWords)+","+String.valueOf(correctWords));

        RemotoDatabase.getInstance(appContext).
                updateSqlite(TABLE_NAME,
                        TABLE_PRIMARY_KEY,
                        String.valueOf(LearnPlanId),
                        new String[]{"last_Signin"},
                        new String[]{(last_Signin.size()==0)?"":StringUtils.join(last_Signin,"||")});

        RemotoDatabase.getInstance(appContext).
                updateSqlite(TABLE_NAME,
                        TABLE_PRIMARY_KEY,
                        String.valueOf(LearnPlanId),
                        new String[]{"studylog"},
                        new String[]{(study_log.size()==0)?"":StringUtils.join(study_log,"||")});

        return 0;
    }

    public static  void createNewPlan(Context appContext,int planId){
        ContentValues contentValues=new ContentValues();
        //new String[]{"LearnPlanId","createTime","wordBookId","hasLearned","weChartOrderId","last_Signin"},
        contentValues.put("LearnPlanId",planId);
        contentValues.put("createTime", "");
        contentValues.put("wordBookId",NO_PLAN);
        contentValues.put("weChartOrderId","");
        contentValues.put("last_Signin","");
        contentValues.put("studylog","");
        contentValues.put("learndaily",0);
        RemotoDatabase.getInstance(appContext).addSqllite(TABLE_NAME,contentValues);
    }

    public void giveupPlan(){
        setCreateTime("");
    }
    public boolean is_noPlan(){
        if (createTime==null ||createTime==""){
          return true;
        }
        else return true;
    }

    public int getLearndaily() {
        if(is_noPlan()==false)return 0;
        return learndaily;
    }

    public void setLearndaily(int learndaily) {
        this.learndaily = learndaily;
        RemotoDatabase.getInstance(appContext).
                updateSqlite(TABLE_NAME,
                        TABLE_PRIMARY_KEY,
                        String.valueOf(LearnPlanId),
                        new String[]{"learndaily"},
                        new String[]{String.valueOf(learndaily)});
    }
}
