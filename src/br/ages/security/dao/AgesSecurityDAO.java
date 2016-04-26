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
		sql.append("select * from tb_usuario where usuario = ? and senha = ?");

		PreparedStatement statement = connection.prepareStatement(sql.toString());
		statement.setString(1, username);
		statement.setString(2, password);

		ResultSet resultset = statement.executeQuery();
		if (resultset.next()) {
			user = new AgesSecurityUser();
			user.setUsername(resultset.getString("USUARIO"));
		}
		
		return user;
	}
}
