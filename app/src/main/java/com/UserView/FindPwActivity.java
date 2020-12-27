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
    private Button mBtn_fpwSendIdenCode,mBtn_fpwIdentify;
    private EditText mEdit_fpwNewpw,mEdit_fpwNewpwcf,mEdit_fpwPhone,mEdit_fpwIdenNum;
    @Override
    protected void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_findpassword);

       mEdit_fpwPhone = findViewById(R.id.Fpw_newpw);
       mEdit_fpwNewpw = findViewById(R.id.Fpw_newpw);
       mEdit_fpwNewpwcf = findViewById(R.id.Fpw_newpwcf);
       mBtn_fpwSendIdenCode = findViewById(R.id.Fpw_sentidentify);
       mBtn_fpwIdentify = findViewById(R.id.Fpw_identify);
       mEdit_fpwIdenNum = findViewById(R.id.Fpw_idencode);

       //发送验证码
       mBtn_fpwSendIdenCode.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(FindPwActivity.this,"验证码为：145674",Toast.LENGTH_SHORT).show();
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

                Toast.makeText(FindPwActivity.this, "验证成功！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent( FindPwActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
       


    }

}
