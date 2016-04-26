package br.ages.security.interfaces;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import br.ages.security.interfaces.models.IAgesSecurityResult;
import br.ages.security.interfaces.models.IAgesSecurityUser;

public interface IAgesSecurity {
	
	/**
	* Valida o usu�rio e cria resultado da valida��o na sess�o
	*
	* @param request contexto da requisi��o http 
	* @param user usu�rio a ser validado
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	*/
	IAgesSecurityResult validate(HttpServletRequest request, String username, String password)
			throws ClassNotFoundException, SQLException;

}
