package com.bwie.wangkui.mynewjdshopping.homepage.SqDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ThinkPad on 2018/4/9.
 */

public class SqliteHelp extends SQLiteOpenHelper {
    public SqliteHelp(Context context) {
        super(context, "HistotySeek.db",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       sqLiteDatabase.execSQL("create table goodseek(name text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
