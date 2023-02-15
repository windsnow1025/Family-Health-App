package com.project.Sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;


import com.project.JDBC.AccountDao;
import com.project.Pojo.History;

import java.util.ArrayList;
import java.util.List;

public class UserLocalDao {
    private Context context;         //上下文
    private SqliteHelper dbHelper; //数据库访问对象
    private SQLiteDatabase db;       //可对数据库进行读写的操作对象

    private AccountDao accountDao=new AccountDao(); //用于数据同步
    public UserLocalDao(Context context) {
        this.context = context;
    }
    public UserLocalDao(){}
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


    //获取当前登录账号
    @SuppressLint("Range")
    public String getUser(){
        String stringReturn = null;
        Cursor cursor = db.query("user", null, "is_login = ?", new String[]{"true"}, null, null, null);
        if(cursor.moveToFirst()){
            do {
                {
                    stringReturn= cursor.getString(cursor.getColumnIndex("phone_number"));
                }
            }while (cursor.moveToNext());
        }
        return stringReturn;
    }

    public Boolean checkUser(String account){
        Boolean returnValue=false;
        Cursor cursor= db.query("user", null, "phone_number = ?", new String[]{account}, null, null, null);
        if(cursor.moveToNext()){
            returnValue=(!returnValue);
        }
        return  returnValue;
    }


    //登录状态改变 账号原本存在于本地库中则更新 否则添加进入 并设置登录状态为true
    public void addOrUpdateUser(String account){
        ContentValues values = new ContentValues();
        values.put("phone_number", account);
        values.put("is_login", "true");
        values.put("is_multipled", "false");               //多用户状态 备用
        if(checkUser(account))
        {
            db.update("user",values,"phone_number=?",new String[]{account});
        }
        else
        {
            db.insert("user", null, values);
        }
    }

    //账号登出
    public void userLoginout(String account)
    {
        ContentValues values= new ContentValues();
        values.put("is_login","false");
        db.update("user",values,"phone_number = ?",new String[]{account});
    }

    public Boolean sync(){
        ArrayList<History> historyArrayList=new ArrayList<>();
        ArrayList<History> reportArrayList=new ArrayList<>();
//        historyArrayList=accountDao.getHistory_Remote(getUser());
//        reportArrayList=accountDao.getReport_Remote(getUser());
        for (History history:historyArrayList) {
            ContentValues values = new ContentValues();
            values.put("phone_number",getUser());
            values.put("history_No",history.getNo());
            values.put("history_content",history.getContent());
            values.put("remind",history.getRemind());
            if(!isExistHistory(history.getNo()))
            {
                db.insert("history", null, values);
            }
            else
            {
                db.update("history", values, "history_No = ? AND phone_number=?",new String[]{String.valueOf(history.getNo()),getUser()});
            }
        }
        for (History report:reportArrayList)
        {
            ContentValues values = new ContentValues();
            values.put("phone_number",getUser());
            values.put("report_No",report.getNo());
            values.put("report_content",report.getContent());
            if(!isExistReport(report.getNo()))
            {
                db.insert("report", null, values);
            }
            else
            {
                db.update("report", values, "report_No = ? AND phone_number=?",new String[]{String.valueOf(report.getNo()),getUser()});
            }
        }
        return true;
    }

    public Boolean isExistHistory(Integer history_No){
        Cursor cursor=db.query("history",null,"history_No = ? AND phone_number=?",new String[]{String.valueOf(history_No),getUser()},null,null,null);
        return cursor != null && cursor.getCount() > 0;
    }
    public Boolean isExistReport(Integer report_No){
        Cursor cursor=db.query("report",null,"report_No = ? AND phone_number=?",new String[]{String.valueOf(report_No),getUser()},null,null,null);
        return cursor != null && cursor.getCount() > 0;
    }

    @SuppressLint("Range")
    public ArrayList<History> getHistory(String account)
    {
        ArrayList<History> historyArrayList =new ArrayList<>();
        Cursor cursor = db.query("history", null, "phone_number = ?", new String[]{account}, null, null, null);
        if(cursor.moveToFirst()){
            do{
                History history=new History();
                history.setNo(cursor.getInt(cursor.getColumnIndex("history_No")));
                history.setContent(cursor.getString(cursor.getColumnIndex("history_content")));
                history.setRemind(cursor.getString(cursor.getColumnIndex("remind")));
                historyArrayList.add(history);
            }while(cursor.moveToNext());
        }
        return historyArrayList;
    }

    public Boolean insertHistory(String account,History history){
        Boolean valueReturn=false;
        ContentValues values = new ContentValues();
        values.put("phone_number",getUser());
        values.put("history_No",history.getNo());
        values.put("history_content",history.getContent());
        values.put("remind",history.getRemind());
        db.insert("history", null, values);
        valueReturn=accountDao.insertHistory_Remote(account,history);
        return valueReturn;
    }
    public Boolean updateHistory(String account,History history){
        Boolean valueReturn=false;
        ContentValues values = new ContentValues();
        values.put("phone_number",getUser());
        values.put("history_No",history.getNo());
        values.put("history_content",history.getContent());
        values.put("remind",history.getRemind());
        db.update("history", values, "history_No = ? AND phone_number=?", new String[]{String.valueOf(history.getNo()),account});
//        valueReturn=accountDao.updateHistory_Remote(account,history);
        return valueReturn;
    }
    public Boolean deleteHistory(String account,Integer history_No){
        Boolean valueReturn=false;
        ContentValues values = new ContentValues();
        values.put("phone_number",getUser());
//        valueReturn=accountDao.deleteHistroy_Remote(account,history_No);
        db.delete("history", "history_No = ? AND phone_number=?", new String[]{String.valueOf(history_No),account});
        return valueReturn;
    }
    public Boolean insertReport(String account,History Report){
        Boolean valueReturn=false;
        ContentValues values = new ContentValues();
        values.put("phone_number",getUser());
        values.put("history_No",Report.getNo());
        values.put("history_content",Report.getContent());
        db.insert("Report", null, values);
//        valueReturn=accountDao.insertReport_Remote(account,Report);
        return valueReturn;
    }
    public Boolean updateReport(String account,History Report){
        Boolean valueReturn=false;
        ContentValues values = new ContentValues();
        values.put("phone_number",getUser());
        values.put("Report_No",Report.getNo());
        values.put("Report_content",Report.getContent());
        db.update("Report", values, "Report_No = ? AND phone_number=?", new String[]{String.valueOf(Report.getNo()),account});
//        valueReturn=accountDao.updateReport_Remote(account,Report);
        return valueReturn;
    }
    public Boolean deleteReport(String account,Integer Report_No){
        Boolean valueReturn=false;
        ContentValues values = new ContentValues();
        values.put("phone_number",getUser());
//        valueReturn=accountDao.deleteReport_Remote(account,Report_No);
        db.delete("Report", "Report_No = ? AND phone_number=?", new String[]{String.valueOf(Report_No),account});
        return valueReturn;
    }


}
