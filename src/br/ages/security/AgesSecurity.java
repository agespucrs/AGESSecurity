package br.ages.security;

import javax.servlet.http.HttpServletRequest;

import br.ages.security.interfaces.IAgesSecurity;
import br.ages.security.interfaces.models.IAgesSecurityResult;

public class AgesSecurity implements IAgesSecurity {

	@Override
	public IAgesSecurityResult Validate(HttpServletRequest request, IAgesSecurity user) {
		
		// TODO Acessa o banco e verifica se nome e senha s�o v�lidos
		// Se for v�lido, cria usu�rio na sess�o
		
		return null;
	}


}
