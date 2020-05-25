package servlets.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@SuppressWarnings("serial")
public class EditUser extends HttpServlet {
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String key_session = request.getParameter("key_session");
		String login_user = request.getParameter("login_user");
		String password_user = request.getParameter("password_user");
		String prenom_user = request.getParameter("prenom_user");
		String nom_user = request.getParameter("nom_user");
		String mail_user = request.getParameter("mail_user");
		
		JSONObject json = services.UserServices.editUser(key_session, login_user, password_user, prenom_user, nom_user, mail_user);
		
		response.setContentType("text/json");
		response.getWriter().println(json.toString());
	}
}