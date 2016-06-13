package br.ages.crud.command;

import javax.servlet.http.HttpServletRequest;

import br.ages.crud.bo.UsuarioBO;
import br.ages.crud.dao.UsuarioDAO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.model.PerfilAcesso;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.MensagemContantes;
import br.ages.security.AgesSecurity;

public class RemoveUserCommand implements Command {

	private String proximo;

	private UsuarioBO usuarioBO;
	private AgesSecurity agesSecurity;
	@Override
	public String execute(HttpServletRequest request) {
		proximo = "main?acao=listUser";
		this.usuarioBO = new UsuarioBO();
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuarioSessao");		

		try {
			if( !usuario.getPerfilAcesso().equals(PerfilAcesso.ADMINISTRADOR) ) throw new NegocioException(MensagemContantes.MSG_INF_DENY);
			
			String username = request.getParameter("usuario");
			// agesSecurity.removerUsuario(username);
			// usuarioBO.removerUsuario(usuario);
			
			
			request.setAttribute("msgSucesso", MensagemContantes.MSG_SUC_REMOVE_USUARIO.replace("?", username.toString()).concat("<br/>"));
			
		} catch (Exception e) {
			request.setAttribute("msgErro", e.getMessage());
		}
		
		return proximo;
	}

}
