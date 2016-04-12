package br.ages.crud.command;

import javax.servlet.http.HttpServletRequest;

import br.ages.crud.bo.UsuarioBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.Util;

public class LoginCommand implements Command {

	private UsuarioBO usuarioBO;

	private String proxima;

	private Util util;

	@Override
	public String execute(HttpServletRequest request) {
		// seta a mesma pagina, para o caso de erro/exceção
		proxima = "login.jsp";
		Usuario user = new Usuario();
		usuarioBO = new UsuarioBO();
		util = new Util();


		Usuario usuarioDTO = new Usuario((String) request.getParameter("login"), (String) request.getParameter("senha"));

		try {
			user = usuarioBO.validaUsuario(usuarioDTO); 
			if (user != null) {
				request.getSession().setAttribute("usuarioSessao", user);
				request.getSession().setAttribute("versao", util.getVersion());
				proxima = "index.jsp";
			
			}
		} catch (NegocioException e) {
			request.setAttribute("msgErro" , e.getMessage());
			return proxima;
		}
		
		return proxima;
	}
}
