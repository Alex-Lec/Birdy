package test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileTest {
	public static void main(String args[]) {
		JSONObject json = new JSONObject();
		//StringBuilder sb = new StringBuilder();
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			System.out.println(tools.UserTools.isLoginExist("Alex"));
			int id = tools.UserTools.getId("Alex").getInt("id_user");
			System.out.println(id);
			String query = "SELECT * FROM user WHERE id_user = '" + id + "'";
			ResultSet res = stmt.executeQuery(query);
			res.next();
			/*
			sb.append(res.getInt(1) + "\n");
			sb.append(res.getString(2) + "\n");
			sb.append(res.getString(3) + "\n");
			sb.append(res.getString(5) + "\n");
			sb.append(res.getString(6) + "\n");
			System.out.println(sb.toString());
			*/
			json.put("id_user", res.getInt(1));
			json.put("login_user", res.getString(2));
			json.put("prenom_user", res.getString(4));
			json.put("nom_user", res.getString(5));
			json.put("mail_user", res.getString(6));
			System.out.println(json.toString());
		} catch(SQLException | JSONException e) {
			e.printStackTrace();
		}
	}
}
