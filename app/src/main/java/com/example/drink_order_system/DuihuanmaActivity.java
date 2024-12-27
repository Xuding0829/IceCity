package com.example.drink_order_system;

import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DuihuanmaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duihuanma);

        ImageView duihuan = findViewById(R.id.duihuan);
        duihuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DuihuanmaActivity.this, "兑换成功", Toast.LENGTH_SHORT).show();
                // 关闭当前Activity，返回到之前的Activity
                People_Data.pages = People_Data.pages + 1;
                finish();
            }
        });
    }
}