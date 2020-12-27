package com.UserView;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.R;

public class LoginActivity  extends AppCompatActivity {
    private Button btn_log; //登录按钮
    private TextView mtv_reg,mtv_findpw;//注册和找回密码按钮
    private EditText edt_username,edt_password;//账号和密码框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edt_username = findViewById(R.id.Lg_userName);
        edt_password = findViewById(R.id.Lg_passWord);
        mtv_findpw = findViewById(R.id.Lg_findpw);
        btn_log = findViewById(R.id.Lg_lg);
        mtv_reg = findViewById(R.id.Lg_reg);

        //登录按钮事件
        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edt_username.getText().toString().trim();        //获取用户名
                String password = edt_password.getText().toString().trim();        //获取密码

                Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
            }
        });

        //找回密码
        mtv_findpw.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);       //下划线
        mtv_findpw.setOnClickListener(new View.OnClickListener() {      //设置点击事件
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( LoginActivity.this,FindPwActivity.class);
                startActivity(intent);
            }
        });

        //注册
        mtv_reg.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);       //下划线
        mtv_reg.setOnClickListener(new View.OnClickListener() {        //设置点击事件
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}