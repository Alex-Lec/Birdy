package tools;

import org.json.JSONException;
import org.json.JSONObject;

public class ErrorJSON {
	public static JSONObject serviceRefused(String message, int codeErreur) {
		try {
			return new JSONObject().put(message, codeErreur);
		} catch(JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static JSONObject serviceAccepted() {
		try {
			return new JSONObject().put("OK", 1);
		} catch(JSONException e) {
			return serviceRefused("Erreur JSON", 100);
		}
	}
}