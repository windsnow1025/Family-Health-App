package com.project.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCHelper {
    private static final String db_url="jdbc:mysql://110.40.251.64:3306/android";
    private static final String db_username="AndroidDev";
    private static final String db_password="zxcvbnm123";
    static Connection connection=null;
    static {
        try {
            connection= DriverManager.getConnection(db_url,db_username,db_password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
