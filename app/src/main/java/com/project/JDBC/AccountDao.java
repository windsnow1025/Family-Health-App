package com.project.JDBC;

import androidx.annotation.NonNull;

import com.project.Exception.updateInfoException;
import com.project.Pojo.History;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class AccountDao {
    public AccountDao() {
        if(JDBCHelper.connection==null)
        {
            System.out.println("test");
        }
    }
    public String checkUserPassword(String account,String password)
    {
        String stringReturn=null;
        String sql="SELECT phone_number,password FROM user WHERE phone_number=?";
        String passwordGet=null;
        try {
            PreparedStatement preparedStatement=JDBCHelper.connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
            {
                passwordGet=resultSet.getString("password");
                if(passwordGet.equals(password))
                {
                    stringReturn=account;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stringReturn;
    }
    /**
     * 测试包含错误密码 错误账号 空账号 正确三种情况 这三种情况均通过
     * 未测试空密码
     * */
    public Boolean checkUserUnique(String account)
    {
        String sql="SELECT phone_number FROM user WHERE phone_number=?";
        try {
            PreparedStatement preparedStatement=JDBCHelper.connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
            {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    /**
     * 生日使用String类型 格式为yyyy-MM-dd如2022-06-18等
     * 此处类型问题原本想使用LocalDate类型 但setDate似乎不支持LocalDate
     * 使用错误的生日会导致异常
     * */
    public String insertUser(@NonNull String account,String password,String sex,String birthday) {
        String accountReturn=null;
        String sql="INSERT INTO user (phone_number,password,sex,birthday) VALUES (?,?,?,?)";
        if((!checkUserUnique(account))&&(account!=null)&&(password!=null)&&(!password.equals(""))) {
            try {
                PreparedStatement preparedStatement=JDBCHelper.connection.prepareStatement(sql);
                preparedStatement.setString(1, account);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3,sex);
                preparedStatement.setString(4,birthday);
                //preparedStatement.setDate(4,birthday);
                if(preparedStatement.executeUpdate()!=0){
                    accountReturn=account;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return accountReturn;
    }

    public HashMap<String,String> getUserInformation(String account){
        String sql="SELECT username,sex,email,birthday from user WHERE phone_number=?";
        HashMap userInfo=new HashMap<String,String>();
        try {
            PreparedStatement preparedStatement=JDBCHelper.connection.prepareStatement(sql);
            preparedStatement.setString(1, account);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
            {
                userInfo.put("username",resultSet.getString("username"));
                userInfo.put("email",resultSet.getString("email"));
                userInfo.put("sex",resultSet.getString("sex"));
                userInfo.put("birthday",resultSet.getString("birthday"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userInfo;
    }
/**
 * hashmap中 key为username,sex,email,password，birthday
 * */
    public Boolean updateUserInformation(String account,HashMap<String,String> userInfo_update) throws updateInfoException {
        String sql_username="UPDATE user set username=? WHERE phone_number=?";
        String sql_sex="UPDATE user set sex=? WHERE phone_number=?";
        String sql_email="UPDATE user set email=? WHERE phone_number=?";
        String sql_password="UPDATE user set password=? WHERE phone_number=?";
        //String sql_birthday="UPDATE user set birthday=? WHERE phone_number=?";
        String sex_update=userInfo_update.get("sex");
        String username_update=userInfo_update.get("username");
        String email_update=userInfo_update.get("email");
        String password_update=userInfo_update.get("password");
        if(!checkUserUnique(account)){
            throw new updateInfoException("账号不存在");
        }
        if(sex_update==null&&username_update==null&&email_update==null&&password_update==null)
        {
            throw new updateInfoException("没有需要修改的内容");
        }
        if(sex_update!=null)
        {
            try {
                PreparedStatement preparedStatement=JDBCHelper.connection.prepareStatement(sql_sex);
                preparedStatement.setString(1, sex_update);
                preparedStatement.setString(2,account);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(username_update!=null)
        {
            try {
                PreparedStatement preparedStatement=JDBCHelper.connection.prepareStatement(sql_username);
                preparedStatement.setString(1, username_update);
                preparedStatement.setString(2,account);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(email_update!=null)
        {
            try {
                PreparedStatement preparedStatement=JDBCHelper.connection.prepareStatement(sql_email);
                preparedStatement.setString(1, email_update);
                preparedStatement.setString(2,account);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(password_update!=null)
        {
            try {
                PreparedStatement preparedStatement=JDBCHelper.connection.prepareStatement(sql_password);
                preparedStatement.setString(1, password_update);
                preparedStatement.setString(2,account);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    public ArrayList<History> getHistory_Remote(String account)
    {
        ArrayList<History> historyArrayList=new ArrayList<>();
        String sql="SELECT ID,history_No,history_content,remind FROM history WHERE phone_number=? ORDER BY history_No";
        try {
            PreparedStatement preparedStatement=JDBCHelper.connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            ResultSet resultSet= preparedStatement.executeQuery();
            while(resultSet.next()){
                History history_temp=new History();
                history_temp.setID(resultSet.getInt("ID"));
                history_temp.setNo(resultSet.getInt("history_No"));
                history_temp.setContent(resultSet.getString("history_content"));
                history_temp.setRemind(resultSet.getString("remind"));
                historyArrayList.add(history_temp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return historyArrayList;
    }
    public Boolean updateHistory_Remote(String account, History history_update){
        Boolean valueReturn=false;
        String sql="UPDATE history SET remind=?,history_content=? where phone_number=? and history_No=?";
        try {
            PreparedStatement preparedStatement=JDBCHelper.connection.prepareStatement(sql);
            preparedStatement.setString(1, history_update.getRemind());
            preparedStatement.setString(2, history_update.getContent());
            preparedStatement.setString(3,account);
            preparedStatement.setInt(4,history_update.getNo());
            if(preparedStatement.executeUpdate()>0)
            {
                valueReturn=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return valueReturn;
    }
    public Boolean deleteHistroy_Remote(String account, Integer history_No){
        Boolean valueReturn=false;
        String sql="DELETE FROM history where phone_number=? and history_No=?";
        if(history_No==null){
            history_No=0;
        }
        try {
            PreparedStatement preparedStatement=JDBCHelper.connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            preparedStatement.setInt(2,history_No);
            if(preparedStatement.executeUpdate()>0){
                valueReturn=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return valueReturn;
    }

    public  Boolean insertHistory_Remote(String account, History history_insert){
        Boolean valueReturn=false;
        String sql="INSERT INTO history (phone_number,history_content,history_No,remind) VALUES (?,?,?,?)";
        Integer nextNo=getHistoryCount(account)+1;
        try {
            PreparedStatement preparedStatement=JDBCHelper.connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            preparedStatement.setString(2, history_insert.getContent());
            preparedStatement.setInt(3,nextNo);
            preparedStatement.setString(4,history_insert.getRemind());
            if(preparedStatement.executeUpdate()>0){
                valueReturn=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return valueReturn;
    }

    //计数 在插入记录时控制序号
    private Integer getHistoryCount(String account){
        Integer count = 0;
        String sql="SELECT history_No from history where phone_number=? ORDER BY history_No DESC limit 1";
        try {
            PreparedStatement preparedStatement=JDBCHelper.connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                count=resultSet.getInt("history_No");
            }
            else
            {
                count=1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }


    public ArrayList<History> getReport_Remote(String account)
    {
        ArrayList<History> historyArrayList=new ArrayList<>();
        String sql="SELECT ID,report_No,report_content FROM report WHERE phone_number=? ORDER BY report_No";
        try {
            PreparedStatement preparedStatement=JDBCHelper.connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            ResultSet resultSet= preparedStatement.executeQuery();
            while(resultSet.next()){
                History history_temp=new History();
                history_temp.setID(resultSet.getInt("ID"));
                history_temp.setNo(resultSet.getInt("report_No"));
                history_temp.setContent(resultSet.getString("report_content"));
                historyArrayList.add(history_temp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return historyArrayList;
    }
    public Boolean updateReport_Remote(String account, History history_update){
        Boolean valueReturn=false;
        String sql="UPDATE report SET report_content=? where phone_number=? and report_No=?";
        try {
            PreparedStatement preparedStatement=JDBCHelper.connection.prepareStatement(sql);
            preparedStatement.setString(1, history_update.getContent());
            preparedStatement.setString(2,account);
            preparedStatement.setInt(3,history_update.getNo());
            if(preparedStatement.executeUpdate()>0)
            {
                valueReturn=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return valueReturn;
    }
    public Boolean deleteReport_Remote(String account, Integer history_No){
        Boolean valueReturn=false;
        String sql="DELETE FROM report where phone_number=? and report_No=?";
        if(history_No==null){
            history_No=0;
        }
        try {
            PreparedStatement preparedStatement=JDBCHelper.connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            preparedStatement.setInt(2,history_No);
            if(preparedStatement.executeUpdate()>0){
                valueReturn=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return valueReturn;
    }

    public  Boolean insertReport_Remote(String account, History history_insert){
        Boolean valueReturn=false;
        String sql="INSERT INTO report (phone_number,report_content,report_No) VALUES (?,?,?)";
        Integer nextNo=getReportCount(account)+1;
        try {
            PreparedStatement preparedStatement=JDBCHelper.connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            preparedStatement.setString(2, history_insert.getContent());
            preparedStatement.setInt(3,nextNo);
            if(preparedStatement.executeUpdate()>0){
                valueReturn=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return valueReturn;
    }

    //计数 在插入记录时控制序号
    private Integer getReportCount(String account){
        Integer count = 0;
        String sql="SELECT report_No from report where phone_number=?  ORDER BY report_No DESC limit 1";
        try {
            PreparedStatement preparedStatement=JDBCHelper.connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                count=resultSet.getInt("report_No");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }
}
