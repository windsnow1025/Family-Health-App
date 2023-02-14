package com.project.JDBC;

import androidx.annotation.NonNull;

import com.project.Exception.updateInfoException;
import com.project.Pojo.Account;
import com.project.Pojo.UserInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;


public class AccountDao {
    public AccountDao() {}
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
     * 生日使用String类型 格式为yyyy-MM-dd如2022-07-08等
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



//    /**
//     * 登录 需要account 返回Interger表示登录结果
//     * 200表示正常登录
//     * 301表示密码错误
//     * 404表示账号不存在
//     * */
//    public Integer Login(@NonNull Account account){
//        Integer code=0;
//        String sql="SELECT phone_number,password FROM user WHERE phone_number=?";
//        Account accountGet = null;
//        try {
//            PreparedStatement preparedStatement=JDBCHelper.connection.prepareStatement(sql);
//            preparedStatement.setString(1,account.getPhoneNumber());
//            ResultSet resultSet=preparedStatement.executeQuery();
//            if(resultSet.next())
//            {
//                accountGet.setPhoneNumber(resultSet.getString("phone_number"));
//                accountGet.setPassword(resultSet.getString("password"));
//            }
//            else {
//                code=404;
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        if(account.getPassword().equals(accountGet.getPassword())&&code!=404) {
//            code = 200;
//        }
//        else {
//            code=301;
//        }
//        return code;
//    }
//    /**
//     * 获取用户信息
//     * */
//    public UserInfo getInfo(@NonNull Account account){
//        UserInfo userInfo = new UserInfo();
//        String sql="SELECT username,sex,email,birthday from user WHERE phone_number=?";
//        try {
//            PreparedStatement preparedStatement=JDBCHelper.connection.prepareStatement(sql);
//            preparedStatement.setString(1, account.getPhoneNumber());
//            ResultSet resultSet=preparedStatement.executeQuery();
//            if(resultSet.next())
//            {
//                userInfo.setUsername(resultSet.getString("username"));
//                userInfo.setEmail(resultSet.getString("email"));
//                userInfo.setSex(resultSet.getString("sex"));
//                userInfo.setBirthday(resultSet.getString("birthday"));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return userInfo;
//    }
//    /**
//     * Login()函数调用查询账户
//     * 200表示成功查询账户但因某些原因没有成功修改密码
//     * 201表示表示密码修改成功
//     * 301表示密码错误
//     * 404表示账号不存在
//     * */
//    public Integer AlterPassword(@NonNull Account account,@NonNull String newPassword) {
//        Integer code;
//        code=Login(account);
//        String sql="UPDATE user set password=? WHERE phone_number=?";
//        if(code==200)
//        {
//            try {
//                PreparedStatement preparedStatement = JDBCHelper.connection.prepareStatement(sql);
//                preparedStatement.setString(1, newPassword);
//                preparedStatement.setString(2, account.getPhoneNumber());
//                if(preparedStatement.executeUpdate()!=0)
//                {
//                    code=201;
//                }
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return code;
//    }
//
//    public Integer AlterUserName(@NonNull Account account,@NonNull String newUserName) {
//        Integer code=0;
//        String sql="UPDATE user set username=? WHERE phone_number=?";
//        try {
//            PreparedStatement preparedStatement=JDBCHelper.connection.prepareStatement(sql);
//            preparedStatement.setString(1,newUserName);
//            preparedStatement.setString(2, account.getPhoneNumber());
//            if(preparedStatement.executeUpdate()!=0)
//            {
//                code=200;
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return code;
//    }
//    public Integer AlterEmail(@NonNull Account account,@NonNull String newEmail){
//        Integer code=0;
//        String sql="UPDATE user set email=? WHERE phone_number=?";
//        try {
//            PreparedStatement preparedStatement=JDBCHelper.connection.prepareStatement(sql);
//            preparedStatement.setString(1,newEmail);
//            preparedStatement.setString(2, account.getPhoneNumber());
//            if(preparedStatement.executeUpdate()!=0)
//            {
//                code=200;
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return code;
//    }
}
