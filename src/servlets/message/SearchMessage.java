package servlets.message;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@SuppressWarnings("serial")
public class SearchMessage extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String key_session = request.getParameter("key_session");
		String keyword = request.getParameter("keyword");
		boolean filterByFriends = Boolean.valueOf(request.getParameter("filterByFriends")).booleanValue();
		
		JSONObject json = services.MessageServices.searchMessage(key_session, keyword, filterByFriends);
		
		response.setContentType("text/json");
		response.getWriter().println(json.toString());
	}
}