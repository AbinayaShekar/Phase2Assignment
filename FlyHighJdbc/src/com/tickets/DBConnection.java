package com.tickets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private Connection connection;
	
	public DBConnection(String dbURL, String user , String password) throws SQLException, ClassNotFoundException
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		this.connection=DriverManager.getConnection(dbURL , user , password);
	}
	
	public Connection getConnection()
	{
		return this.connection;
	}
	
	public void closeConnection() throws SQLException
	{
		if(this.connection!=null)
		{
			this.connection.close();
		}
	}
}
