package com.project.JDBC;

import androidx.annotation.NonNull;

import com.project.Pojo.UserInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class UserDao extends JDBCHelper{
    public UserDao() {
    }
    //account 均指手机号码
    public String checkUserPassword(String account,String password) throws TimeoutException {
        String valueReturn=null;
        FutureTask<String> futureTask= new FutureTask<>(()->{
            getConnection();
            String value=checkUserPasswordImpl(account,password);
            closeConnection();
            return value;
        });
        new Thread(futureTask).start();
        try {
            valueReturn=futureTask.get(2, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            futureTask.cancel(true);
        }
        return valueReturn;
    }
    public String checkUserPasswordImpl(String account,String password) {
        String stringReturn=null;
        String sql="SELECT phone_number,password FROM user WHERE phone_number=?";
        String passwordGet=null;
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
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
    public Boolean checkUserUnique(String account) throws TimeoutException {
        Boolean valueReturn=false;
        FutureTask<Boolean> futureTask=new FutureTask<>(()->{
            getConnection();
            Boolean value=checkUserUniqueImpl(account);
            closeConnection();
            return value;
        });
        new Thread(futureTask).start();
        try {
            valueReturn=futureTask.get(2, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            futureTask.cancel(true);
        }
        return valueReturn;
    }
    public Boolean checkUserUniqueImpl(String account) {
        Boolean valueReturn=false;
        String sql="SELECT phone_number FROM user WHERE phone_number=?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
            {
                valueReturn=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return valueReturn;
    }

    /**
     * 生日使用String类型 格式为yyyy-MM-dd如2022-06-18等
     * 此处类型问题原本想使用LocalDate类型 但setDate似乎不支持LocalDate
     * 使用错误的生日会导致异常
     * */
    public String insertUser(String account,String password,String sex,String birthday) throws TimeoutException {
        String valueReturn=null;
        FutureTask<String> futureTask=new FutureTask<>(()->{
            getConnection();
            String value=insertUserImpl(account, password, sex, birthday);
            closeConnection();
            return value;
        });
        new Thread(futureTask).start();
        try {
            valueReturn=futureTask.get(2, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            futureTask.cancel(true);
        }
        return valueReturn;
    }
    public String insertUserImpl(String account,String password,String sex,String birthday) throws TimeoutException {
        String accountReturn=null;
        String sql="INSERT INTO user (phone_number,password,sex,birthday) VALUES (?,?,?,?)";
        if((!checkUserUnique(account))&&(account!=null)&&(password!=null)&&(!password.equals(""))) {
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(sql);
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
    public UserInfo getUserInformation(String account) throws TimeoutException {
        UserInfo valueReturn=new UserInfo();
        FutureTask<UserInfo> futureTask=new FutureTask<>(()->{
            getConnection();
            UserInfo value=getUserInformationImpl(account);
            closeConnection();
            return value;
        });
        new Thread(futureTask).start();
        try {
            valueReturn=futureTask.get(2, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            futureTask.cancel(true);
        }
        return valueReturn;
    }
    public UserInfo getUserInformationImpl(String account){
        String sql="SELECT username,sex,email,birthday from user WHERE phone_number=?";
        UserInfo userInfo=new UserInfo();
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1, account);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
            {
                userInfo.setPhone_number(account);
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
    public Boolean updateUserInformation(String account,HashMap<String,String> userInfo_update) throws TimeoutException {
        Boolean valueReturn=false;
        FutureTask<Boolean> futureTask=new FutureTask<>(()->{
            getConnection();
            Boolean value=updateUserInformationImpl(account,userInfo_update);
            closeConnection();
            return value;
        });
        new Thread(futureTask).start();
        try {
            valueReturn=futureTask.get(2, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            futureTask.cancel(true);
        }
        return valueReturn;
    }
    public Boolean updateUserInformationImpl(String account,HashMap<String,String> userInfo_update){
        getConnection();
        String sql_username="UPDATE user set username=? WHERE phone_number=?";
        String sql_sex="UPDATE user set sex=? WHERE phone_number=?";
        String sql_email="UPDATE user set email=? WHERE phone_number=?";
        String sql_password="UPDATE user set password=? WHERE phone_number=?";
        //String sql_birthday="UPDATE user set birthday=? WHERE phone_number=?";
        String sex_update=userInfo_update.get("sex");
        String username_update=userInfo_update.get("username");
        String email_update=userInfo_update.get("email");
        String password_update=userInfo_update.get("password");
        if(sex_update!=null)
        {
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(sql_sex);
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
                PreparedStatement preparedStatement=connection.prepareStatement(sql_username);
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
                PreparedStatement preparedStatement=connection.prepareStatement(sql_email);
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
                PreparedStatement preparedStatement=connection.prepareStatement(sql_password);
                preparedStatement.setString(1, password_update);
                preparedStatement.setString(2,account);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }
}
