package com.Unit;

import java.util.ArrayList;

public abstract class Collection {
    private Long CollectionVersion =0L;

    public abstract int initCollection(User user);

    public abstract int flushCollection(User user);

    public abstract ArrayList<Word> getCollectionList();

    public abstract int delWord(String word);

    public abstract int delWords(ArrayList<String> words);

    public abstract int addWord(String word);

    public abstract int addWords(ArrayList<String> words);
}
