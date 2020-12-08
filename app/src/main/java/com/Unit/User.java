package com.Unit;

/*
进行登录、注册、获取个人信息的用户类
 */
public class User {

    //token，在登录后获得一个token，一个账号每次登陆唯一
    private String token=null;
    //账户昵称
    private  String nickName=null;
    //账号id
    private int uuid=0;
    //账号
    private String username=null;
    //用户的单词学习计划
    private LearnPlan lp=null;
    //错词本
    private WrongCollection wc=null;
    //生词本
    private DiffCollection dc=null;
    //手机号
    private int phoneNum=0;
    //邮箱
    private String email=null;
    //个人配置
    private Preference preference=null;

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
        this.nickName = nickName;
    }

    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(int phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
