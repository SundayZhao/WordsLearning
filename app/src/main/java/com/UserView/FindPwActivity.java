package com.UserView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.R;

public class FindPwActivity extends AppCompatActivity {
    private Button mBtn_fpwSendIdenCode,mBtn_fpwIdentify,mBtn_fpwTurnBack;
    private EditText mEdit_fpwNewpw,mEdit_fpwNewpwcf,mEdit_fpwPhone,mEdit_fpwIdenNum;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpassword);

        mEdit_fpwPhone = findViewById(R.id.Fpw_userphone);
        mEdit_fpwNewpw = findViewById(R.id.Fpw_newpw);
        mEdit_fpwNewpwcf = findViewById(R.id.Fpw_newpwcf);
        mBtn_fpwSendIdenCode = findViewById(R.id.Fpw_sentidentify);
        mBtn_fpwIdentify = findViewById(R.id.Fpw_identify);
        mEdit_fpwIdenNum = findViewById(R.id.Fpw_idencode);
        mBtn_fpwTurnBack = findViewById(R.id.Fpw_turnback);

        //发送验证码
        mBtn_fpwSendIdenCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FindPwActivity.this,"发送成功！验证码为：665674",Toast.LENGTH_SHORT).show();
            }
        });

        //找回密码
        mBtn_fpwIdentify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newpw = mEdit_fpwNewpw.getText().toString();                 //获取新密码
                String newpwcf = mEdit_fpwNewpwcf.getText().toString();             //获取新密码
                String phone = mEdit_fpwPhone.getText().toString();                   //获取电话
                String identifynum = mEdit_fpwIdenNum.getText().toString();            //获取输入的验证码
                //判断手机号、新密码等合法性
                if(11 != phone.length())
                {
                    mEdit_fpwPhone.setError("手机输入不正确");
                }
                else if(6 > newpw.length())
                {
                    mEdit_fpwNewpw.setError("新密码小于6位");
                }
                else if(6 > newpwcf.length())
                {
                    mEdit_fpwNewpwcf.setError("新密码小于6位");
                }
                else if(! newpw.equals(newpwcf))
                {
                    mEdit_fpwNewpwcf.setError("两次密码不一致");
                }
                else if( ! identifynum.equals("665674"))
                {
                    mEdit_fpwIdenNum.setError("验证码错误");
                }
                else {
                    Toast.makeText(FindPwActivity.this, "密码找回成功！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FindPwActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        //返回按钮
        mBtn_fpwTurnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindPwActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
