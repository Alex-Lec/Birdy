package servlets.message;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;
import org.json.JSONObject;

@SuppressWarnings("serial")
public class RemoveLike extends HttpServlet {
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String key_session = request.getParameter("key_session");
		ObjectId objectId = new ObjectId(request.getParameter("objectId"));
		
		JSONObject json = services.MessageServices.removeLike(key_session, objectId);
		
		response.setContentType("text/json");
		response.getWriter().println(json.toString());
	}
}