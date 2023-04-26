package com.project.JDBC;

import android.util.Log;

import com.project.Pojo.History;
import com.project.Pojo.Report;
import com.project.Sqlite.UserLocalDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReportDao extends JDBCHelper{
    public ReportDao(){
    }
    public ArrayList<Report> getReportList(String account,Integer...args) throws TimeoutException {
        ArrayList<Report> valueReturn=null;
        FutureTask<ArrayList<Report>> futureTask=new FutureTask<>(()->{
            //Thread.sleep(3000);                                                                   //模拟网络出了问题
            getConnection();
            ArrayList<Report> value=getReportListImpl(account);
            closeConnection();
            return value;
        });
        new Thread(futureTask).start();
        try {
            valueReturn=futureTask.get(20, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            futureTask.cancel(true);
        }
        if(args.length==0&&valueReturn!=null)
        {
            Stream<Report> reportStream=valueReturn.stream();
            valueReturn= (ArrayList<Report>) reportStream.filter(report -> report.getIs_deleted().equals("false")).collect(Collectors.toList());
        }
        return valueReturn;
    }
    private ArrayList<Report> getReportListImpl(String account) {
        ArrayList<Report> reportArrayList=new ArrayList<>();
        String sql="SELECT report_No,report_content,report_picture,report_type,report_place,report_date,is_deleted FROM report WHERE phone_number=? ORDER BY report_No";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            ResultSet resultSet= preparedStatement.executeQuery();
            while(resultSet.next()){
                Report report_temp=new Report();
                report_temp.setPhone_number(account);
                report_temp.setReport_No(resultSet.getInt("report_No"));
                report_temp.setReport_content(resultSet.getString("report_content"));
                report_temp.setReport_picture(resultSet.getString("report_picture"));
                report_temp.setReport_type(resultSet.getString("report_type"));
                report_temp.setReport_place(resultSet.getString("report_place"));
                report_temp.setReport_date(resultSet.getString("report_date"));
                report_temp.setIs_deleted(resultSet.getString("is_deleted"));
                reportArrayList.add(report_temp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reportArrayList;
    }
    public Boolean updateReport(String account,Report report_update) throws TimeoutException {
        Boolean valueReturn=null;
        FutureTask<Boolean> futureTask=new FutureTask<>(()->{
            getConnection();
            Boolean value=updateReportImpl(account,report_update);
            closeConnection();
//            UserLocalDao userLocalDao=new UserLocalDao();
//            userLocalDao.open();
//            userLocalDao.updateReport(account, report_update);
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
    private Boolean updateReportImpl(String account, Report report_update){
        Boolean valueReturn=false;
        String sql="UPDATE report SET report_content=?,report_picture=?,report_type=?,report_place=?,report_date=?,is_deleted=? where phone_number=? and report_No=?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,report_update.getReport_content());
            preparedStatement.setString(2,report_update.getReport_picture());
            preparedStatement.setString(3,report_update.getReport_type());
            preparedStatement.setString(4,report_update.getReport_place());
            preparedStatement.setString(5,report_update.getReport_date());
            preparedStatement.setString(6,report_update.getIs_deleted());
            preparedStatement.setString(7,account);
            preparedStatement.setInt(8,report_update.getReport_No());

            if(preparedStatement.executeUpdate()>0)
            {
                valueReturn=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return valueReturn;
    }
    public Boolean deleteReport(String account,Integer report_No) throws TimeoutException {
        Boolean valueReturn=null;
        FutureTask<Boolean> futureTask=new FutureTask<>(()->{
            getConnection();
            Boolean value=deleteReportImpl(account,report_No);
            closeConnection();
//            UserLocalDao userLocalDao=new UserLocalDao();
//            userLocalDao.open();
//            userLocalDao.deleteReport(account, report_No);
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
    private Boolean deleteReportImpl(String account, Integer report_No){
        Boolean valueReturn=false;
        String sql="UPDATE report SET is_deleted='true' where phone_number=? and report_No=?";
        if(report_No==null){
            report_No=0;
        }
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            preparedStatement.setInt(2,report_No);

            if(preparedStatement.executeUpdate()>0){
                valueReturn=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return valueReturn;
    }
    public Boolean insertReport(String account,Report report_insert) throws TimeoutException {
        Boolean valueReturn=null;
        FutureTask<Boolean> futureTask=new FutureTask<>(()->{
            getConnection();
            report_insert.setReport_No(reportCount(account));
            Boolean value=insertReportImpl(account,report_insert);
            closeConnection();
//            UserLocalDao userLocalDao=new UserLocalDao();
//            userLocalDao.open();
//            userLocalDao.insertReport(account, report_insert);
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
    private  Boolean insertReportImpl(String account, Report report_insert){
        Boolean valueReturn=false;
        String sql="INSERT INTO report (phone_number,report_content,report_picture,report_No,report_type,report_place,report_date,is_deleted) VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            preparedStatement.setString(2,report_insert.getReport_content());
            preparedStatement.setString(3,report_insert.getReport_picture());
            preparedStatement.setInt(4,report_insert.getReport_No());
            preparedStatement.setString(5,report_insert.getReport_type());
            preparedStatement.setString(6,report_insert.getReport_place());
            preparedStatement.setString(7,report_insert.getReport_date());
            preparedStatement.setString(8,"false");
            if(preparedStatement.executeUpdate()>0){
                valueReturn=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return valueReturn;
    }
    public void SyncReportUpload(String account,ArrayList<Report> reportArrayList){
        FutureTask<Boolean> futureTask=new FutureTask<>(()->{
            getConnection();
            SyncReportUploadImpl(account,reportArrayList);
            closeConnection();
            return null;
        });
        new Thread(futureTask).start();
    }
    private void SyncReportUploadImpl(String account,ArrayList<Report> reportArrayList){
        Stream<Report> alertStream=reportArrayList.stream();
        alertStream.forEach(report -> {
            if(is_Exist(account,report.getReport_No()))
            {
                updateReportImpl(account,report);
            }
            else
            {
                insertReportImpl(account,report);
            }
        });
    }
    private Boolean is_Exist(String account,Integer report_No) {
        Boolean valueReturn=false;
        String sql="SELECT phone_number FROM report WHERE phone_number=? AND report_No=?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            preparedStatement.setInt(2,report_No);
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
    private Integer reportCount(String account){
        Integer valueReturn=0;
        String sql="SELECT COUNT(report_No)as count FROM report WHERE phone_number=?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
            {
                valueReturn=resultSet.getInt("count")+1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return valueReturn;
    }
}