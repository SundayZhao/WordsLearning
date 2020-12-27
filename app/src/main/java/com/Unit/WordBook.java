package com.Unit;

import android.content.Context;

import java.util.ArrayList;

/*
单词书类
从单词书中获取单词等操作在这里实现
 */
public class WordBook {
    //单词书编号
    private  int bookId=0;
    //单词书名称
    private  String bookName=null;
    //单词列表，一定不要获取整个单词书的列表，但是现在是本地操作，无所谓了
    private ArrayList<Word> words=null;

    public WordBook(int bookId, Context context) {
        this.bookId = bookId;
        context.getAssets().open()
    }
}
