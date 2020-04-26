package servlets.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@SuppressWarnings("serial")
public class ProfileUser extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login_user = request.getParameter("login_user");

		JSONObject json = services.UserServices.getProfile(login_user);
		
		response.setContentType("text/json");
		response.getWriter().println(json.toString());
	}
}