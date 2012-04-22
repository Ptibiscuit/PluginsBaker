package com.ptibiscuit.framework.mysql;

import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHandler {

	private mysqlCore core;
	private Connection connection;
	private String dblocation;
	private String username;
	private String password;
	private String database;

	public DatabaseHandler(mysqlCore core, String dbLocation, String database, String username, String password) {
		this.core = core;
		this.dblocation = dbLocation;
		this.database = database;
		this.username = username;
		this.password = password;
	}

	public void openConnection() throws MalformedURLException, InstantiationException, IllegalAccessException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = DriverManager.getConnection("jdbc:mysql://" + this.dblocation + "/" + this.database + "?autoReconnect=true", this.username, this.password);
		} catch (ClassNotFoundException e) {
			this.core.writeError("ClassNotFoundException! " + e.getMessage(), Boolean.valueOf(true));
		} catch (SQLException e) {
			this.core.writeError("SQLException! " + e.getMessage(), Boolean.valueOf(true));
		}
	}

	public Boolean checkConnection() {
		if (this.connection == null) {
			try {
				openConnection();
				return Boolean.valueOf(true);
			} catch (MalformedURLException ex) {
				this.core.writeError("MalformedURLException! " + ex.getMessage(), Boolean.valueOf(true));
			} catch (InstantiationException ex) {
				this.core.writeError("InstantiationExceptioon! " + ex.getMessage(), Boolean.valueOf(true));
			} catch (IllegalAccessException ex) {
				this.core.writeError("IllegalAccessException! " + ex.getMessage(), Boolean.valueOf(true));
			}
			return Boolean.valueOf(false);
		}
		return Boolean.valueOf(true);
	}

	public void closeConnection() {
		try {
			if (this.connection != null) {
				this.connection.close();
			}
		} catch (Exception e) {
			this.core.writeError("Failed to close database connection! " + e.getMessage(), Boolean.valueOf(true));
		}
	}

	public Connection getConnection() throws MalformedURLException, InstantiationException, IllegalAccessException {
		if (this.connection == null) {
			openConnection();
		}
		return this.connection;
	}

	public ResultSet sqlQuery(String query) throws MalformedURLException, InstantiationException, IllegalAccessException {
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();

			ResultSet result = statement.executeQuery(query);

			return result;
		} catch (SQLException ex) {
			this.core.writeError("Error at SQL Query: " + ex.getMessage(), Boolean.valueOf(false));
		}
		return null;
	}

	public void insertQuery(String query) throws MalformedURLException, InstantiationException, IllegalAccessException {
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();

			statement.executeUpdate(query);
		} catch (SQLException ex) {
			if (!ex.toString().contains("not return ResultSet")) {
				this.core.writeError("Error at SQL INSERT Query: " + ex, Boolean.valueOf(false));
			}
		}
	}

	public void updateQuery(String query)
			  throws MalformedURLException, InstantiationException, IllegalAccessException {
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();

			statement.executeUpdate(query);
		} catch (SQLException ex) {
			if (!ex.toString().contains("not return ResultSet")) {
				this.core.writeError("Error at SQL UPDATE Query: " + ex, Boolean.valueOf(false));
			}
		}
	}

	public void deleteQuery(String query) throws MalformedURLException, InstantiationException, IllegalAccessException {
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();

			statement.executeUpdate(query);
		} catch (SQLException ex) {
			if (!ex.toString().contains("not return ResultSet")) {
				this.core.writeError("Error at SQL DELETE Query: " + ex, Boolean.valueOf(false));
			}
		}
	}

	public Boolean checkTable(String table) throws MalformedURLException, InstantiationException, IllegalAccessException {
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();

			ResultSet result = statement.executeQuery("SELECT * FROM " + table);

			if (result == null) {
				return Boolean.valueOf(false);
			}
			if (result != null) {
				return Boolean.valueOf(true);
			}
		} catch (SQLException ex) {
			if (ex.getMessage().contains("exist")) {
				return Boolean.valueOf(false);
			}
			this.core.writeError("Error at SQL Query: " + ex.getMessage(), Boolean.valueOf(false));
		}

		if (sqlQuery("SELECT * FROM " + table) == null) {
			return Boolean.valueOf(true);
		}
		return Boolean.valueOf(false);
	}

	public Boolean wipeTable(String table) throws MalformedURLException, InstantiationException, IllegalAccessException {
		try {
			if (!this.core.checkTable(table).booleanValue()) {
				this.core.writeError("Error at Wipe Table: table, " + table + ", does not exist", Boolean.valueOf(true));
				return Boolean.valueOf(false);
			}
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			String query = "DELETE FROM " + table + ";";
			statement.executeUpdate(query);

			return Boolean.valueOf(true);
		} catch (SQLException ex) {
			if (!ex.toString().contains("not return ResultSet")) {
				this.core.writeError("Error at SQL WIPE TABLE Query: " + ex, Boolean.valueOf(false));
			}
		}
		return Boolean.valueOf(false);
	}

	public Boolean createTable(String query) {
		try {
			if (query == null) {
				this.core.writeError("SQL Create Table query empty.", Boolean.valueOf(true));
				return Boolean.valueOf(false);
			}
			Statement statement = this.connection.createStatement();
			statement.execute(query);
			return Boolean.valueOf(true);
		} catch (SQLException ex) {
			this.core.writeError(ex.getMessage(), Boolean.valueOf(true));
		}
		return Boolean.valueOf(false);
	}
}

/* Location:           C:\Users\ANNA\Documents\Frederic\netbeanspace\CommuBan\dist\DestiPlugins.jar
 * Qualified Name:     com.ptibiscuit.framework.mysql.DatabaseHandler
 * JD-Core Version:    0.6.0
 */