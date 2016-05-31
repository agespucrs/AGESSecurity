package br.ages.security.interfaces.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import br.ages.security.interfaces.models.IAgesSecurityUser;

public interface IAgesSecurityDAO {
	IAgesSecurityUser getUserByUsernameAndPassword(String username, String password) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException;
}
