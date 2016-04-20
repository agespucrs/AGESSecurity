package br.ages.security.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(Configuration.CONNECTION_BD_DRIVE);

		// TODO - dados da conexão devem ser configuráveis
		return DriverManager.getConnection(Configuration.CONNECTION_BD_URL, 
										   Configuration.CONNECTION_BD_USER, 
										   Configuration.CONNECTION_BD_PASSWORD);
	}
}
