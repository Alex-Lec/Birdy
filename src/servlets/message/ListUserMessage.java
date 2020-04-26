package servlets.message;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

@SuppressWarnings("serial")
public class ListUserMessage extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id_user = Integer.parseInt(request.getParameter("id_user"));
		
		JSONObject json = services.MessageServices.listMessage(id_user);
		
		response.setContentType("text/json");
		response.getWriter().println(json.toString());
	}
}