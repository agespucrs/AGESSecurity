package br.ages.security.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.ages.security.infrastructure.ConnectionUtil;
import br.ages.security.interfaces.dao.IAgesSecurityDAO;
import br.ages.security.interfaces.models.IAgesSecurityUser;
import br.ages.security.models.AgesSecurityUser;

public class AgesSecurityDAO implements IAgesSecurityDAO {

	@Override
	public IAgesSecurityUser getUserByUsernameAndPassword(String username, String password) throws ClassNotFoundException, SQLException {
		AgesSecurityUser user = null;
				
		Connection connection = ConnectionUtil.getConnection();
		
		// TODO - tratamento para evitar SQL injection
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ages_security_user where username = ? and password = ?");

		PreparedStatement statement = connection.prepareStatement(sql.toString());
		statement.setString(1, username);
		statement.setString(2, password);

		ResultSet resultset = statement.executeQuery();
		if (resultset.next()) {
			user = new AgesSecurityUser();
			user.setUsername(resultset.getString("USERNAME"));
		}
		
		return user;
	}
	
	public boolean deleteUserbyId(Integer idUsuario) throws ClassNotFoundException, SQLException{
		
		Connection connection = ConnectionUtil.getConnection();
		
		boolean isSucceed = false;
			
		java.sql.Statement st = connection.createStatement();
		StringBuilder sql = new StringBuilder();
		sql.append("delete * from ages_security_user where id = "+ idUsuario );
		isSucceed= true;
			
		return isSucceed;
	}
	
}
