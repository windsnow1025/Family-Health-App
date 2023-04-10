package com.project.JDBC;

import com.project.Pojo.Alert;

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

public class AlertDao extends JDBCHelper{
    public AlertDao(){
    }
    public ArrayList<Alert> getAlertList(String account) throws TimeoutException {
        ArrayList<Alert> valueReturn=new ArrayList<>();
        FutureTask<ArrayList<Alert>> futureTask=new FutureTask<>(()->{
            getConnection();
            ArrayList<Alert> alertArrayList=getAlertListImpl(account);
            closeConnection();
            return alertArrayList;
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
    private ArrayList<Alert> getAlertListImpl(String account){
        ArrayList<Alert> alertArrayList=new ArrayList<>();
        String sql="SELECT alert_No,date,cycle,content,type,type_no,is_deleted from alert WHERE phone_number=? AND is_deleted='false'";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                Alert alert_temp=new Alert();
                alert_temp.setAlert_No(resultSet.getInt("alert_No"));
                alert_temp.setDate(resultSet.getString("date"));
                alert_temp.setCycle(resultSet.getString("cycle"));
                alert_temp.setContent(resultSet.getString("content"));
                alert_temp.setType(resultSet.getString("type"));
                alert_temp.setType_No(resultSet.getInt("type_no"));
                alert_temp.setPhone_number(account);
                alert_temp.setIs_deleted(resultSet.getString("is_deleted"));
                alertArrayList.add(alert_temp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return alertArrayList;
    }

    public Boolean updateAlert(String account, Alert alert_update) throws TimeoutException {
        Boolean valueReturn=false;
        FutureTask<Boolean> futureTask=new FutureTask<>(()->{
            getConnection();
            Boolean value=updateAlertImpl(account,alert_update);
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
    private Boolean updateAlertImpl(String account, Alert alert_update){
        Boolean valueReturn=false;
        String sql="UPDATE alert SET date=?,cycle=?,content=?,type=?,type_no=?,is_deleted=? WHERE alert_No=? and phone_number=?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,alert_update.getDate());
            preparedStatement.setString(2,alert_update.getCycle());
            preparedStatement.setString(3,alert_update.getContent());
            preparedStatement.setString(4,alert_update.getType());
            preparedStatement.setInt(5,alert_update.getType_No());
            preparedStatement.setString(6,alert_update.getIs_deleted());
            preparedStatement.setInt(7,alert_update.getAlert_No());
            preparedStatement.setString(8,account);
            if(preparedStatement.executeUpdate()>0){
                valueReturn=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return valueReturn;
    }

    public Boolean deleteAlert(String account, Integer alert_No) throws TimeoutException {
        Boolean valueReturn=false;
        FutureTask<Boolean> futureTask=new FutureTask<>(()->{
            getConnection();
            Boolean value=deleteAlertImpl(account,alert_No);
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
    private Boolean deleteAlertImpl(String account, Integer alert_No){
        Boolean valueReturn=false;
        String sql="UPDATE alert SET is_deleted='true' WHERE alert_No=? and phone_number=?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            preparedStatement.setInt(2,alert_No);
            if(preparedStatement.executeUpdate()>0){
                valueReturn=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return valueReturn;
    }

    public Boolean insertAlert(String account, Alert alert_insert) throws TimeoutException {
        Boolean valueReturn=false;
        FutureTask<Boolean> futureTask=new FutureTask<>(()->{
            getConnection();
            alert_insert.setAlert_No(alertCount(account));
            Boolean value=insertAlertImpl(account,alert_insert);
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
    private Boolean insertAlertImpl(String account, Alert alert_insert){
        Boolean valueReturn=false;
        String sql="INSERT INTO alert (alert_No,phone_number,date,cycle,content,type,type_no,is_deleted) VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(sql);
            preparedStatement.setInt(1,alert_insert.getAlert_No());
            preparedStatement.setString(2,account);
            preparedStatement.setString(3, alert_insert.getDate());
            preparedStatement.setString(4, alert_insert.getCycle());
            preparedStatement.setString(5, alert_insert.getContent());
            preparedStatement.setString(6,alert_insert.getType());
            preparedStatement.setInt(7,alert_insert.getType_No());
            preparedStatement.setString(8,"false");
            if(preparedStatement.executeUpdate()>0)
            {
                valueReturn=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return valueReturn;
    }

    public void SyncAlertUpload(String account,ArrayList<Alert> alertArrayList){
        FutureTask<Boolean> futureTask=new FutureTask<>(()->{
            getConnection();
            SyncAlertUploadImpl(account,alertArrayList);
            closeConnection();
            return null;
        });
        new Thread(futureTask).start();
    }
    private void SyncAlertUploadImpl(String account,ArrayList<Alert> alertArrayList) {
        Stream<Alert> alertStream=alertArrayList.stream();
        alertStream.forEach(alert -> {
            if(is_Exist(account,alert.getAlert_No()))
            {
                updateAlertImpl(account,alert);
            }
            else
            {
                insertAlertImpl(account,alert);
            }
        });
    }
    private Boolean is_Exist(String account,Integer alert_No) {
        Boolean valueReturn=false;
        String sql="SELECT phone_number FROM alert WHERE phone_number=? AND alert_No=?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            preparedStatement.setInt(2,alert_No);
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
    private Integer alertCount(String account){
        Integer valueReturn=0;
        String sql="SELECT COUNT(alert_No)as count FROM alert WHERE phone_number=?";
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
