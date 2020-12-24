package com.Unit;

public class Preference {
    private int preferenceId=0;
    //学习模式
    private  int studyMode;
    //是否乱序
    private  boolean studyInOrder=false;
    //发音
    private  int pronunciation;
    //自动发音
    private  boolean autoPlay;

    public Preference(int preferenceId){
        this.preferenceId=preferenceId;
        initPreference();
    }

    //这个函数是用来登录后初始化个人配置的
    private void initPreference() {

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
        this.studyMode = studyMode;
    }

    public boolean isStudyInOrder() {
        return studyInOrder;
    }

    public void setStudyInOrder(boolean studyInOrder) {
        this.studyInOrder = studyInOrder;
    }

    public int getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(int pronunciation) {
        this.pronunciation = pronunciation;
    }

    public boolean isAutoPlay() {
        return autoPlay;
    }

    public void setAutoPlay(boolean autoPlay) {
        this.autoPlay = autoPlay;
    }
}
