package com.project.Sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class SqliteHelper extends SQLiteOpenHelper {
    // 数据库名称
    public static final String DATABASE = "Android.db";
    // 数据库版本号
    public static final int VERSION = 1;
    // 创建DB对象时的构造函数

    private Context context;

    public SqliteHelper(Context context) {
        super(context, DATABASE, null, VERSION);

        this.context=context;
    }

    // 创建用户表User
    public static final String CREATE_USER = "create table user ("
            + "phone_number text primary key,"
            + "is_login text,"
            + "is_multipled text)";
    public static final String CREATE_HISTORY = "create table history ("
            + "ID integer primary key,"
            + "phone_number text,"
            + "history_No integer,"
            + "history_content text,"
            + "remind text)";
    public static final String CREATE_REPORT = "create table report ("
            + "ID integer primary key,"
            + "phone_number text,"
            + "report_No integer,"
            + "report_content text)";
    public static final String CREATE_COLOK = "create table colok ("
            + "ID integer primary key,"
            + "phone_number text,"
            + "history_No integer,"
            + "date text,"
            + "title text,"
            + "cycle text)";



    // 创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_REPORT);
        db.execSQL(CREATE_HISTORY);
        db.execSQL(CREATE_COLOK);
    }

    // 升级数据库
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
        db.execSQL("drop table if exists report");
        db.execSQL("drop table if exists history");
        db.execSQL("drop table if exists colok");
        onCreate(db);
    }
}