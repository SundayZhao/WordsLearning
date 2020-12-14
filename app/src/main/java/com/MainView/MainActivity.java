package com.MainView;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.MainView.LearnWords.LearnWordsFragment;
import com.MainView.Personal.PersonalFragment;
import com.R;
import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.OnTabSelectListener;
import com.jpeng.jptabbar.anno.NorIcons;
import com.jpeng.jptabbar.anno.SeleIcons;
import com.jpeng.jptabbar.anno.Titles;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnTabSelectListener {
    /*
    这里是tabbar需要用的标题和图标
     */
    @Titles
    private final String[] mTitles = MainActivityUnit.mTitles;
    @SeleIcons
    private  final int[] mSeleIcons = MainActivityUnit.mSeleIcons;
    @NorIcons
    private  final int[] mNormalIcons = MainActivityUnit.mNormalIcons;

    //界面的组件
    private ViewPager mVp;
    private  JPTabBar mTabBar;
    private ArrayList<Fragment> fragmentArray;

    @Override
    public void onTabSelect(int index) {

    }

    @Override
    public boolean onInterruptSelect(int index) {
        //控制是否中断tabbar的切换
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //初始化tabbar
        initViews();
    }

    /*
    组件绑定布局
    可以把组件的一些初始化也放在里面
     */
    private void initViews(){
        //viewpaper 用来显示fragment
        mVp = (ViewPager) findViewById(R.id.homepage_vp);
        mVp.setOffscreenPageLimit(1);

        /*
        初始化tabbar
        惰性加载fragment并塞到adapter内
         */
        mTabBar = (JPTabBar) findViewById(R.id.tabbar);
        fragmentArray=new ArrayList<Fragment>(2);
        fragmentArray.add(new LearnWordsFragment());
        fragmentArray.add(new PersonalFragment());
        //如果需要新的页面，把fragment加到array，并在最上方的标题和图标加新的
        MainActivityTabbarAdapter adapter = new MainActivityTabbarAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,fragmentArray);
        mVp.setAdapter(adapter);
        mTabBar.setContainer(mVp);
        mTabBar.setTabListener(this);
    }
}


