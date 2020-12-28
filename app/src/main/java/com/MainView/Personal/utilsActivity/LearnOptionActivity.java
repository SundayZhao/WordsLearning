package com.MainView.Personal.utilsActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.R;
import com.Unit.Preference;
import com.Unit.User;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
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
    private final int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

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
        QMUICommonListItemView item_StuduModel =  makeListItem("学习模式：",
                (User.getInstance(getApplicationContext()).getPreference().getStudyMode()==Preference.MODE_SPELL)?"拼写模式":"再认模式", SELECT_STUDYMODE,QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView item_StudyInOrder =  makeListItem("学习顺序：",
                (User.getInstance(getApplicationContext()).getPreference().getStudyInOrder()==Preference.STUDY_OREDER_INORDER)?"顺序":"随机", SELECT_STUDYINORDER,QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView item_Pronunciation =  makeListItem("当前发音：",
                (User.getInstance(getApplicationContext()).getPreference().getPronunciation()==Preference.PRONUNCIATION_US)?"美式":"英式", SELECT_PRONUNCIATION,QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView item_AutoPlay =  makeListItem("自动发音","", SELECT_AUTOPLAY,QMUICommonListItemView.ACCESSORY_TYPE_SWITCH);
        item_AutoPlay.getSwitch().setChecked((User.getInstance(getApplicationContext()).getPreference().getAutoPlay()==Preference.AUTOPLAY_OPEN)?true:false);
        item_AutoPlay.getSwitch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    //自动发音
                    User.getInstance(getApplicationContext()).getPreference().setAutoPlay(Preference.AUTOPLAY_OPEN);
                }else{
                    User.getInstance(getApplicationContext()).getPreference().setAutoPlay(Preference.AUTOPLAY_CLOSE);
                }
            }
        });

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:点击后弹出对应的窗口，简化用dialog
                int selectId=(int)v.getTag();
                QMUIDialog.CheckableDialogBuilder builder = new QMUIDialog.CheckableDialogBuilder(LearnOptionActivity.this);
                //System.out.println(selectId);
                switch (selectId){
                    case SELECT_STUDYMODE:
                        //改学习模式
                        String []studymode=new String[]{"拼写模式","再认模式"};
                        builder.setTitle("学习模式")
                                .setCheckedIndex(0)
                                .setSkinManager(QMUISkinManager.defaultInstance(LearnOptionActivity.this))
                                .addItems(studymode, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(LearnOptionActivity.this, "你选择了 " + studymode[which], Toast.LENGTH_SHORT).show();
                                        User.getInstance(getApplicationContext()).getPreference().setStudyMode(which);
                                        ((QMUICommonListItemView) v).getDetailTextView().setText(studymode[which]);
                                        dialog.dismiss();
                                    }
                                })
                                .create(mCurrentDialogStyle).show();
                        break;
                    case SELECT_STUDYINORDER:
                        String []studyOrder=new String[]{"无序","有序"};
                        builder.setTitle("学习顺序")
                                .setCheckedIndex(0)
                                .setSkinManager(QMUISkinManager.defaultInstance(LearnOptionActivity.this))
                                .addItems(studyOrder, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(LearnOptionActivity.this, "你选择了 " + studyOrder[which], Toast.LENGTH_SHORT).show();
                                        User.getInstance(getApplicationContext()).getPreference().setStudyInOrder(which);
                                        ((QMUICommonListItemView) v).getDetailTextView().setText(studyOrder[which]);
                                        dialog.dismiss();
                                    }
                                })
                                .create(mCurrentDialogStyle).show();
                        break;
                    case SELECT_PRONUNCIATION:
                        String []pronunciation=new String[]{"英式","美式"};
                        builder.setTitle("单词发音")
                                .setCheckedIndex(0)
                                .setSkinManager(QMUISkinManager.defaultInstance(LearnOptionActivity.this))
                                .addItems(pronunciation, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(LearnOptionActivity.this, "你选择了 " + pronunciation[which], Toast.LENGTH_SHORT).show();
                                        User.getInstance(getApplicationContext()).getPreference().setPronunciation(which);
                                        ((QMUICommonListItemView) v).getDetailTextView().setText(pronunciation[which]);
                                        dialog.dismiss();
                                    }
                                })
                                .create(mCurrentDialogStyle).show();
                        break;
                    case SELECT_AUTOPLAY:
                        //无视
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

    private QMUICommonListItemView makeListItem(String title, String description, int callbackId,int accessoryType) {
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
