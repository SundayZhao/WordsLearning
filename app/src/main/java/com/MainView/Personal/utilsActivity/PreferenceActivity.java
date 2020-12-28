package com.MainView.Personal.utilsActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;

import com.R;
import com.Unit.Preference;
import com.Unit.User;
import com.cazaea.sweetalert.SweetAlertDialog;
import com.dao.RemotoDatabase;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;



public class PreferenceActivity extends Activity {
    private static final float TEXTSIZE = 20.0f;
    private static final int ITEMHEIGHT = 150;
    private static final int SELECT_NOACITION = -1;
    private final int SELECT_USERNAME = 0;
    private final int SELECT_NICKNAME = 1;
    private final int SELECT_PASSWORD = 2;
    private final int SELECT_EMAIL = 3;
    private final int SELECT_PHONE = 4;

    private final int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    private QMUIGroupListView mGroupListView = null;
    private QMUIRoundButton qmuiRoundButton = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        //开始初始化界面
        initViews();
    }

    private void initViews() {
        mGroupListView = (QMUIGroupListView) findViewById(R.id.list_preference);
        qmuiRoundButton = (QMUIRoundButton) findViewById(R.id.button_preference_logout);
        qmuiRoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(PreferenceActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("真的吗")
                        .setContentText("确定要退出登录吗")
                        .setCancelText("取消 ")
                        .setConfirmText("确定")
                        .showCancelButton(true)
                        .showConfirmButton(true)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                User.getInstance(getApplicationContext()).logOut();
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });
        initListItems();
    }

    private void initListItems() {
        //TODO:个人设置
        QMUICommonListItemView item_uuid = makeListItem("账号",
                User.getInstance(getApplicationContext()).getUsername(), SELECT_USERNAME,QMUICommonListItemView.ACCESSORY_TYPE_NONE);

        QMUICommonListItemView item_nickname = makeListItem("昵称",
                User.getInstance(getApplicationContext()).getNickName(), SELECT_NICKNAME,QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView item_password = makeListItem("修改密码", "", SELECT_PASSWORD,QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView item_email = makeListItem("邮箱",
                User.getInstance(getApplicationContext()).getEmail(), SELECT_EMAIL,QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView item_pohone = makeListItem("手机号",
                User.getInstance(getApplicationContext()).getPhoneNum(), SELECT_PHONE,QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:点击后弹出对应的窗口，简化用dialog
                int selectId = (int) v.getTag();
                //System.out.println(selectId);
                QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(PreferenceActivity.this);
                switch (selectId) {
                    case SELECT_USERNAME:
                       //什么也不做
                        break;
                    case SELECT_NICKNAME:
                        //改名字
                        builder.setTitle("改个名字")
                                .setSkinManager(QMUISkinManager.defaultInstance(PreferenceActivity.this))
                                .setPlaceholder("新名字")
                                .setInputType(InputType.TYPE_CLASS_TEXT)
                                .addAction("保存", new QMUIDialogAction.ActionListener() {
                                    @Override
                                    public void onClick(QMUIDialog dialog, int index) {
                                        String changeText=builder.getEditText().getText().toString();
                                        User.getInstance(getApplicationContext()).setNickName(changeText);
                                        ((QMUICommonListItemView)v).getDetailTextView().setText(changeText);
                                        dialog.dismiss();
                                    }
                                })
                                .addAction("取消", new QMUIDialogAction.ActionListener() {
                                    @Override
                                    public void onClick(QMUIDialog dialog, int index) {
                                        dialog.dismiss();
                                    }
                                }).create(mCurrentDialogStyle).show();
                        break;
                    case SELECT_PASSWORD:
                        //跳转到修改密码的窗口
                        break;
                    case SELECT_PHONE:
                        builder.setTitle("改个手机号")
                                .setSkinManager(QMUISkinManager.defaultInstance(PreferenceActivity.this))
                                .setPlaceholder("新手机")
                                .setInputType(InputType.TYPE_CLASS_TEXT)
                                .addAction("保存", new QMUIDialogAction.ActionListener() {
                                    @Override
                                    public void onClick(QMUIDialog dialog, int index) {
                                        String changeText=builder.getEditText().getText().toString();
                                        User.getInstance(getApplicationContext()).setPhoneNum(changeText);
                                        ((QMUICommonListItemView)v).getDetailTextView().setText(changeText);
                                        dialog.dismiss();
                                    }
                                })
                                .addAction("取消", new QMUIDialogAction.ActionListener() {
                                    @Override
                                    public void onClick(QMUIDialog dialog, int index) {
                                        dialog.dismiss();
                                    }
                                }).create(mCurrentDialogStyle).show();
                        break;
                    case SELECT_EMAIL:
                        builder.setTitle("改个邮箱")
                                .setSkinManager(QMUISkinManager.defaultInstance(getBaseContext()))
                                .setPlaceholder("新邮箱")
                                .setInputType(InputType.TYPE_CLASS_TEXT)
                                .addAction("保存", new QMUIDialogAction.ActionListener() {
                                    @Override
                                    public void onClick(QMUIDialog dialog, int index) {
                                        String changeText=builder.getEditText().getText().toString();
                                        ((QMUICommonListItemView)v).getDetailTextView().setText(changeText);
                                        User.getInstance(getApplicationContext()).setEmail(changeText);
                                        dialog.dismiss();
                                    }
                                })
                                .addAction("取消", new QMUIDialogAction.ActionListener() {
                                    @Override
                                    public void onClick(QMUIDialog dialog, int index) {
                                        dialog.dismiss();
                                    }
                                }).create(mCurrentDialogStyle).show();
                        break;
                }
            }
        };

        QMUIGroupListView.newSection(getBaseContext())
                .setTitle("个人设置")
                .setDescription("")
                .addItemView(item_uuid, onClickListener)
                .addItemView(item_nickname, onClickListener)
                .addItemView(item_password, onClickListener)
                .addItemView(item_email, onClickListener)
                .addItemView(item_pohone, onClickListener)
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
