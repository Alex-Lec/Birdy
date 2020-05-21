package test;

import javax.naming.*;
import javax.sql.*;
import java.sql.*;

public class DBTest {
	
	
	public static void init() {
		try {
			Context ctx = new InitialContext();
			if (ctx == null) throw new Exception("Boom");
			DataSource ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/bd");
			if (ds != null) {
				Connection conn = ds.getConnection();
				if (conn != null) {
					Statement stmt = conn.createStatement();
					ResultSet rst = stmt.executeQuery("SELECT * FROM user");
					if (rst.next()) {
						System.out.println("OK !");
					}
					conn.close();
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}