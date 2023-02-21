package com.project.JDBC;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class JDBCHelper {
    private static final String db_url="jdbc:mysql://110.40.251.64:3306/android?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
    //?useUnicode=true&characterEncodeing=UTF-8&useSSL=false&serverTimezone=GMT&allowPublicKeyRetrieval=true
    private static final String db_username="AndroidDev";
    private static final String db_password="zxcvbnm123";

    public static Connection connection;


    public void getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
//            Log.i("Test","JDBCConnection connecting");
            connection = DriverManager.getConnection(db_url,db_username,db_password);
        }
        catch (ClassNotFoundException e) {
//            Log.e("Test","Class missed");
            e.printStackTrace();
        }
        catch (SQLException e) {
//            Log.e("Test","JDBCConnection missed");
            e.printStackTrace();
        }
        finally {
//            Log.i("Test","JDBCConnection Test END");
        }
    }
    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        connection=null;
    }

}
