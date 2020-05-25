package servlets.message;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;
import org.json.JSONObject;

@SuppressWarnings("serial")
public class EditMessage extends HttpServlet {
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String key_session = request.getParameter("key_session");
		ObjectId objectId = new ObjectId(request.getParameter("objectId"));
		String text = request.getParameter("text");
		
		JSONObject json = services.MessageServices.editMessage(key_session, objectId, text);
		
		response.setContentType("text/json");
		response.getWriter().println(json.toString());
	}
}