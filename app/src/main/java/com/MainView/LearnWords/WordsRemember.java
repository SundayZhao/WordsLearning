package com.MainView.LearnWords;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.R;
import com.Unit.User;
import com.Unit.Word;
import com.Unit.WordBook;

import java.util.List;

public class WordsRemember extends AppCompatActivity {

    TextView eng = null;
    TextView ch = null;
    Button button1 = null;
    Button button2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_remember);
        eng= findViewById(R.id.textView2);
        ch= findViewById(R.id.textView3);

        button1 = findViewById(R.id.play);
        button2 = findViewById(R.id.next);

        User user = User.getInstance(null);
        List<Word> words = null;

        eng.setText(words.get(0).getEnglish());
        ch.setText(words.get(0).getChinese());

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String word = (String) eng.getText();
                try {
                    MediaPlayer player = new MediaPlayer();
                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    String link = "http://dict.youdao.com/dictvoice?type=0&audio=" + word;
                    player.setDataSource(link);
                    player.prepare();
                    player.start();

                } catch (Exception e) {

                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (words.size()>0){
                    words.remove(0);
                    eng.setText(words.get(0).getEnglish());
                    ch.setText(words.get(0).getChinese());
                }
                else{
                    finish();
                }
            }
        });
    }
}