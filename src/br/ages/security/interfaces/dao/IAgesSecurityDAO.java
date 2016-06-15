package br.ages.security.interfaces.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import br.ages.security.interfaces.models.IAgesSecurityUser;
import br.ages.security.models.AgesSecurityUser;

public interface IAgesSecurityDAO {
		
	/**
	 * Cria um novo usu�rio
	 * 
	 * @param username Nome de usu�rio
	 * @param password Senha
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws NoSuchAlgorithmException
	 */
	boolean create(String username, String password) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException;
	
	
	/**
	 * Retorna a listagem de todos os usu�rios cadastrados
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	List<AgesSecurityUser> list() throws SQLException;
	
	/**
	 * Busca um usu�rio pelo nome de usu�rio e senha
	 * 
	 * @param username Nome de usu�rio
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
