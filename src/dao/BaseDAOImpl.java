package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBUtils;
import exceptions.DBException;
import logging.Log;

/**
 * This is the base class for all DAO implementation classes. Common methods
 * like getting a database connection, and closing ResultSets are available in
 * this class.
 * 
 */
public class BaseDAOImpl {
	/**
	 * Utility method to close a ResultSet.
	 * 
	 * @param rs
	 *            - ResultSet to be closed
	 */
	protected void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				Log.error("Error when closing ResultSet", e);
				throw new DBException("Error when closing ResultSet", e);
			}
		}
	}

	/**
	 * Utility method to get a Connection to the database.
	 * 
	 * @return - Connection to the database.
	 * 
	 * @throws SQLException
	 */
	protected Connection getConnection() throws SQLException {
		return DBUtils.getConnection();
	}

	protected void handleException(Exception e) {
		Log.error(e);
		throw new DBException(e);
	}

	protected void executeStatement(PreparedStatement stmt) throws SQLException {
		Log.debug("Executing query = " + stmt);
		int rowUpdated = stmt.executeUpdate();
		Log.debug(rowUpdated + " row(s) updated");
	}
}
