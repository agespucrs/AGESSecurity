package br.ages.crud.command;

import javax.servlet.http.HttpServletRequest;

import br.ages.crud.bo.UsuarioBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.Util;
import br.ages.security.AgesSecurity;
import br.ages.security.models.AgesSecurityResult;

public class LoginCommand implements Command {

	private UsuarioBO usuarioBO;
	private String proxima;
	private Util util;
	private AgesSecurity agesSecurity;

	@Override
	public String execute(HttpServletRequest request) {
		// seta a mesma pagina, para o caso de erro/exceção
		proxima = "login.jsp";
		Usuario user = new Usuario();
		usuarioBO = new UsuarioBO();
		util = new Util();
		agesSecurity = new AgesSecurity();

		String username = request.getParameter("login");
		String password = request.getParameter("senha");
		
		try {
			AgesSecurityResult result = (AgesSecurityResult) agesSecurity.validate(request, username, password);
			
			if (result.isSucceeded()) {
				proxima = "index.jsp";
				
				// TODO - Obter Usuario do sistema cliente a partir do usuário AgesSecurityUser
				Usuario usuarioDTO = new Usuario((String) request.getParameter("login"), (String) request.getParameter("senha"));
				user = usuarioBO.validaUsuario(usuarioDTO);
				request.getSession().setAttribute("usuarioSessao", user);
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
