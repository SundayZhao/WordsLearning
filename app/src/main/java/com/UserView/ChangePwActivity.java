package com.UserView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.OnClickAction;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.R;
import com.Unit.User;

import static android.widget.Toast.LENGTH_SHORT;

public class ChangePwActivity extends AppCompatActivity {
    private Button mBtn_cpwChangePw,mBtn_fpwTurnBack;
    private EditText mEdit_cpwOldPw,mEdit_cpwNewPw,mEdit_cpwNewPwCf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pw);

        mEdit_cpwOldPw = findViewById(R.id.Cpw_oldpw);
        mEdit_cpwNewPw = findViewById(R.id.Cpw_newpw);
        mEdit_cpwNewPwCf = findViewById(R.id.Cpw_newpwcf);
        mBtn_cpwChangePw = findViewById(R.id.Cpw_change);
        mBtn_fpwTurnBack = findViewById(R.id.Cpw_turnback);

        String oldpassword = mEdit_cpwOldPw.getText().toString().trim();              //获取旧密码
        String newpassword = mEdit_cpwNewPw.getText().toString().trim();              //获取新密码
        String newpasswordcf = mEdit_cpwNewPwCf.getText().toString().trim();       //获取新密码确认

        //修改密码按钮
        mBtn_cpwChangePw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(6 > mEdit_cpwNewPw.getText().toString().length())
                {
                    mEdit_cpwNewPw.setError("密码小于6位");
                }
                else if(6 > mEdit_cpwNewPwCf.getText().toString().length())
                {
                    mEdit_cpwNewPwCf.setError("密码小于6位");
                }
                else if(!newpassword.equals(newpasswordcf))
                {
                    mEdit_cpwNewPwCf.setError("两次密码不一致");
                }
                else{
                    //修改密码
                    User.getInstance(getApplicationContext()).changePassword(newpassword);
                    Toast.makeText(ChangePwActivity.this, "密码修改成功", LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        //返回按钮
        mBtn_fpwTurnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangePwActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}