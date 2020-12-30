package com.MainView.Personal.utilsActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import com.R;
import com.Unit.User;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.cazaea.sweetalert.SweetAlertDialog;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import me.itangqi.waveloadingview.WaveLoadingView;

public class LearnPlanActivity extends Activity {
    private static final float TEXTSIZE = 20.0f;
    private static final int ITEMHEIGHT = 150;
    private static final int SELECT_NOACITION = -1;
    private static final int SELECT_CURBOOK = 0;
    private static final int SELECT_DAILYWORDSNUM = 1;
    private static final int SELECT_CHECKINDAYS = 2;
    private static final int SELECT_GIVEUPPLAN = 3;
    private static ArrayList<String> dailyLearnNum = new ArrayList<String>();
    private QMUIGroupListView mGroupListView = null;
    private WaveLoadingView mWaveLoadingView = null;
    private  ArrayList<String> wordBookList  =new ArrayList<String>();

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
        HashMap <String,Integer>WordBookMap = User.getInstance(null).getLearnPlan().getWordBook().get_word_books();
        Iterator iter = WordBookMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            wordBookList.add(entry.getKey() + "\t" + entry.getValue().toString());
        }

        if (User.getInstance(getApplicationContext()).getLearnPlan().is_noPlan()) {
            mWaveLoadingView.setCenterTitle("还没计划呢");
            mWaveLoadingView.setProgressValue(0);
        } else {
            //TODO:计划进度
            int proccess=
                    User.getInstance(getApplicationContext()).getLearnPlan().getHasLearned()/
                            Integer.parseInt(wordBookList.get(User.getInstance(getApplicationContext()).getLearnPlan().getWordBook().getBookId()).split("\t")[1]);
            mWaveLoadingView.setCenterTitle("计划进度:"+String.valueOf(proccess)+"%");
            mWaveLoadingView.setProgressValue(proccess);
        }
        mWaveLoadingView.setShapeType(WaveLoadingView.ShapeType.CIRCLE);
        mWaveLoadingView.setCenterTitleColor(Color.parseColor("#FF69B4"));
        mWaveLoadingView.setBottomTitleSize(18);
        mWaveLoadingView.setBorderWidth(10);
        mWaveLoadingView.setAmplitudeRatio(60);
        mWaveLoadingView.setWaveColor(Color.parseColor("#E1FFFF"));
        mWaveLoadingView.setBorderColor(Color.parseColor("#00FFFF"));
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
        User instanceUser = User.getInstance(getApplicationContext());
        int height = QMUIResHelper.getAttrDimen(getApplicationContext(), com.qmuiteam.qmui.R.attr.qmui_list_item_height);
        QMUICommonListItemView item_curBook = makeListItem("当前单词书：", (instanceUser.getLearnPlan().is_noPlan() == false) ?
                        wordBookList.get(User.getInstance(getApplicationContext()).getLearnPlan().getWordBook().getBookId()) : "",
                SELECT_CURBOOK, QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView item_DaylyWordsNum = makeListItem("每日学习单词数：",
                (instanceUser.getLearnPlan().is_noPlan()) ? "" : String.valueOf(User.getInstance(getApplicationContext()).getLearnPlan().getLearndaily() + "个"),
                SELECT_DAILYWORDSNUM, QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView item_CheckIndays = makeListItem("已打卡天数：",
                (instanceUser.getLearnPlan().is_noPlan() ? "" : String.valueOf(User.getInstance(getApplicationContext()).getLearnPlan().getClockInDate().size()) + "天"),
                SELECT_CHECKINDAYS, QMUICommonListItemView.ACCESSORY_TYPE_NONE);

        QMUICommonListItemView item_CreateTime = makeListItem("计划创建日期：",
                (instanceUser.getLearnPlan().is_noPlan()) ? "" : User.getInstance(getApplicationContext()).getLearnPlan().getCreateTime(),
                SELECT_NOACITION, QMUICommonListItemView.ACCESSORY_TYPE_NONE);

        QMUICommonListItemView item_GiveupPlan = makeListItem("放弃计划", "",
                SELECT_GIVEUPPLAN, QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:点击后弹出对应的窗口，简化用dialog
                int selectId = (int) view.getTag();
                if (User.getInstance(getApplicationContext()).getLearnPlan().is_noPlan() && selectId != SELECT_CURBOOK)
                    return;
                switch (selectId) {
                    case SELECT_CURBOOK:
                        //TODO:简洁，用WheelView直接选一个单词书

                        OptionsPickerView wordBookPickerView = new OptionsPickerBuilder(LearnPlanActivity.this, new OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                User.getInstance(null).getLearnPlan().setWordBook(String.valueOf(options1));
                                ((QMUICommonListItemView) view).getDetailTextView().setText(wordBookList.get(options1));


                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy日MM月dd日");
                                String createTime = formatter.format(new Date());
                                User.getInstance(getApplicationContext()).getLearnPlan().setCreateTime(createTime);

                                User.getInstance(getApplicationContext()).getLearnPlan().setLearndaily(30);

                                SweetAlertDialog pDialog = new SweetAlertDialog(LearnPlanActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                                pDialog.setTitleText("更改成功，开始学习吧");
                                pDialog.showConfirmButton(true);
                                pDialog.setConfirmText("确定");
                                pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        mGroupListView.getSection(0).removeFrom(mGroupListView);
                                        initListItems();
                                        pDialog.dismissWithAnimation();
                                    }
                                });
                                pDialog.setCancelable(false);
                                pDialog.show();
                            }
                        }).setItemVisibleCount(5).setCancelText("取消").setSubmitText("确定").build();
                        wordBookPickerView.setPicker(wordBookList);
                        wordBookPickerView.show();
                        break;
                    case SELECT_DAILYWORDSNUM:
                        OptionsPickerView optionsPickerView = new OptionsPickerBuilder(LearnPlanActivity.this, new OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                User.getInstance(getApplicationContext()).getLearnPlan().setLearndaily(options1 * 25+25);
                                ((QMUICommonListItemView) view).getDetailTextView().setText(String.valueOf(options1 * 25) + "个");
                                SweetAlertDialog pDialog = new SweetAlertDialog(LearnPlanActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                                pDialog.setTitleText("更改成功，明天继续加油");
                                pDialog.showConfirmButton(true);
                                pDialog.setConfirmText("确定");
                                pDialog.setCancelable(false);
                                pDialog.show();
                            }
                        }).setItemVisibleCount(5).setCancelText("取消").setSubmitText("确定").build();
                        dailyLearnNum.clear();
                        for (int i = 1; i <= 10; i++)
                            //TODO:拿到单词书的总单词量
                            dailyLearnNum.add(String.format("新词%d  复习%d  预计完成%d天", i * 5, i * 20, 3000 / i / 20));
                        optionsPickerView.setPicker(dailyLearnNum);
                        optionsPickerView.show();
                        break;
                    case SELECT_CHECKINDAYS:
                        //打开天数，什么也不干
                        ((QMUICommonListItemView) view).getDetailTextView().setText(
                                User.getInstance(getApplicationContext()).getLearnPlan().getCreateTime());
                        break;
                    case SELECT_GIVEUPPLAN:
                        if (User.getInstance(getApplicationContext()).getLearnPlan().is_noPlan() == false) {
                            new SweetAlertDialog(LearnPlanActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("真的吗")
                                    .setContentText("确定要放弃这个计划吗？放弃后押金会根据已完成度退还。")
                                    .setCancelText("取消 ")
                                    .setConfirmText("确定")
                                    .showCancelButton(true)
                                    .showConfirmButton(true)
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            //退费
                                            User.getInstance(getApplicationContext()).getLearnPlan().giveupPlan();
                                            sDialog.dismissWithAnimation();
                                        }
                                    })
                                    .show();
                        }
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

    private QMUICommonListItemView makeListItem(String title, String description, int callbackId, int accessoryType) {
        QMUICommonListItemView itemView = mGroupListView.createItemView(null,
                title, description,
                QMUICommonListItemView.HORIZONTAL,
                accessoryType,
                ITEMHEIGHT);
        itemView.getTextView().setTextSize(TypedValue.COMPLEX_UNIT_DIP, TEXTSIZE);
        itemView.setTag(callbackId);
        return itemView;
    }

}

