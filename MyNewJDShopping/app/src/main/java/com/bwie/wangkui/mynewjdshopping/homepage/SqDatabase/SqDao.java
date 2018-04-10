package com.bwie.wangkui.mynewjdshopping.homepage.SqDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ThinkPad on 2018/4/9.
 */

public class SqDao {
    private Context context;
    private final SQLiteDatabase database;

    public SqDao(Context context) {
        this.context = context;
        SqliteHelp help = new SqliteHelp(context);
        database = help.getWritableDatabase();
    }
    //添加数据库
    public void add(String str){
        ContentValues values = new ContentValues();
        values.put("name",str);
        database.insert("goodseek",null,values);
    }
}
