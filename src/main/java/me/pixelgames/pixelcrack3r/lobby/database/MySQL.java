package me.pixelgames.pixelcrack3r.lobby.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MySQL {
	
	private String HOST;
	private String DATABASE;
	private String USER;
	private String PASSWORD;
	
	private Connection connection;
	
	private long currentTimeout;
	private long maxTimeout;
	
	public MySQL(String host, String database, String user, String password) {
		this.HOST = host;
		this.DATABASE = database;
		this.USER = user;
		this.PASSWORD = password;
	}
	
	public Thread connect() {
		Runnable action = new Runnable() {
			
			@Override
			public void run() {
				try {
					MySQL.this.connection = DriverManager.getConnection("jdbc:mysql://" + HOST + "/" + DATABASE + "?autoReconnect=true", USER, PASSWORD);
					MySQL.this.out("The connection to the MySQL database has been successfully created.");
					MySQL.this.maxTimeout = 140000;
				} catch (SQLException e) {
					MySQL.this.out("The connection to the MySQL database is failed.\n" + e.getLocalizedMessage() + "( " + e.getErrorCode() + " / " + e.getMessage() + " / " + e.getSQLState() + " )");
				}
			}
		};
		
		Thread t = new Thread(action);
		t.start();
		return t;
	}
	
	public void update(String sql) {
		try {
			if(!this.isConnected()) {
				Thread action = this.connect();
				while(action.isAlive());
			}
			
			if(this.connection == null) return;
			
			Statement st = this.connection.createStatement();
			st.executeUpdate(sql);
			st.close();
		} catch (SQLException e) {
			this.out(e.getSQLState());
			this.out(e.getMessage());
		}
	}
	
	public ResultSet query(String sql) {
		ResultSet rs = null;
		
		try {
			if(!this.isConnected()) {
				Thread action = this.connect();
				while(action.isAlive());
			}
			
			Statement st = this.connection.createStatement();
			rs = st.executeQuery(sql);
		} catch (SQLException e) {
			this.out(e.getSQLState());
			this.out(e.getMessage());
		}
		return rs;
		
	}
	
	public void close() {
		this.currentTimeout = 0;
		
		try {
			if(this.connection != null) {
				this.connection.close();
				this.out("The connection to the MySQL database has been successfully closed.");
			}
		} catch (SQLException e) {
			this.out("Stopping the connection to MySQL failed.\n" + e.getStackTrace());
		}
	}
	
	public boolean isConnected() {
		if(this.connection == null) {
			return false;
		}
		
		try {
			if(this.connection.isClosed()) {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
		
		if(this.currentTimeout > this.maxTimeout) {
			return false;
		}
		
		return true;
	}
	
	public void createTable(String name, String args) {
		this.update("CREATE TABLE IF NOT EXISTS " + name + args + ";");
	}
	
	public void out(String text) {
		final String DATE = "[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + " INFO]";
		
		System.out.println(DATE + " [PixelGames] " + "[MySQL] " + text);
	}
	
	
	public static MySQL createSession(String host, String databse, String user, String password) {
		MySQL mySQL = new MySQL(host, databse, user, password);
		mySQL.connect();
		return mySQL;
	}
	
}
