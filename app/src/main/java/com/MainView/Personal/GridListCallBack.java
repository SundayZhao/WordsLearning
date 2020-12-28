package com.MainView.Personal;

import android.content.Context;
import android.content.Intent;

import com.MainView.MainActivity;
import com.MainView.Personal.utilsActivity.DiffCollectionActivity;
import com.Unit.User;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

public class GridListCallBack {

    public static final int STATE_DIFF_COLLECTION=1;
    public static final int STATE_WRONG_COLLECTION=2;
    public static final int STATE_LEARN_DATA=3;
    public static final int STATE_ABOUT_US=4;
    public static final int STATE_FAVORATE=5;
    public static final int STATE_USER_AGREEMENT=6;
    public static final int STATE_PREFERENCE=7;
    public static final int STATE_LEARNOPTIONS=8;
    public static final int STATE_LEARNPLANS = 9;

    private Context context=null;
    public GridListCallBack(Context context){
        this.context=context;
    }
    public void callbackFunction(int position){
        if(context==null || User.getInstance(context).isLogged()==false)return;
        Intent intent = new Intent();
        //System.out.println(position);
        switch (position){
            case STATE_DIFF_COLLECTION :
                intent.setAction("android.intent.action.DiffCollectionActivity");
                intent.addCategory("android.intent.category.DEFAULT");
                context.startActivity(intent);
                break;
            case STATE_WRONG_COLLECTION:
                intent.setAction("android.intent.action.WrongColeectionActivity");
                intent.addCategory("android.intent.category.DEFAULT");
                context.startActivity(intent);
                break;
            case STATE_LEARN_DATA:
                intent.setAction("android.intent.action.LearnDataActivity");
                intent.addCategory("android.intent.category.DEFAULT");
                context.startActivity(intent);
                break;
            case STATE_ABOUT_US:
                intent.setAction("android.intent.action.AboutUsActivity");
                intent.addCategory("android.intent.category.DEFAULT");
                context.startActivity(intent);
                break;
            case STATE_FAVORATE:
                QMUIDialog.MessageDialogBuilder builder=new QMUIDialog.MessageDialogBuilder(context);
                builder.setTitle("谢谢你的好评")
                        .setMessage("App还没上架，你的好评已经收到了")
                        .addAction("好的", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        }).show();
                break;
            case STATE_USER_AGREEMENT:
                intent.setAction("android.intent.action.UserAgreementActivity");
                intent.addCategory("android.intent.category.DEFAULT");
                context.startActivity(intent);
                break;
            case STATE_PREFERENCE:
                intent.setAction("android.intent.action.PreferenceActivity");
                intent.addCategory("android.intent.category.DEFAULT");
                context.startActivity(intent);
                break;
            case STATE_LEARNOPTIONS:
                intent.setAction("android.intent.action.LearnOptionActivity");
                intent.addCategory("android.intent.category.DEFAULT");
                context.startActivity(intent);
                break;
            case STATE_LEARNPLANS:
                intent.setAction("android.intent.action.LearnPlanActivity");
                intent.addCategory("android.intent.category.DEFAULT");
                context.startActivity(intent);
                break;
        }
    }
}
