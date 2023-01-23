package com.project.JDBC;

import androidx.annotation.NonNull;

import com.project.Pojo.Account;
import com.project.Pojo.UserInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class AccountDao {
    public AccountDao() {}
    /**
     * 登录 需要account 返回Interger表示登录结果
     * 200表示正常登录
     * 301表示密码错误
     * 404表示账号不存在
     * */
    public Integer Login(@NonNull Account account){
        Integer code=0;
        String sql="SELECT phone_number,password FROM user WHERE phone_number=?";
        Account accountGet = null;
        try {
            PreparedStatement preparedStatement=JDBCHelper.connection.prepareStatement(sql);
            preparedStatement.setString(1,account.getPhoneNumber());
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
            {
                accountGet.setPhoneNumber(resultSet.getString("phone_number"));
                accountGet.setPassword(resultSet.getString("password"));
            }
            else {
                code=404;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(account.getPassword().equals(accountGet.getPassword())&&code!=404) {
            code = 200;
        }
        else {
            code=301;
        }
        return code;
    }
    /**
     * 获取用户信息
     * */
    public UserInfo getInfo(@NonNull Account account){
        UserInfo userInfo = new UserInfo();
        String sql="SELECT username,sex,email,birthday from user WHERE phone_number=?";
        try {
            PreparedStatement preparedStatement=JDBCHelper.connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getPhoneNumber());
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
            {
                userInfo.setUsername(resultSet.getString("username"));
                userInfo.setEmail(resultSet.getString("email"));
                userInfo.setSex(resultSet.getString("sex"));
                userInfo.setBirthday(resultSet.getString("birthday"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userInfo;
    }
    /**
     * Login()函数调用查询账户
     * 200表示成功查询账户但因某些原因没有成功修改密码
     * 201表示表示密码修改成功
     * 301表示密码错误
     * 404表示账号不存在
     * */
    public Integer AlterPassword(@NonNull Account account,@NonNull String newPassword) {
        Integer code;
        code=Login(account);
        String sql="UPDATE user set password=? WHERE phone_number=?";
        if(code==200)
        {
            try {
                PreparedStatement preparedStatement = JDBCHelper.connection.prepareStatement(sql);
                preparedStatement.setString(1, newPassword);
                preparedStatement.setString(2, account.getPhoneNumber());
                if(preparedStatement.executeUpdate()!=0)
                {
                    code=201;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return code;
    }
    /**
     * 注册 必须填入账户，性别和生日
     * 生日使用String类型 格式为yyyy-MM-dd如2022-07-08等
     * 此处类型问题原本想使用LocalDate类型 但setDate似乎不支持LocalDate
     * 200表示账户存在无法注册
     * 202注册成功
     * */
    public Integer Register(@NonNull Account account, @NonNull String sex, @NonNull String birthday) {
        Integer code=0;
        code=Login(account);
        String sql="INSERT INTO user (phone_number,password,sex,birthday) VALUES (?,?,?,?)";
        if(code!=200)
        {
            try {
                PreparedStatement preparedStatement=JDBCHelper.connection.prepareStatement(sql);
                preparedStatement.setString(1, account.getPhoneNumber());
                preparedStatement.setString(2, account.getPassword());
                preparedStatement.setString(3,sex);
                preparedStatement.setString(4,birthday);
                //preparedStatement.setDate(4,birthday);
                if(preparedStatement.executeUpdate()!=0){
                    code=202;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return code;
    }
    public Integer AlterUserName(@NonNull Account account,@NonNull String newUserName) {
        Integer code=0;
        String sql="UPDATE user set username=? WHERE phone_number=?";
        try {
            PreparedStatement preparedStatement=JDBCHelper.connection.prepareStatement(sql);
            preparedStatement.setString(1,newUserName);
            preparedStatement.setString(2, account.getPhoneNumber());
            if(preparedStatement.executeUpdate()!=0)
            {
                code=200;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return code;
    }
    public Integer AlterEmail(@NonNull Account account,@NonNull String newEmail){
        Integer code=0;
        String sql="UPDATE user set email=? WHERE phone_number=?";
        try {
            PreparedStatement preparedStatement=JDBCHelper.connection.prepareStatement(sql);
            preparedStatement.setString(1,newEmail);
            preparedStatement.setString(2, account.getPhoneNumber());
            if(preparedStatement.executeUpdate()!=0)
            {
                code=200;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return code;
    }
}
