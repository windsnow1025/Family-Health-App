package com.project.JDBC;

import com.project.Pojo.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AlertDao extends JDBCHelper{
    public AlertDao(){
    }
    public ArrayList<Alert> getAlert_Remote(String account){
        ArrayList<Alert> alertArrayList=new ArrayList<>();
        String sql="SELECT alert_No,date,cycle,content,type,type_no from alert WHERE phone_number=?";
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
                alertArrayList.add(alert_temp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return alertArrayList;
    }
    public Boolean updateAlert_Remote(String account,Alert alert_update){
        Boolean valueReturn=false;
        String sql="UPDATE alert SET date=?,cycle=?,content=?,type=?,type_no=? WHERE alert_No=? and phone_number=?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,alert_update.getDate());
            preparedStatement.setString(2,alert_update.getCycle());
            preparedStatement.setString(3,alert_update.getContent());
            preparedStatement.setString(4,alert_update.getType());
            preparedStatement.setInt(5,alert_update.getType_No());
            preparedStatement.setInt(6,alert_update.getAlert_No());
            preparedStatement.setString(7,account);
            if(preparedStatement.executeUpdate()>0){
                valueReturn=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return valueReturn;
    }
    public Boolean deleteAlert_Remote(String account,Integer alert_No){
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
    public Boolean insertAlert_Remote(String account,Alert alert_insert){
        Boolean valueReturn=false;
        String sql="INSERT INTO alert (alert_No,phone_number,date,cycle,content,type,type_no) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(sql);
            preparedStatement.setInt(1,getAlertCount(account));
            preparedStatement.setString(2,account);
            preparedStatement.setString(3, alert_insert.getDate());
            preparedStatement.setString(4, alert_insert.getCycle());
            preparedStatement.setString(5, alert_insert.getContent());
            preparedStatement.setString(6,alert_insert.getType());
            preparedStatement.setInt(7,alert_insert.getType_No());
            if(preparedStatement.executeUpdate()>0)
            {
                valueReturn=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return valueReturn;
    }
    private Integer getAlertCount(String account){
        Integer count=0;
        String sql="SELECT alert_No from alert where phone_number=? ORDER BY alert_No DESC limit 1";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            ResultSet resultSet= preparedStatement.executeQuery();
            if(resultSet.next()){
                count=resultSet.getInt("alert_No");
            }
            else {
                count=0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (count+1);
    }
}
