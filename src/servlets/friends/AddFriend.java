package servlets.friends;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

@SuppressWarnings("serial")
public class AddFriend extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id_user_1 = Integer.parseInt(request.getParameter("id_user_1"));
		int id_user_2 = Integer.parseInt(request.getParameter("id_user_2"));
		
		JSONObject json = services.FriendsServices.addFriend(id_user_1, id_user_2);
		
		response.setContentType("text/json");
		response.getWriter().println(json.toString());
	}
}