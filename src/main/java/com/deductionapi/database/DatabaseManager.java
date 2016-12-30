package com.deductionapi.database;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;




import java.sql.Connection;

import com.deductionapi.model.DeductionItem;
import com.deductionapi.model.DeductionItemList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DatabaseManager {
	
	private String url;
	private String username;
	private String password;
	private Connection connection;
	
	public DatabaseManager(String url,String userName,String password) throws Exception{
		this.url=url;
		this.username=userName;
		this.password=password;
		connect();
	}
	
	public void saveDeductionItems(List<DeductionItem> items) throws SQLException{
		String sql = "INSERT INTO deduction_item (item_id, name, description,estimated_value,year)" +
		        "VALUES (?, ?, ?, ?,?)";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		
		for(DeductionItem item:items) {
			preparedStatement.setString(1, ""+item.getItemId());
			preparedStatement.setString(2, item.getItemName());
			preparedStatement.setString(3, item.getItemDescription());
			preparedStatement.setString(4, ""+item.getEstimatedValue());
			preparedStatement.setString(5, ""+item.getYear());
			preparedStatement.executeUpdate(); 
		}
	}
	
	public void removeAllItems() throws SQLException {
		Statement st;
		//ResultSet rs=null;
		try {
			st = connection.createStatement();
			st.executeUpdate("TRUNCATE table deduction_item");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	
	public List<DeductionItem> getDeductionItems(String sql) {
		if(sql==null || sql.isEmpty()) {
			Logger.getGlobal().log(Level.SEVERE,"Sql string is either null or blank");
			return null;
		}
		Statement st;
		ResultSet rs;
		try {
			st = connection.createStatement();
			rs=st.executeQuery(sql);
			List<DeductionItem> items = getItemsFromResult(rs);
			return items;
			//String response = this.getJsonFromDeductionItems(items);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private void connect() throws Exception{
		try {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			ex.printStackTrace();
			throw ex;
		}
	}
	
	

	private List<DeductionItem> getItemsFromResult(ResultSet resultSet) throws SQLException {
		List<DeductionItem> items= new ArrayList<DeductionItem>();
        while (resultSet.next()) {
            
            int id = resultSet.getInt("item_id");
            String name = resultSet.getString("name");
            String desc = resultSet.getString("description");
            float est_val = resultSet.getFloat("estimated_value");
            int year = resultSet.getInt("year");
            
            //DeductionItem item = new DeductionItem(id,name,desc,est_val,amt);
            DeductionItem item = new DeductionItem();
            item.setItemId(id);
            item.setItemDescription(desc);
            item.setItemName(name);
            item.setEstimatedValue(est_val);
            item.setYear(year);
            items.add(item);	
       }
       return items;
	}
	

	
}
