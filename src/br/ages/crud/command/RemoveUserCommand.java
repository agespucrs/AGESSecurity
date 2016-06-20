package br.ages.crud.command;

import javax.servlet.http.HttpServletRequest;

import br.ages.crud.exception.NegocioException;
import br.ages.crud.model.PerfilAcesso;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.MensagemContantes;

public class RemoveUserCommand implements Command {

	private String proximo;

	@Override
	public String execute(HttpServletRequest request) {
		proximo = "main?acao=listUser";
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuarioSessao");		

		try {
			if( !usuario.getPerfilAcesso().equals(PerfilAcesso.ADMINISTRADOR) ) throw new NegocioException(MensagemContantes.MSG_INF_DENY);
			
			String username = request.getParameter("usuario");
			
			request.setAttribute("msgSucesso", MensagemContantes.MSG_SUC_REMOVE_USUARIO.replace("?", username.toString()).concat("<br/>"));
			
		} catch (Exception e) {
			request.setAttribute("msgErro", e.getMessage());
		}
		
		return proximo;
	}

}
