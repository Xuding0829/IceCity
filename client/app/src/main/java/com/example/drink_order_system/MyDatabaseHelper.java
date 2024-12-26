package com.example.drink_order_system;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context mContext;  // 新增成员变量用于保存Context
    private static final String createBook = "create table People(" +
            " id integer primary key autoincrement," +
            "name text, " +
            "money integer, " +
            "pages integer)";

    public MyDatabaseHelper(Context context, String name, int version) {
        super(context, name, null, version);
        mContext = context;  // 在构造函数中将传入的context赋值给成员变量
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createBook);
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
        // 通过成员变量mContext来访问，此时就能正常识别了
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}