package br.ages.security;

import java.util.List;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
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
import br.ages.security.models.AgesSecurityResult.AgesSecurityStatus;
import br.ages.security.models.AgesSecurityUser;

@WebFilter("/*")
public final class AgesSecurity implements Filter {
	
	private static final String SESSION_KEY = "AgesSecurityUser";
	private static AgesSecurityDAO agesSecurityDao;
	private static ArrayList<String> protectedResources;

	public static IAgesSecurityResult validate(HttpServletRequest request, String username, String password) throws ClassNotFoundException, SQLException {
		
		AgesSecurityResult result = new AgesSecurityResult();
		
		try{
			AgesSecurityUser user = (AgesSecurityUser) agesSecurityDao.getUserByUsernameAndPassword(username, password);	
			if (user != null) {
				result.setIsSucceeded(true);
				request.getSession().setAttribute(SESSION_KEY, user);
			} 
			else {
				result.setStatus(AgesSecurityStatus.INVALID_DATA);
			}
		} 
		catch(ClassNotFoundException cnfe){
			result.setStatus(AgesSecurityStatus.CLASS_NOT_FOUND);
		}
		catch(SQLException sqle){
			result.setStatus(AgesSecurityStatus.DATABASE_CONNECTION_ERROR);
		}
		catch(NoSuchAlgorithmException nsae){
			result.setStatus(AgesSecurityStatus.UNEXPECTED_ERROR);
		}
		catch(Exception e) {
			result.setStatus(AgesSecurityStatus.UNEXPECTED_ERROR);
		}
		
		return result;
	}
	
	public static IAgesSecurityResult logout(HttpServletRequest request) {
		AgesSecurityResult result = new AgesSecurityResult();
		request.getSession().setAttribute(SESSION_KEY, null);
		result.setIsSucceeded(true);
		return result;
	}
	
	public static IAgesSecurityResult create (String username, String password) throws ClassNotFoundException, NoSuchAlgorithmException, SQLException {
		AgesSecurityResult result = new AgesSecurityResult ();
		
		if(agesSecurityDao.create(username, password)){
			result.setStatus(AgesSecurityStatus.OPERATION_SUCCESS);
		}
		else {
			result.setStatus(AgesSecurityStatus.DATA_ALREADY_EXISTS);
		} 
			
		return result;
	}
	
	public static void delete(UUID userId) throws ClassNotFoundException, NoSuchAlgorithmException, SQLException{
		AgesSecurityResult result = new AgesSecurityResult ();
		
		if(agesSecurityDao.delete(userId)){
			result.setStatus(AgesSecurityStatus.OPERATION_SUCCESS);
		}else {
			result.setStatus(AgesSecurityStatus.DATA_NOT_EXISTS);
		}
	}
	
	public static List<AgesSecurityUser> list() throws Exception
	{	
		try {
			return agesSecurityDao.list();
		} 
		catch(SQLException sqle){
			sqle.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
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
	    
		if ( protectedResources != null ) {
			for (String resource : protectedResources) { 
				if (requestedUrl.contains(resource)) {
					HttpSession session = httpServletRequest.getSession();
					if (session.getAttribute(SESSION_KEY) == null) {
						((HttpServletResponse)response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
						return;
					} else {
						chain.doFilter(request, response);
					}
				} 
			}
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		try{
			ServletContext context = arg0.getServletContext();
			agesSecurityDao = new AgesSecurityDAO(context); 
			protectedResources = new ArrayList<String>(Arrays.asList(context.getInitParameter("protected-resources").split(",")));			
		} 
		catch(Exception e) {
			String message = e.getMessage();
			System.out.println(message);
		}
	}
	
	/**************** Fim Filtro (autorização) ****************/
	
	public static AgesSecurityUser getLoggedUser(HttpServletRequest request) {
		return (AgesSecurityUser) request.getSession().getAttribute(SESSION_KEY);
	}
}
