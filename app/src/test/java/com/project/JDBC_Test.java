package com.project;

import com.project.JDBC.ReportDao;
import com.project.JDBC.UserDao;
import com.project.Pojo.Report;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class JDBC_Test {
    UserDao userDao;
    @Before
    public void begin(){
        System.out.println("Test Start");
    }
    @After
    public void end(){
        System.out.println("Test End");
    }
    @Test
    //驱动测试
    public void login_test() {
        userDao=new UserDao();
        userDao.getConnection();
        System.out.println("\nlogin_test\n");
        String output=null;
        output= userDao.checkUserPassword(null,"1");
        System.out.println("null account:"+output);
        //空账号
        output= userDao.checkUserPassword("1800171898","1");
        System.out.println("wrong account:"+output);
        //错误账户
        output= userDao.checkUserPassword("18001718981","1");
        System.out.println("wrong password:"+output);
        //错误密码
        output= userDao.checkUserPassword("18001718981","2");
        System.out.println("correct:"+output);
        //正确密码
        userDao.closeConnection();
    }
    @Test
    //流测试
    public void StreamTest(){
        ReportDao reportDao=new ReportDao();
        reportDao.getConnection();
        ArrayList<Report> reportArrayList=reportDao.getReportList("1111");
        Report report=ReportDao.gerReport(reportArrayList,1);
        System.out.println(report.getReport_type());
    }
}
