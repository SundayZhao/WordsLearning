package com.Unit;

import java.util.ArrayList;

public class WrongCollection extends Collection{
    private  int collectionId=0;

    @Override
    public int initCollection() {
        return 0;
    }

    @Override
    public int flushCollection(User user) {
        return 0;
    }

    @Override
    public ArrayList<Word> getCollectionList(int startSit, int offSit) {
        return null;
    }

    @Override
    public int delWord(String word) {
        return 0;
    }

    @Override
    public int delWords(ArrayList<String> words) {
        return 0;
    }

    @Override
    public int addWord(String word) {
        return 0;
    }

    @Override
    public int addWords(ArrayList<String> words) {
        return 0;
    }

    public WrongCollection(int collectionId){
        this.collectionId=collectionId;
        initCollection();
    }
}
