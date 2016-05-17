package br.ages.security;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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

import br.ages.crud.util.MensagemContantes;
import br.ages.security.dao.AgesSecurityDAO;
import br.ages.security.interfaces.models.IAgesSecurityResult;
import br.ages.security.models.AgesSecurityResult;
import br.ages.security.models.AgesSecurityUser;

@WebFilter("/*")
public final class AgesSecurity implements Filter {
	
	private static final String SESSION_KEY = "AgesSecurityUser";
	private static AgesSecurityDAO agesSecurityDao;
	
	static {
		agesSecurityDao = new AgesSecurityDAO();
	}

	// Valida se o usu�rio est� no banco gerenciado pelo AgesSecurity
	public static IAgesSecurityResult validate(HttpServletRequest request, String username, String password) throws ClassNotFoundException, SQLException {
		
		AgesSecurityResult result = new AgesSecurityResult();
		
		// TODO Acessa o banco e verifica se nome e senha s�o v�lidos
		AgesSecurityUser user = (AgesSecurityUser) agesSecurityDao.getUserByUsernameAndPassword(username, password);
		
		if (user != null){
			result.setIsSucceeded(true);
			request.getSession().setAttribute(SESSION_KEY, user);
		} else {
			result.setMessage("O usu�rio n�o est� cadastrado");
		}
		
		return result;
	}
	
	// Remove valida��o do usu�rio
	public static IAgesSecurityResult logout(HttpServletRequest request){
		AgesSecurityResult result = new AgesSecurityResult();
		request.getSession().setAttribute("AgesSecurityUser", null);
		result.setIsSucceeded(true);
		return result;
	}
	
	/**************** Filter (autoriza��o) ****************/

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = ((HttpServletRequest) request);
	    String servletPath = ((HttpServletRequest) request).getServletPath().split("/")[1];   
	    
	    // TODO - Obter URLs protegidas do xml de configura��o
		ArrayList<String> protectedUrls = new ArrayList<String>();
		protectedUrls.add("index.jsp"); 
	
		if (protectedUrls.contains(servletPath)) {
			HttpSession session = httpRequest.getSession();
			if (session.getAttribute(SESSION_KEY) == null) {
				
				// TODO - Obter URL de redirecionamento do xml de configura��o
				((HttpServletResponse)response).sendError(HttpServletResponse.SC_FORBIDDEN);
			} else {
				chain.doFilter(request, response);
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	/**************** Fim Filter (autoriza��o) ****************/
}
