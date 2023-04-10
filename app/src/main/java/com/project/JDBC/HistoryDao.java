package com.project.JDBC;


import com.project.Pojo.Alert;
import com.project.Pojo.History;

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

public class HistoryDao extends JDBCHelper{
    public HistoryDao() {
    }
    public ArrayList<History> getHistoryList(String account) throws TimeoutException {
        ArrayList<History> valueReturn=new ArrayList<>();
        FutureTask<ArrayList<History>> futureTask=new FutureTask<>(()->{
            getConnection();
            ArrayList<History> value=getHistoryListImpl(account);
            closeConnection();
            return value;
        });
        new Thread(futureTask).start();
        try {
            valueReturn=futureTask.get(2, TimeUnit.MILLISECONDS);
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
    public ArrayList<History> getHistoryListImpl(String account) {
        ArrayList<History> historyArrayList=new ArrayList<>();
        String sql="SELECT history_No,history_date,history_place,history_doctor,history_organ,conclusion,symptom,suggestion,is_deleted FROM history WHERE phone_number = ? ORDER BY history_organ,history_No";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            ResultSet resultSet= preparedStatement.executeQuery();
            while(resultSet.next()){
                History history_temp=new History();
                history_temp.setPhone_number(account);
                history_temp.setHistory_No(resultSet.getInt("history_No"));
                history_temp.setHistory_date(resultSet.getString("history_date"));
                history_temp.setHistory_place(resultSet.getString("history_place"));
                history_temp.setHistory_doctor(resultSet.getString("history_doctor"));
                history_temp.setHistory_organ(resultSet.getString("history_organ"));
                history_temp.setConclusion(resultSet.getString("conclusion"));
                history_temp.setSymptom(resultSet.getString("symptom"));
                history_temp.setSuggestion(resultSet.getString("suggestion"));
                history_temp.setIs_deleted(resultSet.getString("is_deleted"));
                historyArrayList.add(history_temp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return historyArrayList;
    }
    public Boolean updateHistory(String account, History history_update) throws TimeoutException {
        Boolean valueReturn=false;
        FutureTask<Boolean> futureTask=new FutureTask<>(()->{
            getConnection();
            Boolean value=updateHistoryImpl(account,history_update);
            closeConnection();
            return value;
        });
        new Thread(futureTask).start();
        try {
            valueReturn=futureTask.get(2, TimeUnit.MILLISECONDS);
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
    public Boolean updateHistoryImpl(String account, History history_update){
        Boolean valueReturn=false;
        String sql="UPDATE history SET history_date=?,history_place=?,history_doctor=?,conclusion=?,symptom=?,suggestion=?,is_deleted=? where phone_number=? and history_No=?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1, history_update.getHistory_date());
            preparedStatement.setString(2, history_update.getHistory_place());
            preparedStatement.setString(3, history_update.getHistory_doctor());
            preparedStatement.setString(4, history_update.getConclusion());
            preparedStatement.setString(5, history_update.getSymptom());
            preparedStatement.setString(6, history_update.getSuggestion());
            preparedStatement.setString(7, history_update.getIs_deleted());
            preparedStatement.setString(8, account);
            preparedStatement.setInt(9, history_update.getHistory_No());
            if(preparedStatement.executeUpdate()>0)
            {
                valueReturn=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return valueReturn;
    }
    public Boolean deleteHistory(String account, Integer history_No) throws TimeoutException {
        Boolean valueReturn=false;
        FutureTask<Boolean> futureTask=new FutureTask<>(()->{
            getConnection();
            Boolean value=deleteHistoryImpl(account,history_No);
            closeConnection();
            return value;
        });
        new Thread(futureTask).start();
        try {
            valueReturn=futureTask.get(2, TimeUnit.MILLISECONDS);
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
    public Boolean deleteHistoryImpl(String account, Integer history_No){
        Boolean valueReturn=false;
        String sql="UPDATE history SET is_deleted='true' where phone_number=? and history_No=?";
        if(history_No==null){
            history_No=0;
        }
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            preparedStatement.setInt(2,history_No);
            if(preparedStatement.executeUpdate()>0){
                valueReturn=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return valueReturn;
    }
    public Boolean insertHistory(String account, History history_insert) throws TimeoutException {
        Boolean valueReturn=false;
        FutureTask<Boolean> futureTask=new FutureTask<>(()->{
            getConnection();
            history_insert.setHistory_No(historyCount(account));
            Boolean value=insertHistoryImpl(account,history_insert);
            closeConnection();
            return value;
        });
        new Thread(futureTask).start();
        try {
            valueReturn=futureTask.get(2, TimeUnit.MILLISECONDS);
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
    public  Boolean insertHistoryImpl(String account, History history_insert){
        Boolean valueReturn=false;
        String sql="INSERT INTO history (phone_number,history_place,history_date,history_doctor,history_organ,symptom,conclusion,suggestion,history_No,is_deleted) VALUES (?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            preparedStatement.setString(2, history_insert.getHistory_place());
            preparedStatement.setString(3,history_insert.getHistory_date());
            preparedStatement.setString(4,history_insert.getHistory_doctor());
            preparedStatement.setString(5,history_insert.getHistory_organ());
            preparedStatement.setString(6,history_insert.getSymptom());
            preparedStatement.setString(7,history_insert.getConclusion());
            preparedStatement.setString(8,history_insert.getSuggestion());
            preparedStatement.setInt(9,history_insert.getHistory_No());
            preparedStatement.setString(10,"false");
            if(preparedStatement.executeUpdate()>0){
                valueReturn=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return valueReturn;
    }
    public void SyncHistoryUpload(String account,ArrayList<History> historyArrayList) throws TimeoutException {
        FutureTask<Boolean> futureTask=new FutureTask<>(()->{
            getConnection();
            SyncHistoryUploadImpl(account,historyArrayList);
            closeConnection();
            return null;
        });
        new Thread(futureTask).start();
        try {
            futureTask.get(2, TimeUnit.MILLISECONDS);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void SyncHistoryUploadImpl(String account,ArrayList<History> historyArrayList) {
        Stream<History> alertStream=historyArrayList.stream();
        alertStream.forEach(history -> {
            if(is_Exist(account,history.getHistory_No()))
            {
                updateHistoryImpl(account,history);
            }
            else
            {
                insertHistoryImpl(account,history);
            }
        });
    }
    private Boolean is_Exist(String account,Integer history_No) {
        Boolean valueReturn=false;
        String sql="SELECT phone_number FROM history WHERE phone_number=? AND history_No=?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            preparedStatement.setInt(2,history_No);
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
    private Integer historyCount(String account){
        Integer valueReturn=0;
        String sql="SELECT COUNT(history_No)as count FROM history WHERE phone_number=?";
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
