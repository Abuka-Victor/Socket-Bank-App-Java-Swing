package jdbcTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCTest {
	public static void main(String[] args) {
		String url = "jdbc:mysql://127.0.0.1:3306/employees_database";
		try {
//			Establish Connection Object
			Connection conn = DriverManager.getConnection(url, "root", "victorsql");
			
//			Create Statement Object to send to DB
			Statement statement = conn.createStatement();
			
//			Execute statement object
			ResultSet resultSet = statement.executeQuery("SELECT * FROM employees_tbl");
			
//			Process Result
			while (resultSet.next()) {
				System.out.println(resultSet.getString("dept"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
