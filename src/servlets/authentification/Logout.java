package servlets.authentification;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

@SuppressWarnings("serial")
public class Logout extends HttpServlet {
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String key_session = request.getParameter("key_session");
		
		JSONObject json = services.AuthentificationServices.logout(key_session);
		
		response.setContentType("text/json");
		response.getWriter().println(json.toString());
	}
}
