package br.ages.security.interfaces.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import br.ages.security.interfaces.models.IAgesSecurityUser;
import br.ages.security.models.AgesSecurityUser;

public interface IAgesSecurityDAO {
		
	/**
	 * Cria um novo usuário
	 * 
	 * @param username Nome de usuário
	 * @param password Senha
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws NoSuchAlgorithmException
	 */
	boolean create(String username, String password) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException;
	
	
	/**
	 * Retorna a listagem de todos os usuários cadastrados
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	List<AgesSecurityUser> list() throws SQLException;
	
	/**
	 * Busca um usuário pelo nome de usuário e senha
	 * 
	 * @param username Nome de usuário
	 * @param password Senha
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws NoSuchAlgorithmException
	 */
	IAgesSecurityUser getUserByUsernameAndPassword(String username, String password) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException;
	
	/**
	 * @param userId
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	boolean delete(UUID userId) throws ClassNotFoundException, SQLException;
}
