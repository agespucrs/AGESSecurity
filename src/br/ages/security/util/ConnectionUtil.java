package br.ages.security.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ConnectionUtil {

	private static ResourceBundle configDB = ResourceBundle.getBundle(Configuration.ENVIRONMENT_PROPERTIES);

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(configDB.getString(Configuration.CONNECTION_BD_DRIVE));

		return DriverManager.getConnection(configDB.getString(Configuration.CONNECTION_BD_URL), 
										   configDB.getString(Configuration.CONNECTION_BD_USER),
										   configDB.getString(Configuration.CONNECTION_BD_PASSWORD));
	}
}
