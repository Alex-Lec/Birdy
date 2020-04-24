package servlets.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@SuppressWarnings("serial")
public class CreateUser extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login_user = request.getParameter("login_user");
		String password_user = request.getParameter("password_user");
		String prenom_user = request.getParameter("prenom_user");
		String nom_user = request.getParameter("nom_user");
		String mail_user = request.getParameter("mail_user");
		
		JSONObject json = services.UserServices.createUser(login_user, password_user, prenom_user, nom_user, mail_user);
		
		response.setContentType("text/json");
		response.getWriter().println(json.toString());
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login_user = request.getParameter("login_user");
		String password_user = request.getParameter("password_user");
		String prenom_user = request.getParameter("prenom_user");
		String nom_user = request.getParameter("nom_user");
		String mail_user = request.getParameter("mail_user");
		
		JSONObject json = services.UserServices.createUser(login_user, password_user, prenom_user, nom_user, mail_user);
		
		response.setContentType("text/json");
		response.getWriter().println(json.toString());
	}
}