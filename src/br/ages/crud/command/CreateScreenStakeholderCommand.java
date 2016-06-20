package br.ages.crud.command;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

public class CreateScreenStakeholderCommand implements Command {

	private String proxima;
//	private StakeholderBO stakeholderBO;

	@Override
	public String execute(HttpServletRequest request) throws SQLException {
		proxima = "";
		String isEdit = request.getParameter("isEdit");
		try {
			if (isEdit != null && !"".equals(isEdit)) {

//				int idStakeholder = Integer.parseInt(request.getParameter("idStakeholder"));
//				Stakeholder stakeholder = stakeholderBO.buscaStakeholderId(idStakeholder);
//				request.setAttribute("nomeStakeholder", stakeholder.getNomeStakeholder());
				
				proxima = "project/addStakeholder.jsp";

			} else {
				proxima = "project/addStakeholder.jsp";

			}

		} catch (Exception e) {
			request.setAttribute("msgErro", e.getMessage());
		}

		return proxima;
	}
}
