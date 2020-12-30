package com.MainView.LearnWords;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.R;
import com.Unit.User;
import com.UserView.LoginActivity;
import com.cazaea.sweetalert.SweetAlertDialog;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.vo.DateData;


public class LearnWordsFragment extends Fragment {

    private View rootView; //这是个fragment，没有完整生命周期，需要有一个rootView一般别动
    private boolean isFirstLoad = true;//这是个bug，如果不加完成标志，fragment会重复加载，导致部分数据重复初始化

    /*
    组件
     */
    private TextView textView = null;
    private MCalendarView calendarView = null;
    private Button button = null;
    private User user = null;

    public LearnWordsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View thisView = inflater.inflate(R.layout.fragment_learnword, null);


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
        textView = (TextView) view.findViewById(R.id.textView);
        calendarView = (MCalendarView) view.findViewById(R.id.mCalendarView);
        button = (Button) view.findViewById(R.id.button1);
        user = User.getInstance(getContext());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                if (User.getInstance(getContext()).isLogged()) {
                    if (User.getInstance(getContext()).getLearnPlan().is_noPlan()) {
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("你的计划呢")
                                .setContentText("请先去个人中心设置一个学习计划")
                                .setConfirmText("知道了")
                                .showCancelButton(false)
                                .showConfirmButton(true)
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                    }
                                })
                                .show();
                    } else {
                        intent = new Intent(getContext(), WordsRemember.class);
                    }
                } else {
                    intent = new Intent(getContext(), LoginActivity.class);

                }
//                String data = null;
//                intent.putExtra(EXTRA_MESSAGE, data);
                if (intent != null)
                    startActivity(intent);
            }
        });

//        LearnPlan learnPlan = user.getLearnPlan();
//        ArrayList<DateData> finished_dates = learnPlan.getFinished_dates();
//        for(int i=0;i<finished_dates.size();i++) {
//            calendarView.markDate(finished_dates.get(i).getYear(),finished_dates.get(i).getMonth(),finished_dates.get(i).getDay());
//        }

//        calendarView.markDate(finished_dates.get(0));
        calendarView.markDate(new DateData(2016, 3, 1).setMarkStyle(new MarkStyle(MarkStyle.DOT, Color.RED)));
    }


}
