package com.Unit;

import java.util.ArrayList;
import java.util.Date;

public class LearnPlan {
    //计划的唯一编号
    private String learnPlanVersion=null;

    //创建日期
    private Date createTime=null;

    //单词书
    private  WordBook wb=null;

    //学习进度
    private  int hasLearned = 0;

    //微信的某种编号，负责退款
    private  Long weChartOrderId=0L;

    //已经打卡的日期
    private ArrayList<Date> last_Signin=null;
}
