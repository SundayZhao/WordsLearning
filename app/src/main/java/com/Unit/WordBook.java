package com.Unit;

import android.content.ContentValues;
import android.content.Context;

import com.dao.RemotoDatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public WordBook(Context context, String bookId) {
        this.bookId = Integer.valueOf(bookId);
        words=new ArrayList<Word>();
//        InputStream file = null;
//        try {
//            file = context.getAssets().open("EnWords.csv");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        List resultList = new ArrayList();
//        int cnt = 0;
//        BufferedReader reader = new BufferedReader(new InputStreamReader(file));
//        try {
//            String csvLine;
//            while ((csvLine = reader.readLine()) != null) {
//                String[] row = csvLine.split(",");
//                resultList.add(row);
//                cnt++;
//                if(cnt>=100) break;
//            }
//        }
//        catch (IOException ex) {
//            throw new RuntimeException("Error in reading CSV file: "+ex);
//        }
//        finally {
//            try {
//                file.close();
//            }
//            catch (IOException e) {
//                throw new RuntimeException("Error while closing input stream: "+e);
//            }
//        }

//        RemotoDatabase remotoDatabase = RemotoDatabase.getInstance(context);
//        for(int i = 0; i< resultList.size(); i++){
//            String[] line = (String[])resultList.get(i);
//            ContentValues contentValues = new ContentValues();
//            contentValues.put("word", line[0]);
//            contentValues.put("trans", line[1]);
//            remotoDatabase.addSqllite("WordBook", contentValues);
//        }
        InputStream file = null;
        try {
            file = context.getAssets().open("EnWords.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        int cnt = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(file));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                Word word = new Word(row[0].replaceAll("\"", ""));
                word.setChinese(row[1].replaceAll("\"", ""));
                words.add(word);
                cnt++;
                if(cnt>=100) break;
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
        finally {
            try {
                file.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: "+e);
            }
        }
        words.remove(0);
    }

    public ArrayList<Word> getWords() {
        return words;
    }

    public String getChinese(String eng){
        String ret = "";
        for(int i=0; i<words.size(); i++){
            if(words.get(i).getEnglish().equals(eng)){
                ret = words.get(i).getChinese();
            }
        }
        return ret;
    }

    private static HashMap<String, Integer> get_word_books(){
        HashMap<String, Integer> ret = new  HashMap<String, Integer>();
        ret.put("四级词汇", 2564);
        ret.put("六级词汇", 3748);
        ret.put("托福词汇", 4156);
        ret.put("雅思词汇", 4962);
        ret.put("GRE词汇", 8945);
        return ret;
    }
}
