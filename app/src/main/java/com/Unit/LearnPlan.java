package com.Unit;

import java.util.ArrayList;
import java.util.Date;

import sun.bob.mcalendarview.vo.DateData;

public class LearnPlan {
    //计划的唯一编号
    private int LearnPlanId=0;

    //创建日期
    private Date createTime=null;

    //单词书
    private  WordBook wb=null;

    //学习进度
    private  int hasLearned = 0;

    //微信的某种编号，负责退款
    private  Long weChartOrderId=0L;

    //已经打卡的日期
    private ArrayList<DateData> finished_dates=null;

    //打卡，具体的返回参数之后再说明
    public int clockIn(){
        return 0;
    }

    public LearnPlan(int LearnPlanId){
        this.LearnPlanId=LearnPlanId;
        initLearnPlan();
    }
    private void initLearnPlan(){
        if(LearnPlanId==0)return;
        else{
            //TODO:从sqlite里面拿到learnplan
        }
    }

    public ArrayList<DateData> getFinished_dates() {
        return finished_dates;
    }

    public void setFinished_dates(ArrayList<DateData> finished_dates) {
        this.finished_dates = finished_dates;
    }
}
