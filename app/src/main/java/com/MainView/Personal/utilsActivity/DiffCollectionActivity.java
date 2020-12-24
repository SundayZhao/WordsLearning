package com.MainView.Personal.utilsActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.R;
import com.Unit.User;
import com.qmuiteam.qmui.widget.QMUILoadingView;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

public class DiffCollectionActivity extends Activity {
    private QMUIGroupListView mGroupListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diffcollection);
        //开始初始化界面
        initViews();
    }

    private void initViews() {
        mGroupListView = (QMUIGroupListView) findViewById(R.id.list_diffcollection);

        initListItems();
    }

    private void initListItems() {
        //TODO:在这里第一次加载，拿到难词表，后面再加个下拉layout
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //TODO:点击单词后可以进入详情页
            }
        };//默认文字在左边   自定义加载框按钮

        //TODO:拿到难词本列表后调用makeListItem,for循环
        QMUICommonListItemView test=makeListItem("单词","单词释义\n例句\n更多例句");
        QMUIGroupListView.newSection(getBaseContext())
                .setTitle("难词本")
                .setDescription("难词本列表")
                .addItemView(test, onClickListener)
                .addTo(mGroupListView);
    }
    private QMUICommonListItemView makeListItem(String word,String description){
        QMUICommonListItemView normalItem = mGroupListView.createItemView(null,
                word,description,
                QMUICommonListItemView.VERTICAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        ImageView hornImage=new ImageView(getBaseContext());
        hornImage.setImageResource(R.drawable.horn);
        hornImage.setTag(word);
        hornImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:在这里对单词进行发音
                String word=v.getTag().toString();
            }
        });
        normalItem.addAccessoryCustomView(hornImage);
        return normalItem;
    }
}
