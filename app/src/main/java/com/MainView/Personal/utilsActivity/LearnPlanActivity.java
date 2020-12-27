package com.MainView.Personal.utilsActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import com.R;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import me.itangqi.waveloadingview.WaveLoadingView;

public class LearnPlanActivity extends Activity {
    private static final float TEXTSIZE = 20.0f;
    private static final int ITEMHEIGHT = 150;
    private static final int SELECT_NOACITION = -1;
    private static final int SELECT_CURBOOK = 0;
    private static final int SELECT_DAILYWORDSNUM = 1;
    private static final int SELECT_CHECKINDAYS = 2;
    private static final int SELECT_GIVEUPPLAN = 3;
    private QMUIGroupListView mGroupListView = null;
    private WaveLoadingView mWaveLoadingView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leanplan_index);
        //开始初始化界面
        initViews();
    }

    private void initViews() {
        mGroupListView = (QMUIGroupListView) findViewById(R.id.list_learnPlan_index);
        mWaveLoadingView = (WaveLoadingView) findViewById(R.id.waveView_leanPlan_index);

        initWaveView();
        initListItems();
    }

    //初始化圆形进度条
    private void initWaveView() {
        mWaveLoadingView.setShapeType(WaveLoadingView.ShapeType.CIRCLE);
        mWaveLoadingView.setTopTitle("计划进度:86%");
        mWaveLoadingView.setCenterTitleColor(Color.GRAY);
        mWaveLoadingView.setBottomTitleSize(18);
        mWaveLoadingView.setProgressValue(86);
        mWaveLoadingView.setBorderWidth(10);
        mWaveLoadingView.setAmplitudeRatio(60);
        mWaveLoadingView.setWaveColor(Color.GRAY);
        mWaveLoadingView.setBorderColor(Color.GRAY);
        mWaveLoadingView.setTopTitleStrokeColor(Color.BLUE);
        mWaveLoadingView.setTopTitleStrokeWidth(3);
        mWaveLoadingView.setAnimDuration(3000);
        mWaveLoadingView.pauseAnimation();
        mWaveLoadingView.resumeAnimation();
        mWaveLoadingView.cancelAnimation();
        mWaveLoadingView.startAnimation();
    }

    //初始化下方列表
    private void initListItems() {
        //TODO:学习计划

        int height = QMUIResHelper.getAttrDimen(getApplicationContext(), com.qmuiteam.qmui.R.attr.qmui_list_item_height);
        QMUICommonListItemView item_curBook = makeListItem("当前单词书：", "", SELECT_CURBOOK);

        QMUICommonListItemView item_DaylyWordsNum = makeListItem("每日学习单词数：", "", SELECT_DAILYWORDSNUM);

        QMUICommonListItemView item_CheckIndays = makeListItem("已打卡天数：", "", SELECT_CHECKINDAYS);

        QMUICommonListItemView item_CreateTime = makeListItem("计划创建日期：", "", SELECT_NOACITION);

        QMUICommonListItemView item_GiveupPlan = makeListItem("放弃计划", "", SELECT_GIVEUPPLAN);


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:点击后弹出对应的窗口，简化用dialog
                int selectId = (int) v.getTag();
                System.out.println(selectId);
                switch (selectId) {
                    case SELECT_CURBOOK:
                        break;
                    case SELECT_DAILYWORDSNUM:
                        break;
                    case SELECT_CHECKINDAYS:
                        break;
                    case SELECT_GIVEUPPLAN:
                        break;
                }
            }
        };

        QMUIGroupListView.newSection(getBaseContext())
                .addItemView(item_curBook, onClickListener)
                .addItemView(item_DaylyWordsNum, onClickListener)
                .addItemView(item_CheckIndays, onClickListener)
                .addItemView(item_CreateTime, onClickListener)
                .addItemView(item_GiveupPlan, onClickListener)
                .addTo(mGroupListView);
    }

    private QMUICommonListItemView makeListItem(String title, String description, int callbackId) {
        QMUICommonListItemView itemView = mGroupListView.createItemView(null,
                title, description,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON,
                ITEMHEIGHT);
        itemView.getTextView().setTextSize(TypedValue.COMPLEX_UNIT_DIP, TEXTSIZE);
        if (callbackId != -SELECT_NOACITION) {
            itemView.setTag(callbackId);
        }
        return itemView;
    }

}

