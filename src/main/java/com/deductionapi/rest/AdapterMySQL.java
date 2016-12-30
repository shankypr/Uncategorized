package com.deductionapi.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
 
public class AdapterMySQL {
 
    enum TestTableColumns{
        id,TEXT;
    }
 
    private final String jdbcDriverStr;
    private final String jdbcURL;
 
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
 
    public AdapterMySQL(String jdbcDriverStr, String jdbcURL){
        this.jdbcDriverStr = jdbcDriverStr;
        this.jdbcURL = jdbcURL;
    }
 
    public String readData() throws Exception {
        try {
            Class.forName(jdbcDriverStr);
            connection = DriverManager.getConnection(jdbcURL);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from deductions_schema.deduction_item;");
            String resultStr = getResultSet(resultSet);
            //preparedStatement = connection.prepareStatement("insert into javaTestDB.test_table values (default,?)");
            //preparedStatement.setString(1,"insert test from java");
            //preparedStatement.executeUpdate();
            return resultStr;
        }finally{
            close();
        }
    }
    
    public void writeData() throws Exception {
    	
    }
 
    private String getResultSet(ResultSet resultSet) throws Exception {
    	StringBuilder sb = new StringBuilder();
        while(resultSet.next()){
            Integer id = resultSet.getInt("item_id");
            String name = resultSet.getString("name");
            String desc = resultSet.getString("description");
            float estimatedVal = resultSet.getFloat("estimated_value");
            float deductionAmt = resultSet.getFloat("deduction_amt");
            int year = resultSet.getInt("year");
            sb.append(id+" "+name+" "+desc+" "+estimatedVal+" "+deductionAmt+year);
            sb.append("\n");
        }
        return sb.toString();
    }
 
    private void close(){
        try {
            if(resultSet!=null) resultSet.close();
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        } catch(Exception e){}
    }
}