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
	public IAgesSecurityUser GetUserByUsernameAndPassword(String username, String password) throws ClassNotFoundException, SQLException {
		AgesSecurityUser user = new AgesSecurityUser();
				
		Connection connection = ConnectionUtil.getConnection();
		
		StringBuilder sql = new StringBuilder();
		sql.append("select * from tb_usuario ");
		sql.append("where usuario = ? and senha = ?");

		PreparedStatement statement = connection.prepareStatement(sql.toString());
		statement.setString(1, username);
		statement.setString(2, password);

		ResultSet resultset = statement.executeQuery();
		if (resultset.next()) {
			user.setUsername(resultset.getString("USERNAME"));
			user.setUsername(resultset.getString("USERNAME"));
		} else {
			user = null;
		}
		
		return user;
	}
}
