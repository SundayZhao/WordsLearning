package com.Unit;

import java.util.ArrayList;

public abstract class Collection {
    //单词书编号
    private Long CollectionVersion =0L;

    //初始化单词书
    public abstract int initCollection();

    //刷新单词书，用来刷新列表
    public abstract int flushCollection(User user);

    //获取当前单词书的列表，因为一次一般不会获取全部（下拉获取更多）就需要输入获取的起点和终点，如果offsit为0的话就代表获取全部
    public abstract ArrayList<Word> getCollectionList(int startSit,int offSit);

    //删除某个单词
    public abstract int delWord(String word);

    //删除很多个单词
    public abstract int delWords(ArrayList<String> words);

    //添加一个单词，要保证单词在单词书内的唯一性
    public abstract int addWord(String word);

    //加若干个单词
    public abstract int addWords(ArrayList<String> words);
}
