package com.project;

import com.project.Exception.updateInfoException;
import com.project.JDBC.AlertDao;
import com.project.JDBC.HistoryDao;
import com.project.JDBC.ReportDao;
import com.project.JDBC.UserDao;
import com.project.Pojo.Alert;
import com.project.Pojo.History;
import com.project.Pojo.Report;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class JDBC_Test {
    UserDao userDao =new UserDao();
    HistoryDao historyDao=new HistoryDao();
    ReportDao reportDao=new ReportDao();
    AlertDao alertDao=new AlertDao();
    @Test
    public void test(){
        System.out.println("JDBC_Test\n");
    }
    @Test
    public void login_test() {
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
    }
    @Test
    public void checkUnique_test() {
        userDao.getConnection();
        Boolean output;
        System.out.println("\ncheckUnique_test\n");
        output= userDao.checkUserUnique("18001718981");
        System.out.println("correct:"+output);
        output= userDao.checkUserUnique(null);
        System.out.println("null:"+output);
        output= userDao.checkUserUnique("1800171898");
        System.out.println("wrong:"+output);
        userDao.closeConnection();
    }
    @Test
    public void insertUser_test() {
        System.out.println("\ninsertUser_test\n");
        String output;
        output= userDao.insertUser("4","2","male","2022-07-08");
        System.out.println("correct or repeat:"+output);
//        output= accountDao.insertUser("2","2","male","2022-07");
//        System.out.println("wrong birthday:"+output);
//        错误的生日 会异常
        output= userDao.insertUser(null,"2","male","2022-07-08");
        System.out.println("null account:"+output);
        output= userDao.insertUser("2","","male","2022-07-08");
        System.out.println("empty password:"+output);
        output= userDao.insertUser("3",null,"male","2022-07-08");
        System.out.println("null password:"+output);
    }
    @Test
    public void getUserInfo_Test(){
        System.out.println("\ngetUserInfo_Test\n");
        HashMap userInfo=new HashMap<>();
        userInfo= userDao.getUserInformation("18001718981");
        System.out.println("correct");
        System.out.println("username="+userInfo.get("username"));
        System.out.println("email="+userInfo.get("email"));
        System.out.println("sex="+userInfo.get("sex"));
        System.out.println("birthday="+userInfo.get("birthday"));
        userInfo= userDao.getUserInformation("1800171898");
        System.out.println("wrong account");
        System.out.println("username="+userInfo.get("username"));
        System.out.println("email="+userInfo.get("email"));
        System.out.println("sex="+userInfo.get("sex"));
        System.out.println("birthday="+userInfo.get("birthday"));
        userInfo= userDao.getUserInformation(null);
        System.out.println("null account");
        System.out.println("username="+userInfo.get("username"));
        System.out.println("email="+userInfo.get("email"));
        System.out.println("sex="+userInfo.get("sex"));
        System.out.println("birthday="+userInfo.get("birthday"));
    }
    @Test
    public void updateUserInfo_Test(){
        HashMap update_test=new HashMap<>();
        update_test.put("username","test");
        try {
            userDao.updateUserInformation("18001718981",update_test);
        } catch (updateInfoException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void historyDao_Test(){
        historyDao.getConnection();
        ArrayList<History> historyArrayList=historyDao.getHistory_Remote("1111");
        for (History history:historyArrayList) {
            System.out.println(history.getHistory_No()+history.getHistory_date()+history.getHistory_organ()+history.getSuggestion());
        }
        historyDao.deleteHistory_Remote("1111",7);
        History history=new History();
        history.setHistory_organ("test");
        history.setHistory_date("2022-07-08");
        history.setHistory_doctor("test");
        history.setConclusion("tttt");
        history.setSymptom("ddddd");
        history.setSuggestion("stttt");
        history.setHistory_place("ssssdd");
        historyDao.insertHistory_Remote("1112",history);
        history.setHistory_No(1);
        historyDao.updateHistory_Remote("1111",history);
        historyDao.closeConnection();
    }
    @Test
    public void reportDao_Test(){
        reportDao.getConnection();
        ArrayList<Report> reportArrayList=reportDao.getReport_Remote("1111");
        for(Report report:reportArrayList){
            System.out.println(report.getReport_No()+report.getReport_content());
        }
        reportDao.deleteReport_Remote("1111",3);
        Report report=new Report();
        report.setReport_date("2022-07-08");
        report.setReport_place("test");
        report.setReport_type("teee");
        report.setReport_content("健康");
        reportDao.insertReport_Remote("1112",report);
        report.setReport_No(1);
        reportDao.updateReport_Remote("1111",report);
    }
    @Test
    public void alertDao_Test(){
        alertDao.getConnection();
        ArrayList<Alert> alertArrayList=alertDao.getAlert_Remote("1111");
        for(Alert alert:alertArrayList){
            System.out.println(alert.getAlert_No()+alert.getCycle()+alert.getContent());
        }
        alertDao.deleteAlert_Remote("1112",4);
        Alert alert=new Alert();
        alert.setPhone_number("1111");
        alert.setType("test");
        alert.setType_No((int) 1e4);
        alert.setCycle("eeeee");
        alert.setContent("22222");
        alert.setDate("2033-08-09");
        alertDao.insertAlert_Remote("1111",alert);
        alert.setAlert_No(1);
        alertDao.updateAlert_Remote("1111",alert);
        alertDao.closeConnection();
    }
}
