package br.ages.security;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import br.ages.security.dao.AgesSecurityDAO;
import br.ages.security.interfaces.models.IAgesSecurityResult;
import br.ages.security.models.AgesSecurityResult;
import br.ages.security.models.AgesSecurityUser;

public final class AgesSecurity {

	private static AgesSecurityDAO agesSecurityDao;
	
	static {
		agesSecurityDao = new AgesSecurityDAO();
	}

	public static IAgesSecurityResult validate(HttpServletRequest request, String username, String password) throws ClassNotFoundException, SQLException {
		
		AgesSecurityResult result = new AgesSecurityResult();
		
		// TODO Acessa o banco e verifica se nome e senha são válidos
		AgesSecurityUser user = (AgesSecurityUser) agesSecurityDao.getUserByUsernameAndPassword(username, password);
		
		if (user != null){
			result.setIsSucceeded(true);
			request.getSession().setAttribute("AgesSecurityUser", user);
		} else {
			result.setMessage("O usuário não está cadastrado");
		}
		
		return result;
	}
}
