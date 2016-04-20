package br.ages.security.interfaces;

import javax.servlet.http.HttpServletRequest;

import br.ages.security.interfaces.models.IAgesSecurityResult;

public interface IAgesSecurity {
	
	/**
	* Valida o usuário e cria resultado da validação na sessão
	*
	* @param request contexto da requisição http 
	* @param user usuário a ser validado
	*/
	IAgesSecurityResult Validate(HttpServletRequest request, IAgesSecurity user);
}
