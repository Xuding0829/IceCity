package com.example.drink_order_system;

//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WentifankuiActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Msg> msgList;
    private MsgAdapter adapter;
    private ImageView send;
    private RecyclerView recyclerView;
    private EditText inputText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wentifankui);

        // 初始化变量
        msgList = new ArrayList<>();
        send = findViewById(R.id.send);
        recyclerView = findViewById(R.id.recyclerView);
        inputText = findViewById(R.id.inputText);

        initMsg();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        recyclerView.setAdapter(adapter);

        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == send) {
            String content = inputText.getText().toString();
            if (!content.isEmpty()) {
                Msg msg = new Msg(content, Msg.TYPE_SENT);
                msgList.add(msg);
                adapter.notifyItemInserted(msgList.size() - 1);
                recyclerView.scrollToPosition(msgList.size() - 1);

                msgList.add(new Msg("对不起，我无法回答您的问题", Msg.TYPE_RECEIVED));
                adapter.notifyItemInserted(msgList.size() - 1);
                recyclerView.scrollToPosition(msgList.size() - 1);

                if ("exit".equals(content)) {
                    finish();
                }

                inputText.setText("");
            }
        }
    }

    private void initMsg() {
        msgList.add(new Msg("您好，我是您的智能小助手，很高兴为您服务！", Msg.TYPE_RECEIVED));
        msgList.add(new Msg("Hello,我想问你一些问题", Msg.TYPE_SENT));
        msgList.add(new Msg("您好，我是您的智能小助手，很高兴为您服务！ ", Msg.TYPE_RECEIVED));
    }

}