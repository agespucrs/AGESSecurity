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

import br.ages.security.infrastructure.Configuration;
import br.ages.security.infrastructure.ConnectionUtil;
import br.ages.security.interfaces.dao.IAgesSecurityDAO;
import br.ages.security.interfaces.models.IAgesSecurityUser;
import br.ages.security.models.AgesSecurityUser;

public class AgesSecurityDAO implements IAgesSecurityDAO {
	
	private Connection connection;
	
	public AgesSecurityDAO(ServletContext context) throws SQLException, ClassNotFoundException{
		String connectionUrl = context.getInitParameter("connection-url");
		String sgbdUser = context.getInitParameter("sgbd-user");
		String sgbdPassword = context.getInitParameter("sgbd-password");
		String sgbdDriver = context.getInitParameter("sgbd-driver");
		
		Class.forName(sgbdDriver);
		connection = DriverManager.getConnection(connectionUrl, sgbdUser, sgbdPassword);
	}

	@Override
	public IAgesSecurityUser getUserByUsernameAndPassword(String username, String password)
			throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
		AgesSecurityUser user = null;
			
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ages_security_user where username = ? and password = ?");

		PreparedStatement statement = connection.prepareStatement(sql.toString());
		statement.setString(1, username);

		// TODO - voltar com a criptografia após a método de criação do usuário
		// statement.setString(2, encrytpPassword(password));
		statement.setString(2, password);

		ResultSet resultset = statement.executeQuery();
		if (resultset.next()) {
			user = new AgesSecurityUser();
			user.setUsername(resultset.getString("USERNAME"));
		}

		return user;
	}

	public boolean deleteUserbyName(String username) throws ClassNotFoundException, SQLException {

		Connection connection = ConnectionUtil.getConnection();

		boolean isSucceed = false;

		java.sql.Statement st = connection.createStatement();
		StringBuilder sql = new StringBuilder();
		sql.append("delete * from ages_security_user where username = " + username);
		isSucceed = true;

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

	public AgesSecurityUser getUserByUsername(String username) throws ClassNotFoundException, SQLException {
		AgesSecurityUser user = null;

		Connection connection = ConnectionUtil.getConnection();

		StringBuilder sql = new StringBuilder();

		sql.append("select * from ages_security_user where username = ?");

		PreparedStatement statement = connection.prepareStatement(sql.toString());
		statement.setString(1, username);
		ResultSet resultset = statement.executeQuery();
		if (resultset.next()) {
			user = new AgesSecurityUser();
			user.setUsername(resultset.getString("USERNAME"));
		}
		
		return user;
	}

	// Cria um novo usuário
	public boolean Create(String username, String password) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
		boolean isSucceeded = false;

		AgesSecurityUser user = getUserByUsername(username);
		if (user == null) {
			user = new AgesSecurityUser();
			user.setUsername(username);
			user.setPassword(password);

			StringBuilder sql = new StringBuilder();
			final String uuid = UUID.randomUUID().toString().replaceAll("-", "");

			sql.append("Insert into ages_security_user (id, username, password) values (?, ?, ?)");
			PreparedStatement statement = connection.prepareStatement(sql.toString());
			statement.setString(1, uuid);
			statement.setString(2, username);
			statement.setString(3, encrytpPassword(password));
			statement.executeUpdate();
			isSucceeded = true;
		}

		return isSucceeded;

	}
}