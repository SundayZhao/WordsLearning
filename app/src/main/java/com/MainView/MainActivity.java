package com.MainView;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.MainView.LearnWords.LearnWordsFragment;
import com.MainView.Personal.PersonalFragment;
import com.R;
import com.UserView.LoginActivity;
import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.OnTabSelectListener;
import com.jpeng.jptabbar.anno.NorIcons;
import com.jpeng.jptabbar.anno.SeleIcons;
import com.jpeng.jptabbar.anno.Titles;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.tab.QMUITab;
import com.qmuiteam.qmui.widget.tab.QMUITabBuilder;
import com.qmuiteam.qmui.widget.tab.QMUITabIndicator;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;

import java.util.ArrayList;

//import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

public class MainActivity extends AppCompatActivity {

    //界面的组件
    private ViewPager mViewPager;
    private ArrayList<Fragment> fragmentArray;
    private QMUITabSegment qmuiTabSegment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//       SQLiteStudioService.instance().start(this);
//        if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//        }
        //开始初始化界面
        initViews();
    }

    /*
    组件绑定布局
    可以把组件的一些初始化也放在里面
     */
    private void initViews() {
        //把初始化控件卸载这里，最简单的方式，要用bind自己改
        mViewPager = (ViewPager) findViewById(R.id.homepage_vp);
        qmuiTabSegment = (QMUITabSegment) findViewById(R.id.tabbar);
        //初始化下方导航和fragments

        initTabFragment();
    }

    /*
        初始化tabbar
        惰性加载fragment并塞到adapter内
     */
    private void initTabFragment() {
        fragmentArray = new ArrayList<Fragment>(2);
        fragmentArray.add(new LearnWordsFragment());
        fragmentArray.add(new PersonalFragment());
        //如果需要新的页面，把fragment加到array，并在MainActivityUnit内的标题和图标加新的
        MainActivityTabbarAdapter adapter = new MainActivityTabbarAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fragmentArray);
        mViewPager.setAdapter(adapter);

        //下面这些别动
        String[] titles = MainActivityUnit.mTitles;
        int[] normalICON = MainActivityUnit.mNormalIcons;
        int[] selectedICON = MainActivityUnit.mSeleIcons;
        for (int index = 0; index < MainActivityUnit.mTitles.length; index++) {
            QMUITabBuilder tabBuilder = qmuiTabSegment.tabBuilder();
            QMUITab tab = tabBuilder.setText(titles[index]).setNormalDrawable(getApplicationContext().getDrawable(normalICON[index])).setSelectedDrawable(getApplicationContext().getDrawable(selectedICON[index])).build(getApplicationContext());
            qmuiTabSegment.addTab(tab);
        }

        int space = QMUIDisplayHelper.dp2px(getApplicationContext(), 16);
        qmuiTabSegment.setIndicator(
                new QMUITabIndicator(QMUIDisplayHelper.dp2px(getApplicationContext(), 2),
                        false, true));
        qmuiTabSegment.setMode(QMUITabSegment.MODE_FIXED);
        qmuiTabSegment.setItemSpaceInScrollMode(space);
        qmuiTabSegment.setupWithViewPager(mViewPager, false);
        qmuiTabSegment.setPadding(space, 0, space, 0);


    }

}


