package com.UserView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.R;
import com.Unit.User;

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
                String phonenum = mEdt_regPhone.getText().toString().trim();               //获取电话
                String password = mEdt_regPassWord.getText().toString().trim();         //获取注册密码
                String passwordcf = mEdt_regPassWordCf.getText().toString().trim();       //获取密码确认
                String nikename = mEdt_regNikeName.getText().toString().trim();           //获取昵称
                //判断用户名、密码、邮箱等的合法性
                if(6 > mEdt_regUsername.getText().toString().length())
                {
                    mEdt_regUsername.setError("用户名小于6位");
                }
                else if(9 > mEdt_regEmail.getText().toString().length())
                {
                    mEdt_regEmail.setError("邮箱输入有误");
                }
                else if(11 != mEdt_regPhone.getText().toString().length())
                {
                    mEdt_regPhone.setError("手机输入有误");
                }
                else if(6 > mEdt_regNikeName.getText().toString().length())
                {
                    mEdt_regNikeName.setError("昵称小于6位");
                }
                else if(6 > mEdt_regPassWord.getText().toString().length())
                {
                    mEdt_regPassWord.setError("密码小于6位");
                }
                else if(6 > mEdt_regPassWordCf.getText().toString().length())
                {
                    mEdt_regPassWordCf.setError("密码小于6位");
                }
                else if(!password.equals(passwordcf)){
                    mEdt_regPassWordCf.setError("两次密码不一致");
                }
                else {
                    //对比已有账号，是否是新账号，否则注册新账号并提示“注册成功，返回登录界面”
                    User.getInstance(getApplicationContext()).registe(username,password,nikename,email,phonenum);
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
