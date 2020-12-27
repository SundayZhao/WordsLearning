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

    //微信的某种编号，负责退款
    private  String weChartOrderId=null;

    //已经打卡的日期
    private ArrayList<String> last_Signin=null;


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
                    new String[]{"LearnPlanId","createTime","wordBookId","hasLearned","weChartOrderId","last_Signin"},
                    TABLE_PRIMARY_KEY+"=?",
                    new String[]{String.valueOf(LearnPlanId)},
                    null,null,null);
            if(cursor.moveToFirst()){
                LearnPlanId=cursor.getInt(cursor.getColumnIndex("LearnPlanId"));
                createTime=cursor.getString(cursor.getColumnIndex("createTime"));
                wordBook=new WordBook(appContext,cursor.getInt(cursor.getColumnIndex("wordBookId")));
                hasLearned=cursor.getInt(cursor.getColumnIndex("hasLearned"));
                weChartOrderId=cursor.getString(cursor.getColumnIndex("weChartOrderId"));

                String[]checkInDates=cursor.getString(cursor.getColumnIndex("last_Signin")).split("||");
                last_Signin= new ArrayList<String>(Arrays.asList(checkInDates));
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

    //打卡，具体的返回参数之后再说明
    public int clockIn(Date date){
        SimpleDateFormat  format= new SimpleDateFormat("yyyy年MM月dd日");
        last_Signin.add(format.format(date));

        RemotoDatabase.getInstance(appContext).
                updateSqlite(TABLE_NAME,
                        TABLE_PRIMARY_KEY,
                        String.valueOf(LearnPlanId),
                        new String[]{"last_Signin"},
                        new String[]{StringUtils.join(last_Signin,"||")});

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
        RemotoDatabase.getInstance(appContext).addSqllite(TABLE_NAME,contentValues);
    }

    public boolean is_noPlan(){
        if (createTime==null ||createTime==""){
          return true;
        }
        else return true;
    }
}
