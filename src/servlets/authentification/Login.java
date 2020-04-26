package servlets.authentification;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

@SuppressWarnings("serial")
public class Login extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login_user = request.getParameter("login_user");
		String password_user = request.getParameter("password_user");
		
		JSONObject json = services.AuthentificationServices.login(login_user, password_user);
		
		response.setContentType("text/json");
		response.getWriter().println(json.toString());
	}
}