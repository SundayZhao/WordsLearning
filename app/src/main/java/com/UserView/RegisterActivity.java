package com.UserView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.R;

import static android.widget.Toast.LENGTH_SHORT;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText mEdt_regUsername = findViewById(R.id.Reg_username);     //账号输入框
        EditText mEdt_regEmail = findViewById(R.id.Reg_email);           //邮箱输入框
        EditText mEdt_regPhone = findViewById(R.id.Reg_phone);           //电话输入框
        EditText mEdt_regNikeName = findViewById(R.id.Reg_nikename);      //昵称框
        EditText mEdt_regPassWord = findViewById(R.id.Reg_password);     //密码输入框
        EditText mEdt_regPassWordCf = findViewById(R.id.Reg_passwordcf); //密码确认框
        Button mBtn_register = findViewById(R.id.Reg_regist);            //注册按钮
        Button mBtn_turnBack = findViewById(R.id.Reg_turnback);           //返回按钮

        //注册
        mBtn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mEdt_regUsername.getText().toString().trim();         //获取用户名
                String email = mEdt_regEmail.getText().toString().trim();                //获取邮箱
                String phone = mEdt_regPhone.getText().toString().trim();               //获取电话
                String password = mEdt_regPassWord.getText().toString().trim();         //获取注册密码
                String passwordcf = mEdt_regPassWordCf.getText().toString().trim();       //获取密码确认
                String nikename = mEdt_regNikeName.getText().toString().trim();           //获取昵称

                //对比已有账号，是否是新账号，否则注册新账号并提示“注册成功，返回登录界面”
            if(!password.equals(passwordcf)){
                Toast.makeText(RegisterActivity.this,"两次密码输入不一致",LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(RegisterActivity.this, "注册成功，返回登录界面", LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                //注册成功则返回提示框“注册成功”，否则返回账号名已存在
            }

            }
        });

        //返回
        mBtn_turnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
