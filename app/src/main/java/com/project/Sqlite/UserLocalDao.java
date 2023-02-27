package com.project.Sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;


import com.project.JDBC.AlertDao;
import com.project.JDBC.HistoryDao;
import com.project.JDBC.ReportDao;
import com.project.JDBC.UserDao;
import com.project.Pojo.Alert;
import com.project.Pojo.History;
import com.project.Pojo.Report;
import com.project.Pojo.UserInfo;

import java.util.ArrayList;

public class UserLocalDao {
    private Context context;         //上下文
    private SqliteHelper dbHelper; //数据库访问对象
    private SQLiteDatabase db;       //可对数据库进行读写的操作对象

    private UserDao userDao; //用于数据同步
    private ReportDao reportDao;
    private HistoryDao historyDao;
    private AlertDao alertDao;
    public UserLocalDao(Context context) {
        this.context = context;
        reportDao=new ReportDao();
        historyDao=new HistoryDao();
        alertDao=new AlertDao();
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
    public void addOrUpdateUser(UserInfo userInfo){
        ContentValues values = new ContentValues();
        values.put("phone_number", userInfo.getPhone_number());
        values.put("username", userInfo.getUsername());
        values.put("email", userInfo.getEmail());
        values.put("birthday", userInfo.getBirthday());
        values.put("sex", userInfo.getSex());
        values.put("is_login", "true");
        values.put("is_multipled", "false");               //多用户状态 备用
        if(checkUser(userInfo.getPhone_number()))
        {
            db.update("user",values,"phone_number=?",new String[]{userInfo.getPhone_number()});
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
    //账号切换
    public void userAlter(String newAccount,String oldAccount){
        userLoginout(oldAccount);
        ContentValues values= new ContentValues();
        values.put("is_login","true");
        db.update("user",values,"phone_number = ?",new String[]{newAccount});
    }


    public Boolean sync(){
        userDao.getConnection();
        ArrayList<History> historyArrayList=new ArrayList<>();
        ArrayList<Report> reportArrayList=new ArrayList<>();
        ArrayList<Alert> alertArrayList=new ArrayList<>();
        historyArrayList=historyDao.getHistoryList(getUser());
        reportArrayList=reportDao.getReportList(getUser());
        alertArrayList=alertDao.getAlertList(getUser());
        for (History history:historyArrayList) {
            if(!isExistHistory(history.getHistory_No()))
            {
                insertHistory(getUser(),history);
            }
            else
            {
                updateHistory(getUser(),history);
            }
        }
        for (Report report:reportArrayList)
        {
            if(!isExistReport(report.getReport_No()))
            {
                insertReport(getUser(),report);
            }
            else
            {
                updateReport(getUser(),report);
            }
        }
        for(Alert alert:alertArrayList)
        {
            if(!isExistAlert(alert.getAlert_No())){
                insertAlert(getUser(),alert);
            }
            else
            {
                updateAlert(getUser(),alert);
            }
        }
        userDao.closeConnection();
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
    public Boolean isExistAlert(Integer alert_No){
        Cursor cursor=db.query("alert",null,"alert_No= ? AND phone_number= ?",new String[]{String.valueOf(alert_No),getUser()},null,null,null);
        return cursor !=null &&cursor.getCount()>0;
    }

    public Boolean insertHistory(String account,History history){
        Boolean valueReturn=false;
        ContentValues values = new ContentValues();
        values.put("phone_number",account);
        values.put("history_No",history.getHistory_No());
        values.put("history_date",history.getHistory_date());
        values.put("history_place",history.getHistory_place());
        values.put("history_doctor",history.getHistory_doctor());
        values.put("history_organ",history.getHistory_organ());
        values.put("conclusion",history.getConclusion());
        values.put("symptom",history.getSymptom());
        values.put("suggestion",history.getSuggestion());
        db.insert("history", null, values);
        return valueReturn;
    }
    public Boolean updateHistory(String account,History history){
        Boolean valueReturn=false;
        ContentValues values = new ContentValues();
        values.put("phone_number",account);
        values.put("history_No",history.getHistory_No());
        values.put("history_date",history.getHistory_date());
        values.put("history_place",history.getHistory_place());
        values.put("history_doctor",history.getHistory_doctor());
        values.put("history_organ",history.getHistory_organ());
        values.put("conclusion",history.getConclusion());
        values.put("symptom",history.getSymptom());
        values.put("suggestion",history.getSuggestion());
        db.update("history", values, "history_No = ? AND phone_number=?", new String[]{String.valueOf(history.getHistory_No()),account});
//        valueReturn=accountDao.updateHistory_Remote(account,history);
        return valueReturn;
    }
    public Boolean deleteHistory(String account,Integer history_No){
        Boolean valueReturn=false;
//        valueReturn=accountDao.deleteHistroy_Remote(account,history_No);
        db.delete("history", "history_No = ? AND phone_number=?", new String[]{String.valueOf(history_No),account});
        return valueReturn;
    }

    public Boolean insertReport(String account,Report report){
        Boolean valueReturn=false;
        ContentValues values = new ContentValues();
        values.put("phone_number",account);
        values.put("report_No",report.getReport_No());
        values.put("report_content",report.getReport_content());
        values.put("report_type",report.getReport_type());
        values.put("report_place",report.getReport_place());
        values.put("report_date",report.getReport_date());
        db.insert("report", null, values);
//        valueReturn=accountDao.insertReport_Remote(account,Report);
        return valueReturn;
    }
    public Boolean updateReport(String account,Report report){
        Boolean valueReturn=false;
        ContentValues values = new ContentValues();
        values.put("phone_number",account);
        values.put("report_No",report.getReport_No());
        values.put("report_content",report.getReport_content());
        values.put("report_type",report.getReport_type());
        values.put("report_place",report.getReport_place());
        values.put("report_date",report.getReport_date());
        db.update("report", values, "Report_No = ? AND phone_number=?", new String[]{String.valueOf(report.getReport_No()),account});
//        valueReturn=accountDao.updateReport_Remote(account,Report);
        return valueReturn;
    }
    public Boolean deleteReport(String account,Integer Report_No){
        Boolean valueReturn=false;
//        valueReturn=accountDao.deleteReport_Remote(account,Report_No);
        db.delete("report", "Report_No = ? AND phone_number=?", new String[]{String.valueOf(Report_No),account});
        return valueReturn;
    }

    public Boolean insertAlert(String account,Alert alert){
        Boolean valueReturn=false;
        ContentValues values=new ContentValues();
        values.put("phone_number",account);
        values.put("Alert_No",alert.getAlert_No());
        values.put("date",alert.getDate());
        values.put("cycle",alert.getCycle());
        values.put("content",alert.getContent());
        values.put("type",alert.getType());
        values.put("type_No",alert.getType_No());
        db.insert("alert",null,values);
        return valueReturn;
    }
    public Boolean updateAlert(String account,Alert alert){
        Boolean valueReturn=false;
        ContentValues values=new ContentValues();
        values.put("phone_number",account);
        values.put("Alert_No",alert.getAlert_No());
        values.put("date",alert.getDate());
        values.put("cycle",alert.getCycle());
        values.put("content",alert.getContent());
        values.put("type",alert.getType());
        values.put("type_No",alert.getType_No());
        db.update("alert",values,"Alert_No=? AND phone_number=?",new String[]{String.valueOf(alert.getAlert_No()),account});
        return valueReturn;
    }
    public Boolean deleteAlert(String account,Integer alert_No){
        Boolean valueReturn=false;
        db.delete("alert", "Report_No = ? AND phone_number=?", new String[]{String.valueOf(alert_No),account});
        return valueReturn;
    }


/*
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


    //以下暂未测试
    public Boolean setColok(Integer history_No,String title,String date,String cycle){
        Boolean valueReturn=false;
        ContentValues values = new ContentValues();
        values.put("phone_number",getUser());
        values.put("history_No",history_No);
        values.put("title",title);
        values.put("date",date);
        values.put("cycle",cycle);
        if(db.insert("colok", null, values)>0)
        {
            valueReturn=true;
        }
        return valueReturn;
    }
    @SuppressLint("Range")
    public ArrayList<Alert> getColok(){
        ArrayList<Alert> alertArrayList =new ArrayList<>();
        Cursor cursor = db.query("colok", null, "phone_number = ?", new String[]{getUser()}, null, null, null);
        if(cursor.moveToFirst()){
            do{
                Alert alert =new Alert();
                alert.setTilte(cursor.getString(cursor.getColumnIndex("title")));
                alert.setCycle(cursor.getString(cursor.getColumnIndex("cycle")));
                alert.setDate(cursor.getString(cursor.getColumnIndex("date")));
                alert.setHistory_No(cursor.getInt(cursor.getColumnIndex("history_No")));
                alertArrayList.add(alert);
            }while(cursor.moveToNext());
        }
        return alertArrayList;
    }

    */

}
