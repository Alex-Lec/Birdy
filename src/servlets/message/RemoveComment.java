package servlets.message;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;
import org.json.JSONObject;

@SuppressWarnings("serial")
public class RemoveComment extends HttpServlet {
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String key_session = request.getParameter("key_session");
		ObjectId objectId_message = new ObjectId(request.getParameter("objectId_message"));
		ObjectId objectId_comment = new ObjectId(request.getParameter("objectId_comment"));
		
		JSONObject json = services.MessageServices.removeComment(key_session, objectId_message, objectId_comment);
		
		response.setContentType("text/json");
		response.getWriter().println(json.toString());
	}
}