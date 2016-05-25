package br.ages.crud.command;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import br.ages.crud.bo.UsuarioBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.model.PerfilAcesso;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.MensagemContantes;
import br.ages.security.dao.AgesSecurityDAO;
import br.ages.security.models.AgesSecurityResult;
import br.ages.security.models.AgesSecurityUser;

public class RemoveUserCommand implements Command {

	private String proximo;

	private AgesSecurityDAO agesDAO;

	@Override
	public String execute(HttpServletRequest request) throws SQLException {
		proximo = "main?acao=listUser";
		this.agesDAO = new AgesSecurityDAO();
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuarioSessao");		
		/*
		
		try {
			if(!usuario.getPerfilAcesso().equals(PerfilAcesso.ADMINISTRADOR) ) throw new NegocioException(MensagemContantes.MSG_INF_DENY);
			
			Integer idUsuario = Integer.parseInt(request.getParameter("id_usuario"));
			usuarioBO.removerUsuario(idUsuario);
			
			request.setAttribute("msgSucesso", MensagemContantes.MSG_SUC_REMOVE_USUARIO.replace("?", idUsuario.toString()).concat("<br/>"));
			
		} catch (Exception e) {
			request.setAttribute("msgErro", e.getMessage());
		}
	*/
		//AgesSecurityUser user = (AgesSecurityUser) request.getAttribute("usuarioSessao");
			//if(user.getUsername().equals(PerfilAcesso.ADMINISTRADOR)){
				
			//}
		Integer idUsuario = Integer.parseInt(request.getParameter("id_usuario"));
		try {
			if(agesDAO.deleteUserbyId(idUsuario)){
			request.setAttribute("msgSucesso", MensagemContantes.MSG_SUC_REMOVE_USUARIO.replace("?", idUsuario.toString()).concat("<br/>"));
			}else{
				request.setAttribute("msgErro", MensagemContantes.MSG_REJ_REMOVE_USUARIO);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return proximo;
		
	}
	
	
}
	