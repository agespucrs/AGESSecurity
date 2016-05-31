package br.ages.security;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.ages.security.dao.AgesSecurityDAO;
import br.ages.security.interfaces.models.IAgesSecurityResult;
import br.ages.security.models.AgesSecurityResult;
import br.ages.security.models.AgesSecurityUser;

@WebFilter("/*")
public final class AgesSecurity implements Filter {
	
	private static final String SESSION_KEY = "AgesSecurityUser";
	private static AgesSecurityDAO agesSecurityDao;
	private static ArrayList<String> protectedResources;
	
	static {
		agesSecurityDao = new AgesSecurityDAO();
	}

	// Valida se o usuário está no banco gerenciado pelo AgesSecurity
	public static IAgesSecurityResult validate(HttpServletRequest request, String username, String password) throws ClassNotFoundException, SQLException {
		
		AgesSecurityResult result = new AgesSecurityResult();
		
		try{
			AgesSecurityUser user = (AgesSecurityUser) agesSecurityDao.getUserByUsernameAndPassword(username, password);	
			if (user != null) {
				result.setIsSucceeded(true);
				request.getSession().setAttribute(SESSION_KEY, user);
			} 
			else {
				result.setMessage("O usuário não está cadastrado");
			}
		} 
		catch(ClassNotFoundException cnfe){
			result.setMessage("Exceção interna: classe não encontrada.");
		}
		catch(SQLException sqle){
			result.setMessage("Erro na comunicação com o banco de dados.");
		}
		catch(NoSuchAlgorithmException nsae){
			result.setMessage("Algortimo de criptografia não encontrado.");
		}
		catch(Exception e) {
			result.setMessage("Ocorreu um erro inesperado.");
		}
		
		return result;
	}
	
	// Remove validação do usuário
	public static IAgesSecurityResult logout(HttpServletRequest request) {
		AgesSecurityResult result = new AgesSecurityResult();
		request.getSession().setAttribute("AgesSecurityUser", null);
		result.setIsSucceeded(true);
		return result;
	}
	
	
	/**************** Filtro (autorização) ****************/

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// Obtendo URL requisitada
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String requestedUrl = httpServletRequest.getRequestURI();
		if (httpServletRequest.getQueryString() != null){
		
			requestedUrl += "?" + httpServletRequest.getQueryString(); 
		}
	    
		for (String resource : protectedResources) { 
			if (requestedUrl.contains(resource)) {
				HttpSession session = httpServletRequest.getSession();
				if (session.getAttribute(SESSION_KEY) == null) {
					((HttpServletResponse)response).sendError(HttpServletResponse.SC_FORBIDDEN);
				} else {
					chain.doFilter(request, response);
				}
			} 
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		try{
			// Lê a lista de URLs protegidas 
			protectedResources = new ArrayList<String>(Arrays.asList(arg0.getServletContext().getInitParameter("protected-resources").split(",")));	
		} catch(Exception e){
			String message = e.getMessage();
		}
		
	}
	
	/**************** Fim Filtro (autorização) ****************/
}
