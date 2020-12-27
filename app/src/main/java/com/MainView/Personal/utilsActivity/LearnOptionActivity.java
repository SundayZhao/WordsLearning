package com.MainView.Personal.utilsActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.R;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

public class LearnOptionActivity extends Activity {
    private static final float TEXTSIZE = 20.0f;
    private static final int ITEMHEIGHT = 150;
    private static final int SELECT_NOACITION = -1;
    private final int SELECT_STUDYMODE=0;
    private final int SELECT_STUDYINORDER=1;
    private final int SELECT_PRONUNCIATION=2;
    private final int SELECT_AUTOPLAY=3;

    private QMUIGroupListView mGroupListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learnoption);
        //开始初始化界面
        initViews();
    }

    private void initViews() {
        mGroupListView = (QMUIGroupListView) findViewById(R.id.list_learnoptions);

        initListItems();
    }

    private void initListItems() {
        //TODO:学习设置
        QMUICommonListItemView item_StuduModel =  makeListItem("学习模式：","", SELECT_STUDYMODE);

        QMUICommonListItemView item_StudyInOrder =  makeListItem("学习顺序","", SELECT_STUDYINORDER);

        QMUICommonListItemView item_Pronunciation =  makeListItem("当前发音：","", SELECT_PRONUNCIATION);

        QMUICommonListItemView item_AutoPlay =  makeListItem("自动发音","", SELECT_AUTOPLAY);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:点击后弹出对应的窗口，简化用dialog
                int selectId=(int)v.getTag();
                System.out.println(selectId);
                switch (selectId){
                    case SELECT_STUDYMODE:
                        break;
                    case SELECT_STUDYINORDER:
                        break;
                    case SELECT_PRONUNCIATION:
                        break;
                    case SELECT_AUTOPLAY:
                        break;
                }
            }
        };//默认文字在左边   自定义加载框按钮

        QMUIGroupListView.newSection(getBaseContext())
                .setTitle("学习设置")
                .setDescription("")
                .addItemView(item_StuduModel, onClickListener)
                .addItemView(item_StudyInOrder, onClickListener)
                .addItemView(item_Pronunciation, onClickListener)
                .addItemView(item_AutoPlay, onClickListener)
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
