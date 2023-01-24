package com.project.Sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteHelper extends SQLiteOpenHelper {
    // 数据库名称
    public static final String DATABASE = "Android.db";
    // 数据库版本号
    public static final int VERSION = 1;
    // 创建DB对象时的构造函数
    public SqliteHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    // 创建用户表User
    public static final String CREATE_USER = "create table user ("
            + "phone_number text primary key,"
            + "username text,"
            + "email text,"
            + "birthday text,"
            + "sex text,"
            + "is_login text,"
            + "is_multipled text)";

    // 创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
    }

    // 升级数据库
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists User");
        db.execSQL("drop table if exists Cart");
        db.execSQL("drop table if exists MyOrder");
        onCreate(db);
    }
}
