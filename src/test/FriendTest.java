package test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONException;
import org.json.JSONObject;

public class FriendTest {
	public static void main(String args[]) throws JSONException {
		String query = "SELECT * FROM friendship WHERE id_user_1 = 1";
		JSONObject json = new JSONObject();
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement(); ResultSet res = stmt.executeQuery(query)) {
			int cpt = 1;
			
			while (res.next()) {
				
				//json.put("Friend n°" + cpt++, tools.UserTools.getProfile(res.getInt("id_user_2"), stmt));
				//System.out.println(res.getInt("id_user_1") + " - " + res.getInt("id_user_2") + " - " + res.getTimestamp("date_friendship"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(json.toString());
		
		/*
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			System.out.println(tools.UserTools.getId("user_1", stmt).getInt("id_user"));
		} catch(SQLException e) {
			e.printStackTrace();
		}
		*/
	}
}
