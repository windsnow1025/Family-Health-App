package com.project.JDBC;

import com.project.Pojo.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AlertDao extends JDBCHelper{
    public AlertDao(){
    }
    public ArrayList<Alert> getAlertList(String account){
        ArrayList<Alert> alertArrayList=new ArrayList<>();
        String sql="SELECT alert_No,date,cycle,content,type,type_no,is_deleted from alert WHERE phone_number=?";
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
    public Boolean updateAlert(String account, Alert alert_update){
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
    public Boolean deleteAlert(String account, Integer alert_No){
        Boolean valueReturn=false;
        String sql="DELETE FROM alert where phone_number=? and alert_No=?";
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
    public Boolean insertAlert(String account, Alert alert_insert){
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
    public void SyncAlertUpload(String account,ArrayList<Alert> alertArrayList)
    {
        Stream<Alert> alertStream=alertArrayList.stream();
        alertStream.forEach(alert -> {
            if(is_Exist(account,alert.getAlert_No()))
            {
                updateAlert(account,alert);
            }
            else
            {
                insertAlert(account,alert);
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
}
