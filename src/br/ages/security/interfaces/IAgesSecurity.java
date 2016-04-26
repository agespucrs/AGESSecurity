package br.ages.security.interfaces;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import br.ages.security.interfaces.models.IAgesSecurityResult;
import br.ages.security.interfaces.models.IAgesSecurityUser;

public interface IAgesSecurity {
	
	/**
	* Valida o usuário e cria resultado da validação na sessão
	*
	* @param request contexto da requisição http 
	* @param user usuário a ser validado
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	*/
	IAgesSecurityResult validate(HttpServletRequest request, String username, String password)
			throws ClassNotFoundException, SQLException;

}
