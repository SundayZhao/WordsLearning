package com.MainView.LearnWords;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.vo.DateData;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.R;
import com.Unit.LearnPlan;
import com.Unit.User;

import java.util.ArrayList;
import java.util.Date;


public class LearnWordsFragment extends Fragment {

    private View rootView; //这是个fragment，没有完整生命周期，需要有一个rootView一般别动
    private  boolean isFirstLoad  = true;//这是个bug，如果不加完成标志，fragment会重复加载，导致部分数据重复初始化

    /*
    组件
     */
    private TextView textView =null;
    private MCalendarView calendarView = null;

    private User user= User.getInstance(getContext());

    public LearnWordsFragment(){
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View thisView=inflater.inflate(R.layout.fragment_learnword,null);



        initView(thisView);
        return thisView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isFirstLoad = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstLoad) {
            // 如果有需要加载的数据，就在这里调用
            //加载数据()
            isFirstLoad = false;
        }
    }

    /**
     * 初始化视图
     *
     * @param view
     */
    protected void initView(View view) {
        textView =(TextView)view.findViewById(R.id.textView);
        calendarView = (MCalendarView) view.findViewById(R.id.mCalendarView);
        LearnPlan learnPlan = user.getLearnPlan();
        ArrayList<DateData> finished_dates = learnPlan.getFinished_dates();

        for(int i=0;i<finished_dates.size();i++) {
            calendarView.markDate(finished_dates.get(i).getYear(),finished_dates.get(i).getMonth(),finished_dates.get(i).getDay());
        }

    }


}
