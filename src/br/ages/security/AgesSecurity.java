package br.ages.security;

import javax.servlet.http.HttpServletRequest;

import br.ages.security.interfaces.IAgesSecurity;
import br.ages.security.interfaces.models.IAgesSecurityResult;

public class AgesSecurity implements IAgesSecurity {

	@Override
	public IAgesSecurityResult Validate(HttpServletRequest request, IAgesSecurity user) {
		
		// TODO Acessa o banco e verifica se nome e senha são válidos
		// Se for válido, cria usuário na sessão
		
		return null;
	}


}
