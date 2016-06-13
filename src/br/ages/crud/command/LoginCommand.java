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

	@Override
	public String execute(HttpServletRequest request) {
		// seta a mesma pagina, para o caso de erro/exce��o
		proxima = "login.jsp";
		Usuario user = new Usuario();
		usuarioBO = new UsuarioBO();
		util = new Util();

		String username = request.getParameter("login");
		String password = request.getParameter("senha");
		
		try {
			AgesSecurityResult result = (AgesSecurityResult) AgesSecurity.validate(request, username, password);
//			AgesSecurity.create("AGESUsuario", "123456");
			
			if (result.isSucceeded()) {
				proxima = "index.jsp";
				
				// TODO - Obter Usuario do sistema cliente a partir do usu�rio AgesSecurityUser
				Usuario usuarioDTO = new Usuario(username, password);
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
