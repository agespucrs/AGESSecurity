package br.ages.crud.command;

import javax.servlet.http.HttpServletRequest;

import br.ages.crud.bo.UsuarioBO;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.MensagemContantes;
import br.ages.crud.util.Util;

public class LogoutCommand implements Command {



	private String proxima;

	@Override
	public String execute(HttpServletRequest request) {
		// seta a mesma pagina, para o caso de erro/exceção
		proxima = "login.jsp";
		Usuario user = null;
		usuarioBO = new UsuarioBO();
		util = new Util();
		

		try {
			request.getSession().setAttribute("usuarioSessao", null);
			request.setAttribute("msgSucesso", MensagemContantes.MSG_INF_LOGOUT);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msgErro", e.getMessage());
		}
		
		return proxima;
	}
}
