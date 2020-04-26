package servlets.message;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

@SuppressWarnings("serial")
public class AddMessage extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id_user = Integer.parseInt(request.getParameter("id_user"));
		String key_session = request.getParameter("key_session");
		String login_user = request.getParameter("login_user");
		String text = request.getParameter("text");
		
		JSONObject json = services.MessageServices.addMessage(id_user, login_user, text, key_session);
		
		response.setContentType("text/json");
		response.getWriter().println(json.toString());
	}
}