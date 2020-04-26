package servlets.friends;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@SuppressWarnings("serial")
public class ListFriend extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login_user = request.getParameter("login_user");

		JSONObject json = services.FriendsServices.getListFriend(login_user);
		
		response.setContentType("text/json");
		response.getWriter().println(json.toString());
	}
}