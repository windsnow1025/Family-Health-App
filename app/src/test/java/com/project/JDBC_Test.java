package com.project;

import com.project.Exception.updateInfoException;
import com.project.JDBC.AccountDao;
import com.project.Pojo.History;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class JDBC_Test {
    AccountDao accountDao=new AccountDao();
    @Test
    public void test(){
        System.out.println("JDBC_Test\n");
    }
    @Test
    public void login_test() {
        System.out.println("\nlogin_test\n");
        String output=null;
        output= accountDao.checkUserPassword(null,"1");
        System.out.println("null account:"+output);
        //空账号
        output= accountDao.checkUserPassword("1800171898","1");
        System.out.println("wrong account:"+output);
        //错误账户
        output= accountDao.checkUserPassword("18001718981","1");
        System.out.println("wrong password:"+output);
        //错误密码
        output= accountDao.checkUserPassword("18001718981","2");
        System.out.println("correct:"+output);
        //正确密码
    }
    @Test
    public void checkUnique_test() {
        Boolean output;
        System.out.println("\ncheckUnique_test\n");
        output=accountDao.checkUserUnique("18001718981");
        System.out.println("correct:"+output);
        output=accountDao.checkUserUnique(null);
        System.out.println("null:"+output);
        output=accountDao.checkUserUnique("1800171898");
        System.out.println("wrong:"+output);
    }
    @Test
    public void insertUser_test() {
        System.out.println("\ninsertUser_test\n");
        String output;
        output= accountDao.insertUser("1","2","male","2022-07-08");
        System.out.println("correct or repeat:"+output);
//        output= accountDao.insertUser("2","2","male","2022-07");
//        System.out.println("wrong birthday:"+output);
//        错误的生日 会异常
        output= accountDao.insertUser(null,"2","male","2022-07-08");
        System.out.println("null account:"+output);
        output= accountDao.insertUser("2","","male","2022-07-08");
        System.out.println("empty password:"+output);
        output= accountDao.insertUser("3",null,"male","2022-07-08");
        System.out.println("null password:"+output);
    }
    @Test
    public void getUserInfo_Test(){
        System.out.println("\ngetUserInfo_Test\n");
        HashMap userInfo=new HashMap<>();
        userInfo=accountDao.getUserInformation("18001718981");
        System.out.println("correct");
        System.out.println("username="+userInfo.get("username"));
        System.out.println("email="+userInfo.get("email"));
        System.out.println("sex="+userInfo.get("sex"));
        System.out.println("birthday="+userInfo.get("birthday"));
        userInfo=accountDao.getUserInformation("1800171898");
        System.out.println("wrong account");
        System.out.println("username="+userInfo.get("username"));
        System.out.println("email="+userInfo.get("email"));
        System.out.println("sex="+userInfo.get("sex"));
        System.out.println("birthday="+userInfo.get("birthday"));
        userInfo=accountDao.getUserInformation(null);
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
            accountDao.updateUserInformation("18001718981",update_test);
        } catch (updateInfoException e) {
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void getHistory_Test(){
        ArrayList<History> historyArrayList=accountDao.getHistory_Remote("1111");
        System.out.println(historyArrayList.get(0).getContent()+"\t"+historyArrayList.get(1).getContent());
    }
    @Test
    public void updateHistory_Test(){
        History history=new History();
        history.setContent("test");
        history.setNo(2);
        history.setRemind("false");
        accountDao.updateHistory_Remote("1111",history);
        getHistory_Test();
    }
    @Test
    public void deleteHistory_Test(){
        System.out.println(accountDao.deleteHistroy_Remote("1111",3));
        System.out.println(accountDao.deleteHistroy_Remote("1111",4));
        System.out.println(accountDao.deleteHistroy_Remote("1111",null));
        System.out.println(accountDao.deleteHistroy_Remote(null,4));
        System.out.println(accountDao.deleteHistroy_Remote("",4));
    }
    @Test
    public void insertHistory_Test()
    {
        History history=new History();
        history.setRemind("false");
        history.setContent("testtttttt");
        System.out.println(accountDao.insertHistory_Remote("1111",history));
    }
    @Test
    public void Report_Test(){
        ArrayList<History> historyArrayList=new ArrayList<>();
        History history=new History();
        history.setNo(2);
        history.setContent("1134");
        historyArrayList=accountDao.getReport_Remote("1111");
        for(int i=0;i<historyArrayList.size();i++)
        {
            System.out.println(historyArrayList.get(i).getID()+"\t"+historyArrayList.get(i).getNo()+"\t"+historyArrayList.get(i).getContent());
        }
        accountDao.updateReport_Remote("1111",history);
        System.out.println();
        historyArrayList=accountDao.getReport_Remote("1111");
        for(int i=0;i<historyArrayList.size();i++)
        {
            System.out.println(historyArrayList.get(i).getID()+"\t"+historyArrayList.get(i).getNo()+"\t"+historyArrayList.get(i).getContent());
        }
        accountDao.insertReport_Remote("1111",history);
        System.out.println();
        historyArrayList=accountDao.getReport_Remote("1111");
        for(int i=0;i<historyArrayList.size();i++)
        {
            System.out.println(historyArrayList.get(i).getID()+"\t"+historyArrayList.get(i).getNo()+"\t"+historyArrayList.get(i).getContent());
        }
        accountDao.deleteReport_Remote("1111",historyArrayList.size());
        System.out.println();
        historyArrayList=accountDao.getReport_Remote("1111");
        for(int i=0;i<historyArrayList.size();i++)
        {
            System.out.println(historyArrayList.get(i).getID()+"\t"+historyArrayList.get(i).getNo()+"\t"+historyArrayList.get(i).getContent());
        }
    }
}
