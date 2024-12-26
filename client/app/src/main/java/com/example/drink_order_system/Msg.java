package com.example.drink_order_system;

public class Msg {
    public final String content;
    public final int type;

    // 构造方法
    public Msg(String content, int type) {
        this.content = content;
        this.type = type;
    }

    // Getter 方法
    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }

     //或者可以直接在Msg类中定义常量
     public static final int TYPE_RECEIVED = 0;
     public static final int TYPE_SENT = 1;
}
