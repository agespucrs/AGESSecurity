package br.ages.crud.command;

import javax.servlet.http.HttpServletRequest;

import br.ages.crud.model.Usuario;
import br.ages.crud.util.Util;
import br.ages.security.AgesSecurity;
import br.ages.security.models.AgesSecurityResult;

public class LoginCommand implements Command {

	private String proxima;
	private Util util;

	@Override
	public String execute(HttpServletRequest request) {
		// seta a mesma pagina, para o caso de erro/exceção
		proxima = "login.jsp";
		util = new Util();

		String username = request.getParameter("login");
		String password = request.getParameter("senha");
		
		try {
			AgesSecurityResult result = (AgesSecurityResult) AgesSecurity.validate(request, username, password);
//			AgesSecurity.create("AGESUsuario", "123456");
			
			if (result.isSucceeded()) {
				proxima = "index.jsp";
				Usuario user = new Usuario(AgesSecurity.getLoggedUser(request));
				request.getSession().setAttribute("userLogged", user);
				request.getSession().setAttribute("versao", util.getVersion());
			}
			else {
				request.setAttribute("msgErro" , result.getMessage());
			}
			
		} catch(Exception e){
			request.setAttribute("msgErro" , e.getMessage());
			return proxima;
		}
		
		return proxima;
	}
}
