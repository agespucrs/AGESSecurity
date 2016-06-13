package br.ages.security.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.ServletContext;

import br.ages.security.infrastructure.ConnectionUtil;
import br.ages.security.interfaces.dao.IAgesSecurityDAO;
import br.ages.security.interfaces.models.IAgesSecurityUser;
import br.ages.security.models.AgesSecurityUser;

public class AgesSecurityDAO implements IAgesSecurityDAO {
	
	private Connection connection;
	
	public AgesSecurityDAO(ServletContext context) throws SQLException, ClassNotFoundException {
		String connectionUrl = context.getInitParameter("connection-url");
		String sgbdUser = context.getInitParameter("sgbd-user");
		String sgbdPassword = context.getInitParameter("sgbd-password");
		String sgbdDriver = context.getInitParameter("sgbd-driver");
		
		Class.forName(sgbdDriver);
		connection = DriverManager.getConnection(connectionUrl, sgbdUser, sgbdPassword);
	}
	
	public boolean create(String username, String password) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
		AgesSecurityUser user = new AgesSecurityUser();
		user.setUsername(username);
		user.setPassword(password);

		StringBuilder sql = new StringBuilder();
		final String uuid = UUID.randomUUID().toString().replaceAll("-", "");

		sql.append("insert into ages_security_user (id, username, password) values (?, ?, ?)");
		PreparedStatement statement = connection.prepareStatement(sql.toString());
		statement.setString(1, uuid);
		statement.setString(2, username);
		statement.setString(3, encrytpPassword(password));
		statement.executeUpdate();
		
		return true;
	}

	public IAgesSecurityUser getUserByUsernameAndPassword(String username, String password) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
		AgesSecurityUser user = null;
			
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ages_security_user where username = ? and password = ?");

		PreparedStatement statement = connection.prepareStatement(sql.toString());
		statement.setString(1, username);
		statement.setString(2, encrytpPassword(password));

		ResultSet resultset = statement.executeQuery();
		if (resultset.next()) {
			user = new AgesSecurityUser();
			user.setUsername(resultset.getString("USERNAME"));
		}

		return user;
	}

	public boolean delete(UUID userId) throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionUtil.getConnection();
		boolean isSucceed = false;
		
		StringBuilder sql = new StringBuilder();
		sql.append("delete * from ages_security_user where id = ?");
		
		PreparedStatement statement = connection.prepareStatement(sql.toString());
		statement.setString(1, userId.toString());
		
		if (statement.execute()) {
			isSucceed = true;			
		}
		
		return isSucceed;
	}

	private String encrytpPassword(String passwordToHash) throws NoSuchAlgorithmException {
		// Create MessageDigest instance for MD5
		MessageDigest md = MessageDigest.getInstance("MD5");

		// Add password bytes to digest
		md.update(passwordToHash.getBytes());

		// Get the hash's bytes
		byte[] bytes = md.digest();

		// This bytes[] has bytes in decimal format;
		// Convert it to hexadecimal format
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}

		// Get complete hashed password in hex format
		return sb.toString();
	}
}