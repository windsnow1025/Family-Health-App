package com.project;

import com.project.Exception.updateInfoException;
import com.project.JDBC.UserDao;
import com.project.Pojo.History;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class JDBC_Test {
    UserDao userDao =new UserDao();
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
        Boolean output;
        System.out.println("\ncheckUnique_test\n");
        output= userDao.checkUserUnique("18001718981");
        System.out.println("correct:"+output);
        output= userDao.checkUserUnique(null);
        System.out.println("null:"+output);
        output= userDao.checkUserUnique("1800171898");
        System.out.println("wrong:"+output);
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
}
