package com.MainView.LearnWords;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.R;
import com.Unit.User;
import com.Unit.Word;
import com.Unit.WordBook;
import com.Unit.WrongCollection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WordsRemember extends AppCompatActivity {

    TextView eng = null;
    TextView ch = null;
    Button button1 = null;
    Button button2 = null;
    boolean added = false;
    EditText editText = null;
    ImageButton mark = null;
    int words_num = 0;
    int correct_num = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_remember);
        eng= findViewById(R.id.textView2);
        ch= findViewById(R.id.textView3);
        editText = findViewById(R.id.input);
        button1 = findViewById(R.id.play);
        button2 = findViewById(R.id.next);
        mark = findViewById(R.id.imageButton);

        User user = User.getInstance(null);
        ArrayList<Word> words = user.getLearnPlan().getWordBook().getWords();
        words_num = words.size();
        correct_num = words_num;
        eng.setText(words.get(0).getEnglish());
        eng.setVisibility(View.INVISIBLE);
        ch.setText(words.get(0).getChinese());

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String word = (String) eng.getText();
                try {
                    MediaPlayer player = new MediaPlayer();
//                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    player.setAudioAttributes( new AudioAttributes
                            .Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build());
                    String link = "http://dict.youdao.com/dictvoice?type=0&audio=" + word;
                    player.setDataSource(link);
//                    player.prepare();
                    player.start();

                } catch (Exception e) {

                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input_eng = editText.getText().toString();
                if (input_eng.equals(words.get(0).getEnglish())){
                    Toast.makeText(getApplicationContext(),"拼写正确",Toast.LENGTH_LONG).show();
                    if (words.size()>1){
                        words.remove(0);
                        added = false;
                        eng.setText(words.get(0).getEnglish());
                        eng.setVisibility(View.INVISIBLE);
                        ch.setText(words.get(0).getChinese());
                        editText.setText("");
                        mark.setVisibility(View.VISIBLE);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"记忆完成", Toast.LENGTH_LONG).show();
                        user.getLearnPlan().setHasLearned(words_num);
                        user.getLearnPlan().clockIn(new Date(), words_num, correct_num);
                        finish();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"拼写错误",Toast.LENGTH_LONG).show();
                    eng.setVisibility(View.VISIBLE);
                    if (!added) {
                        correct_num = correct_num - 1;
                        user.getWrongCollection().addWord(words.get(0));
                        added = true;
                    }
                }
            }
        });

        mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.getDiffCollection().addWord(words.get(0));
                Toast.makeText(getApplicationContext(),"加入收藏成功！", Toast.LENGTH_LONG).show();
                mark.setVisibility(View.INVISIBLE);
            }
        });
    }
}