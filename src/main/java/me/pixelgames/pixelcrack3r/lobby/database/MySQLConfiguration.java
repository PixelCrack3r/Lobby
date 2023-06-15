package me.pixelgames.pixelcrack3r.lobby.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLConfiguration {

	private MySQL provider;
	
	private String database;
	private String uuid;
	
	public MySQLConfiguration(MySQL provider, String database) {
		this.provider = provider;
		this.database = database;
	}
	
	public MySQLConfiguration(MySQL provider, String database, String uuid) {
		this.provider = provider;
		this.database = database;
		this.uuid = uuid;
	}

	public String get(String key) {
		try {
			ResultSet rs = this.getProvider().query("SELECT VALUE FROM " + this.database + " WHERE `KEY`='" + key + "' AND `UUID`='" + this.uuid + "';");
			if(rs == null || !rs.next()) {
				return null;
			}
			return rs.getString("VALUE");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean getBoolean(String key) {
		String result = get(key);
		if(result == null) return false;
		return get(key).equalsIgnoreCase("true");
	}
	
	public String getString(String key, String value, String element) {
		String re = null;
		
		try {
			ResultSet rs = this.getProvider().query("SELECT * FROM " + this.database + " WHERE " + key + "='" + value + "';");
			if(rs == null) {
				this.provider.connect();
				rs = this.getProvider().query("SELECT * FROM " + this.database + " WHERE " + key + "='" + value + "';");
			}
			
			if(rs == null || !rs.next() || rs.getString(element) == null) {
				return null;
			}
			
			re = rs.getString(element);
			
		} catch (SQLException e) {
			this.getProvider().out(e.getSQLState() + ": " + e.getMessage());
			return null;
		}
	
		return re;
	}
	
	public int getInt(String key, String value, String element) {
		int re = 0;
		
		try {
			ResultSet rs = this.getProvider().query("SELECT * FROM " + this.database + " WHERE " + key + "='" + value + "';");
			if((!rs.next()) || (Integer.valueOf(rs.getInt(element)) == null)) {
				return 0;
			}
			
			re = rs.getInt(element);
		} catch (SQLException e) {
			this.getProvider().out(e.getSQLState() + ": " + e.getMessage());
			return 0;
		}
		
		return re;
	}
	
	public void setString(String key, String value, String element, String elementValue) {
		if(getString(key, value, element) != null) {
			this.getProvider().update("UPDATE " + this.database + " SET " + element + "='" + elementValue + "' WHERE " + key + "='" + value + "';");
		} else {
			this.getProvider().update("INSERT INTO " + this.database + "(" + key + "," + element + ") VALUES ('" + value + "'" + "," +  "'" + elementValue + "');");
		}
	}
	
	public void set(String key, String value, String element, Object elementValue) {
		if(getString(key, value, element) != null) {
			this.getProvider().update("UPDATE " + this.database + " SET " + element + "='" + elementValue + "' WHERE " + key + "='" + value + "';");
		} else {
			this.getProvider().update("INSERT INTO " + this.database + "(" + key + "," + element + ") VALUES ('" + value + "'" + "," +  "'" + elementValue + "');");
		}
	}
	
	public void set(String key, String value) {
		String r = get(key);
		if(r == null) {
			this.getProvider().update("INSERT INTO " + this.database + "(`UUID`, `KEY`, `VALUE`) VALUES ('" + this.uuid + "', '" + key + "', '" + value + "');");
		} else {
			this.getProvider().update("UPDATE " + this.database + " SET `VALUE`='" + value + "' WHERE `UUID`='" + this.uuid + "' AND `KEY`='" + key + "';");
		}
	}
	
	public  void createConfigTable(String name) {
		this.getProvider().update("CREATE TABLE IF NOT EXISTS " + name + "(`UUID` varchar(256) UNIQUE, `KEY` varchar(256) UNIQUE, `VALUE` varchar(512));");
	}
	
	public MySQL getProvider() {
		return this.provider;
	}
	
}
