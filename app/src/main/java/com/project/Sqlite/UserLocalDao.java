package com.project.Sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.project.Pojo.Account;
import com.project.Pojo.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class UserLocalDao {
    private Context context;         //上下文
    private SqliteHelper dbHelper; //数据库访问对象
    private SQLiteDatabase db;       //可对数据库进行读写的操作对象
    public UserLocalDao(Context context) {
        this.context = context;
    }
    // 创建并打开数据库（如果数据库已存在直接打开）
    public void open() throws SQLiteException{
        dbHelper = new SqliteHelper(context);
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException exception) {
            db = dbHelper.getReadableDatabase();
        }
    }

    // 关闭数据库
    public void close() {
        if (db != null) {
            db.close();
            db = null;
        }
    }

    // 查询账号的信息
    @SuppressLint("Range")
    public UserInfo findUser(Account account) {
        Cursor cursor = db.query("user", null, "phone_number = ?", new String[]{account.getPhoneNumber()}, null, null, null);
        UserInfo userInfo=new UserInfo();
        if(cursor.moveToFirst()){
            do {
                {
                    userInfo.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                    userInfo.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                    userInfo.setBirthday(cursor.getString(cursor.getColumnIndex("birthday")));
                    userInfo.setSex(cursor.getString(cursor.getColumnIndex("sex")));
                }
            }while (cursor.moveToNext());
        }
        return userInfo;
    }
    // 查询指定账号是否存在在本地数据库中
    public boolean is_Exist(Account account) {
        Cursor cursor = db.query("user", null, "phone_number = ?", new String[]{account.getPhoneNumber()}, null, null, null);
        if (cursor == null || cursor.getCount() < 1) {
            return false;
        }
        cursor.close();
        return true;
    }

    // 用户登录后添加用户信息到数据库中
    public void addUser(Account account, UserInfo userInfo){
        ContentValues values = new ContentValues();
        values.put("phoneNumber", account.getPhoneNumber());
        values.put("username", userInfo.getUsername());
        values.put("birthday", userInfo.getBirthday());
        values.put("email", userInfo.getEmail());
        values.put("sex", userInfo.getSex());
        values.put("is_login", "true");
        db.insert("user", null, values);
    }

    // 修改用户信息
    public void updateUser(Account account, UserInfo userInfo) {
        ContentValues values = new ContentValues();
        values.put("username", userInfo.getUsername());
        values.put("birthday", userInfo.getBirthday());
        values.put("email", userInfo.getEmail());
        values.put("sex", userInfo.getSex());
        values.put("is_login", "true");
        db.update("user", values, "phone_number = ?", new String[]{account.getPhoneNumber()});
    }

    // 账号登出
    public void userLoginout(Account account)
    {
        ContentValues values= new ContentValues();
        values.put("is_login","false");
        db.update("user",values,"phone_number = ?",new String[]{account.getPhoneNumber()});
    }
    // 多账号功能 需要账号信息存入本地数据库
    public void MultiUserAdd(Account account)
    {
        ContentValues values= new ContentValues();
        values.put("is_multipled","true");
        db.update("user",values,"phone_number = ?",new String[]{account.getPhoneNumber()});
    }
    public void MultiUserDelete(Account account)
    {
        ContentValues values= new ContentValues();
        values.put("is_multipled","false");
        db.update("user",values,"phone_number = ?",new String[]{account.getPhoneNumber()});
    }
    // 多账号切换登录
    public void MultiUserChange(Account old_account,Account new_account)
    {
        ContentValues value_old= new ContentValues();
        ContentValues value_new= new ContentValues();
        value_old.put("is_login","false");
        value_new.put("is_login","true");
        db.update("user",value_old,"phone_number = ?",new String[]{old_account.getPhoneNumber()});
        db.update("user",value_new,"phone_number = ?",new String[]{new_account.getPhoneNumber()});
    }
    //返回多用户列表
    @SuppressLint("Range")
    public List<UserInfo> getAllUser(){
        Cursor cursor=db.query("user",null,"is_multipled = ?",new String[]{"true"},null,null,null);
        List<UserInfo> userList=new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                {
                    UserInfo userInfo=new UserInfo();
                    userInfo.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                    userInfo.setSex(cursor.getString(cursor.getColumnIndex("sex")));
                    userList.add(userInfo);
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return userList;
    }

}
