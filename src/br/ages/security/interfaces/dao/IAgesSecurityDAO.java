package br.ages.security.interfaces.dao;

import java.sql.SQLException;

import br.ages.security.interfaces.models.IAgesSecurityUser;

public interface IAgesSecurityDAO {
	IAgesSecurityUser GetUserByUsernameAndPassword(String username, String password) throws ClassNotFoundException, SQLException;
}
