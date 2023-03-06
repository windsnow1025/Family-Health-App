package com.project.JDBC;

import com.project.Pojo.Report;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReportDao extends JDBCHelper{
    public ReportDao(){
    }
    public ArrayList<Report> getReportList(String account)
    {
        ArrayList<Report> reportArrayList=new ArrayList<>();
        String sql="SELECT report_No,report_content,report_type,report_place,report_date FROM report WHERE phone_number=? ORDER BY report_No";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            ResultSet resultSet= preparedStatement.executeQuery();
            while(resultSet.next()){
                Report report_temp=new Report();
                report_temp.setPhone_number(account);
                report_temp.setReport_No(resultSet.getInt("report_No"));
                report_temp.setReport_content(resultSet.getString("report_content"));
                report_temp.setReport_type(resultSet.getString("report_type"));
                report_temp.setReport_place(resultSet.getString("report_place"));
                report_temp.setReport_date(resultSet.getString("report_date"));
                reportArrayList.add(report_temp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reportArrayList;
    }
    public Boolean updateReport(String account, Report report_update){
        Boolean valueReturn=false;
        String sql="UPDATE report SET report_content=?,report_type=?,report_place=?,report_date=? where phone_number=? and report_No=?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,report_update.getReport_content());
            preparedStatement.setString(2,report_update.getReport_type());
            preparedStatement.setString(3,report_update.getReport_place());
            preparedStatement.setString(4,report_update.getReport_date());
            preparedStatement.setString(5,account);
            preparedStatement.setInt(6,report_update.getReport_No());

            if(preparedStatement.executeUpdate()>0)
            {
                valueReturn=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return valueReturn;
    }
    public Boolean deleteReport(String account, Integer report_No){
        Boolean valueReturn=false;
        String sql="DELETE FROM report where phone_number=? and report_No=?";
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

    public  Boolean insertReport(String account, Report report_insert){
        Boolean valueReturn=false;
        String sql="INSERT INTO report (phone_number,report_content,report_No,report_type,report_place,report_date) VALUES (?,?,?,?,?,?)";
        Integer nextNo=getReportCount(account)+1;
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            preparedStatement.setString(2,report_insert.getReport_content());
            preparedStatement.setInt(3,report_insert.getReport_No());
            preparedStatement.setString(4,report_insert.getReport_type());
            preparedStatement.setString(5,report_insert.getReport_place());
            preparedStatement.setString(6,report_insert.getReport_date());
            if(preparedStatement.executeUpdate()>0){
                valueReturn=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return valueReturn;
    }

    //计数 在插入记录时控制序号
    public Integer getReportCount(String account){
        Integer count = 0;
        String sql="SELECT report_No from report where phone_number=?  ORDER BY report_No DESC limit 1";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                count=resultSet.getInt("report_No");
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
    public static Report gerReport(ArrayList<Report> reportArrayList,Integer report_No){
        Report reportReturn=null;
        Stream<Report> reportStream=reportArrayList.stream();
        reportReturn=reportStream.filter(e->e.getReport_No()==report_No).collect(Collectors.toList()).get(0);
        return reportReturn;
    }
}
