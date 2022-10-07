package com.example.asmmob202;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUser, edtPass;
    private Button btnDN;
    private CheckBox chkRemember;
    private TextView txtTaotk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // ánh xạ
        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
        btnDN = findViewById(R.id.btnDN);
        chkRemember  = findViewById(R.id.chkRemember);
        txtTaotk = findViewById(R.id.txtTaotk);

        // gọi ra đối tượng getSharedPreferences
        SharedPreferences preferences = getSharedPreferences("thongtin",MODE_PRIVATE);
        // .edit nhận giá trị từ   SharedPreferences.Editor
        SharedPreferences.Editor editor = preferences.edit();
        //xét cho checkbox
        boolean isRemember = preferences.getBoolean("ghinho", false);
        // xét điều kiện cho checkbox
        if(isRemember){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
        btnDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lây ra text của user pass
                String userInput = edtUser.getText().toString();
                String passInput = edtPass.getText().toString();
                // lấy text của userSP , passSP
                String userSP = preferences.getString("Username","");
                String passSP = preferences.getString("Password","");


                // xét diều kiện

                if (userInput.equals(userSP) && passInput.equals(passSP)) {
                    editor.putBoolean("ghinho", chkRemember.isChecked());
                    editor.commit();
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                } else {

                    Toast.makeText(LoginActivity.this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //xét sự kiện cho nút đăng ký
        txtTaotk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}