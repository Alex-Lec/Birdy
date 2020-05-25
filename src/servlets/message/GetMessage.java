package servlets.message;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;
import org.json.JSONObject;

@SuppressWarnings("serial")
public class GetMessage extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectId objectId = new ObjectId(request.getParameter("objectId"));
		
		JSONObject json = services.MessageServices.getMessage(objectId);
		
		response.setContentType("text/json");
		response.getWriter().println(json.toString());
	}
}