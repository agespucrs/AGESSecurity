package br.ages.security.interfaces;

import javax.servlet.http.HttpServletRequest;

import br.ages.security.interfaces.models.IAgesSecurityResult;

public interface IAgesSecurity {
	
	/**
	* Valida o usu�rio e cria resultado da valida��o na sess�o
	*
	* @param request contexto da requisi��o http 
	* @param user usu�rio a ser validado
	*/
	IAgesSecurityResult Validate(HttpServletRequest request, IAgesSecurity user);
}
