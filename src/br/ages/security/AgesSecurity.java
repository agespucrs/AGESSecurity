package br.ages.security;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import br.ages.security.dao.AgesSecurityDAO;
import br.ages.security.interfaces.IAgesSecurity;
import br.ages.security.interfaces.models.IAgesSecurityResult;
import br.ages.security.models.AgesSecurityResult;
import br.ages.security.models.AgesSecurityUser;

public class AgesSecurity implements IAgesSecurity {

	private AgesSecurityDAO agesSecurityDao;
	
	public AgesSecurity() {
		this.agesSecurityDao = new AgesSecurityDAO();
	}

	@Override
	public IAgesSecurityResult validate(HttpServletRequest request, String username, String password) throws ClassNotFoundException, SQLException {
		
		AgesSecurityResult result = new AgesSecurityResult();
		
		// TODO Acessa o banco e verifica se nome e senha são válidos
		AgesSecurityUser user = (AgesSecurityUser) agesSecurityDao.getUserByUsernameAndPassword(username, password);
		
		if (user != null){
			result.setIsSucceeded(true);
			request.getSession().setAttribute("AgesSecurityUser", user);
			
		} else {
			result.setMessage("O usuário não está autorizado");
		}
		
		return result;
	}
}
