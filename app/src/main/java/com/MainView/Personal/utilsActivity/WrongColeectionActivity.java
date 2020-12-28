package com.MainView.Personal.utilsActivity;

import android.app.Activity;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.R;
import com.Unit.Preference;
import com.Unit.User;
import com.Unit.Word;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WrongColeectionActivity extends Activity {
    private QMUIGroupListView mGroupListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wrongcollection);
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
        QMUIGroupListView.Section section= QMUIGroupListView.newSection(getBaseContext())
                .setTitle("错词本")
                .setDescription("错词本列表");

        User.getInstance(getApplicationContext()).getWrongCollection().flushCollection();
        HashMap<String, Word> collectionList = User.getInstance(getApplicationContext()).getWrongCollection().getCollectionList(0, 0);
        Iterator iter = collectionList.entrySet().iterator();

        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            //Object key = entry.getKey();
            Word word =(Word) entry.getValue();
            QMUICommonListItemView item = makeListItem(word.getEnglish(), word.getChinese());
            section.addItemView(item,onClickListener);
        }
        section.addTo(mGroupListView);

    }

    private QMUICommonListItemView makeListItem(String word, String description) {
        QMUICommonListItemView normalItem = mGroupListView.createItemView(null,
                word, description,
                QMUICommonListItemView.VERTICAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        ImageView hornImage = new ImageView(getBaseContext());
        hornImage.setImageResource(R.drawable.horn);
        hornImage.setTag(word);
        hornImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:在这里对单词进行发音，暂时有问题，放不出来
                String word = v.getTag().toString();
                String url = "https://dict.youdao.com/dictvoice?audio=" + word + "&type=" +
                        ((User.getInstance(getApplicationContext()).getPreference().getPronunciation() == Preference.PRONUNCIATION_US) ? "0" : "1");

                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型
                try {
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.prepareAsync();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        normalItem.addAccessoryCustomView(hornImage);
        return normalItem;
    }
}
