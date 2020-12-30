package com.Unit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.dao.RemotoDatabase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/*
进行登录、注册、获取个人信息的用户类
 */
public  class  User {
    private final String TABLE_NAME="user";
    private final String TABLE_COLUMN_ID="uuid";
    public static  final int LOG_LOGIN_FAIL=0;
    public static  final int LOG_LOGIN_SUCCESS=1;

    public static final String HEAD_IMAGE_NONE="normal.png";
    //必须是appcontext，不是basecontext
    private Context AppContext=null;

    //静态单实例，用User.getInstance()来拿到这个实例，再通过这个实例操作下面这些玩意
    private static User instance = null;

    //是否登录
    private boolean isLog=false;
    //token，在登录后获得一个token，一个账号每次登陆唯一
    private String token=null;
    //账户昵称
    private  String nickName=null;
    //账号id
    private int uuid=0;
    //账号
    private String username=null;
    //用户的单词学习计划
    private LearnPlan learnPlan=null;
    //错词本
    private WrongCollection wrongCollection=null;
    //生词本
    private DiffCollection diffCollection=null;
    //手机号
    private String phoneNum=null;
    //邮箱
    private String email=null;
    //个人配置
    private Preference preference=null;
    //头像的文件名
    private String headImage=null;

    private User(Context AppContext){
        this.AppContext=AppContext;
    }

    //判断当前是否登录
    public boolean isLogged(){
        if(!isLog) return false;
        return true;
    }
    public static synchronized User getInstance(Context AppContext) {
        if(instance==null){
            instance=new User(AppContext);
        }
        return instance;
    }

    //登录，传入用户名，密码
    public int logIn(String username,String password){
        RemotoDatabase remotoDatabase=RemotoDatabase.getInstance(AppContext);
        SQLiteDatabase sqliteDatabase = remotoDatabase.getReadableDatabase();
        Cursor cursor = sqliteDatabase.query(TABLE_NAME,
                new String[]{"uuid","nickname","LearnPlanId","WrongCollectionId",
                        "DiffCollectionID","phoneNum","email","preferenceId","headImage"},
                "username=? and password=?",
                new String[]{username,password},
                null,null,null);
        if(cursor.moveToFirst()){
            this.nickName=cursor.getString(cursor.getColumnIndex("nickname"));
            this.uuid=cursor.getInt(cursor.getColumnIndex("uuid"));
            this.username=username;
            this.learnPlan=new LearnPlan(AppContext,cursor.getInt(cursor.getColumnIndex("LearnPlanId")));
            this.wrongCollection=new WrongCollection(AppContext,cursor.getInt(cursor.getColumnIndex("WrongCollectionId")));
            this.diffCollection=new DiffCollection(AppContext,cursor.getInt(cursor.getColumnIndex("DiffCollectionID")));
            this.phoneNum=cursor.getString(cursor.getColumnIndex("phoneNum"));
            this.email=cursor.getString(cursor.getColumnIndex("email"));
            this.preference=new Preference(AppContext,cursor.getInt(cursor.getColumnIndex("preferenceId")));
            this.headImage=cursor.getString(cursor.getColumnIndex("headImage"));
            this.isLog=true;
            return LOG_LOGIN_SUCCESS;
        }else{
            this.isLog=false;
            return LOG_LOGIN_FAIL;
        }
    }

    public void logOut(){
        isLog=false;
    }

    public void registe(String username,String password,String nickName,String email,String phoneNum){
        this.username=username;
        this.nickName=nickName;
        this.uuid=Math.abs(UUID.randomUUID().hashCode());
        this.email=email;
        this.phoneNum=phoneNum;

        ContentValues contentValues=new ContentValues();
        //TODO:默认的生词本
        contentValues.put("uuid",this.uuid);
        contentValues.put("nickname",this.nickName);

        int newLearnPlanId=Math.abs(UUID.randomUUID().hashCode());
        contentValues.put("LearnPlanId",newLearnPlanId);
        LearnPlan.createNewPlan(AppContext,newLearnPlanId);

        int newDiffCollection=Math.abs(UUID.randomUUID().hashCode());
        contentValues.put("DiffCollectionID",newDiffCollection);

        int newWrongCollection=Math.abs(UUID.randomUUID().hashCode());
        contentValues.put("WrongCollectionId",newWrongCollection);

        contentValues.put("phoneNum",this.phoneNum);
        contentValues.put("email",this.email);

        int newPreferenceId=Math.abs(UUID.randomUUID().hashCode());
        contentValues.put("preferenceId",newPreferenceId);
        Preference.createNewPreference(AppContext,newPreferenceId);

        contentValues.put("headImage",HEAD_IMAGE_NONE);
        contentValues.put("password",password);
        contentValues.put("username",this.username);
        RemotoDatabase.getInstance(AppContext).addSqllite(TABLE_NAME,contentValues);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        RemotoDatabase.getInstance(AppContext).updateSqlite(TABLE_NAME,TABLE_COLUMN_ID,String.valueOf(uuid),new String[]{"nickName"},new String[]{nickName});
        this.nickName = nickName;
    }

    public int getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        RemotoDatabase.getInstance(AppContext).updateSqlite(TABLE_NAME,TABLE_COLUMN_ID,String.valueOf(uuid),new String[]{"phoneNum"},new String[]{phoneNum});
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        RemotoDatabase.getInstance(AppContext).updateSqlite(TABLE_NAME,TABLE_COLUMN_ID,String.valueOf(uuid),new String[]{"email"},new String[]{email});
        this.email = email;
    }


    public Bitmap getHeadImage() {
        FileInputStream fs = null;
        Bitmap bitmap=null;
        try {
            if(this.headImage.equals(HEAD_IMAGE_NONE)){
                //没有头像，读取默认的
                bitmap = BitmapFactory.decodeStream(  AppContext.getAssets().open(HEAD_IMAGE_NONE));
            }else {
                fs = new FileInputStream(this.headImage);
                bitmap = BitmapFactory.decodeStream(fs);
            }

            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setHeadImage(String headImage) {
        RemotoDatabase.getInstance(AppContext).updateSqlite(TABLE_NAME,TABLE_COLUMN_ID,String.valueOf(uuid),new String[]{"headImage"},new String[]{headImage});
        this.headImage = headImage;
    }

    public void changePassword(String username ,String password){
        RemotoDatabase.getInstance(AppContext).updateSqlite(TABLE_NAME,"username",username,new String[]{"password"},new String[]{password});

    }


    public LearnPlan getLearnPlan() {
        return learnPlan;
    }


    public WrongCollection getWrongCollection() {
        return wrongCollection;
    }


    public DiffCollection getDiffCollection() {
        return diffCollection;
    }


    public Preference getPreference() {
        return preference;
    }

}
