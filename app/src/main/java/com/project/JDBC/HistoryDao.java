package com.project.JDBC;


import com.project.Pojo.History;
import com.project.Pojo.Report;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HistoryDao extends JDBCHelper{
    public HistoryDao() {
    }
    public ArrayList<History> getHistoryList(String account)
    {
        ArrayList<History> historyArrayList=new ArrayList<>();
        String sql="SELECT history_No,history_date,history_place,history_doctor,history_organ,conclusion,symptom,suggestion FROM history WHERE phone_number = ? ORDER BY history_organ,history_No";
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
                historyArrayList.add(history_temp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return historyArrayList;
    }
    public Boolean updateHistory(String account, History history_update){
        Boolean valueReturn=false;
        String sql="UPDATE history SET history_date=?,history_place=?,history_doctor=?,conclusion=?,symptom=?,suggestion=? where phone_number=? and history_No=?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1, history_update.getHistory_date());
            preparedStatement.setString(2, history_update.getHistory_place());
            preparedStatement.setString(3, history_update.getHistory_doctor());
            preparedStatement.setString(4, history_update.getConclusion());
            preparedStatement.setString(5, history_update.getSymptom());
            preparedStatement.setString(6, history_update.getSuggestion());
            preparedStatement.setString(7, account);
            preparedStatement.setInt(8, history_update.getHistory_No());
            if(preparedStatement.executeUpdate()>0)
            {
                valueReturn=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return valueReturn;
    }
    public Boolean deleteHistory(String account, Integer history_No){
        Boolean valueReturn=false;
        String sql="DELETE FROM history where phone_number=? and history_No=?";
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

    public  Boolean insertHistory(String account, History history_insert){
        Boolean valueReturn=false;
        String sql="INSERT INTO history (phone_number,history_place,history_date,history_doctor,history_organ,symptom,conclusion,suggestion,history_No) VALUES (?,?,?,?,?,?,?,?,?)";
        Integer nextNo=getHistoryCount(account)+1;
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
            if(preparedStatement.executeUpdate()>0){
                valueReturn=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return valueReturn;
    }

    //计数 在插入记录时控制序号
    public Integer getHistoryCount(String account){
        Integer count = 0;
        String sql="SELECT history_No from history where phone_number=? ORDER BY history_No DESC limit 1";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                count=resultSet.getInt("history_No");
            }
            else
            {
                count=0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (count+1);
    }
    public static History getHistory(ArrayList<History> historyArrayList,Integer history_No){
        History historyReturn=null;
        Stream<History> historyStream=historyArrayList.stream();
        historyReturn=historyStream.filter(e->e.getHistory_No()==history_No).collect(Collectors.toList()).get(0);
        return historyReturn;
    }
}
