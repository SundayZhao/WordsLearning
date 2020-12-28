package com.Unit;

import java.util.Date;

public class Word {
    private  String English=null;
    private  String Chinese=null;
    private  String phoneticSymbol=null;
    private String content=null;
    private int wrongTimes=0;
    private int rememberTimes=0;
    private boolean is_new=true;

    public Word(String English){
        this.English=English;
    }

    public String getEnglish() {
        return this.English;
    }

    public void setEnglish(String english)
    {
        this.English = english;
    }

    public String getChinese() {
        return Chinese;
    }

    public void setChinese(String chinese) {
        Chinese = chinese;
    }

    public String getPhoneticSymbol() {
        return phoneticSymbol;
    }

    public void setPhoneticSymbol(String phoneticSymbol) {
        this.phoneticSymbol = phoneticSymbol;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getWrongTimes() {
        return wrongTimes;
    }

    public void setWrongTimes(int wrongTimes) {
        this.wrongTimes = wrongTimes;
    }

    public int getRememberTimes() {
        return rememberTimes;
    }

    public void setRememberTimes(int rememberTimes) {
        this.rememberTimes = rememberTimes;
    }

    public boolean isIs_new() {
        return is_new;
    }

    public void setIs_new(boolean is_new) {
        this.is_new = is_new;
    }
}
