package com.MainView;

import com.R;

/*
MainActivity要用的常量
 */
public class MainActivityUnit {

    //tabbar的标题
    public final static String[] mTitles = {"单词",  "个人"};

    //被选中时的图标，可以在 res/drawable里面自己换喜欢的
    public  final static int[] mSeleIcons = {R.drawable.homepage_fragment_learnwords_select,R.drawable.homepage_fragment_personal_select};

    //没被选中的图标
    public  final static  int[] mNormalIcons = {R.drawable.homepage_fragment_learnwords_nor,   R.drawable.homepage_fragment_personal_nor};

}
