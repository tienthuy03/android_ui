package com.example.asmmob202;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtUser, edtPass, edtRepass;
    private Button btnDK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //ánh xạ
        edtUser = findViewById(R.id.edtUser);
        edtPass= findViewById(R.id.edtPass);
        edtRepass = findViewById(R.id.edtRepass);
        btnDK = findViewById(R.id.btnDK);

        //xét sự kiện cho nút đăng ký

        btnDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //lấy text
                String user = edtUser.getText().toString();
                String pass = edtPass.getText().toString();
                String repass = edtRepass.getText().toString();

                //xét điều kiện

                //validate cho form đăng ký
                if(user.length()==0)
                {
                    edtUser.requestFocus();// tạo trỏ chuột nhấp nháy tại vị trí của user
                    edtUser.setError("TÊN ĐĂNG NHẬP TRỐNG"); // hiển thị ra thông báo
                }
                else if(pass.length()==0)
                {
                    edtPass.requestFocus();
                    edtPass.setError("MẬT KHẨU TRỐNG");
                }
                else if(repass.length()==0)
                {
                    edtRepass.requestFocus();
                    edtRepass.setError("NHẬP LẠI MẬT KHẨU");
                }
                else if(pass.equals(repass))
                {
                    SharedPreferences preferences = getSharedPreferences("thongtin", MODE_PRIVATE);
                    //(SharedPreferences.Editor)Giao diện được sử dụng để sửa đổi các giá trị trong một SharedPreferences đối tượng.
                    //gọi edit để nhận SharedPreferences.Editor
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("Username", user);
                    editor.putString("Password", pass);

                    //commit giá trị mới đc sử dụng
                    editor.commit();
                    Toast.makeText(RegisterActivity.this, "Đăng ký  thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu đăng nhập sai", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}