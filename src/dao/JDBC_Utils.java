package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author ebru-gyte
 * 
 * For Database Connection I used Apache Derby.
 * Apache Derby has an embedded JDBC Driver
 * The Derby database engine runs inside the same Java Virtual Machine (JVM) as the application. 
 * So, Derby becomes part of the application just like any other jar file that the application uses.
 *
 */
public class JDBC_Utils {
	/**
	 * 
	 * @return the opened connection url
	 * @throws SQLException  if a database access error occurs or the url is null
	 */
	public static Connection createConnection() throws SQLException {
		String url = "jdbc:derby:zooDB;create=true";
		return DriverManager.getConnection(url);
	}
	
	/**
	 * rebuild Table zoo
	 */
	public static void rebuildTable() {
		
		try(Connection c = createConnection();
				Statement stm = c.createStatement()){ //try-with-resources
			
			/*
			 * delete table zoo
			 */
			try {
				stm.executeUpdate("drop table zoo");
				System.out.println("*** Tabel 'zoo' is deleted");
			} catch (SQLException e) {
				if(e.getSQLState().equals("42Y55")) {
					System.out.println("*** Table 'zoo' not found and therefore not deleted");
				} else {
					throw e;
				}
			}// end catch
			
			/*
			 * create table zoo
			 * Auto-increment id
			 */
			String sql = "create table zoo "
					+ "(id int not null primary key GENERATED ALWAYS AS IDENTITY,"
					+ "name varchar(255))";
			stm.executeUpdate(sql);
			
			/*
			 * insert basic records in the table
			 */
			sql = "insert into zoo (name) values (?)";
			try( PreparedStatement pstm = c.prepareStatement(sql) ) {
				
				// id is auto-increment
				
				int paramIndex = 1; // the first question mark in the sql command
				pstm.setString(paramIndex, "Kuh");
				pstm.executeUpdate();
				
				pstm.setString(1, "Giraffe");
				pstm.executeUpdate();
				
				pstm.setString(1, "Elephant");
				pstm.executeUpdate();
			}
			
			System.out.println("*** Table 'zoo' created");
			
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}// end rebuildTable() Method
	
	/**
	 * print Table zoo
	 */
	public static void printTable() {
		try(Connection c = createConnection();
				Statement stm = c.createStatement();
					ResultSet res = stm.executeQuery("select * from zoo")) { //try-with-resources
			
			String fmt = "|%3s|%8s|%n";
			
			System.out.printf(fmt, "id", "name");
			
			while(res.next()) {
				
				int id = res.getInt(1); // columnIndex as a parameter
				String name = res.getString(2);
				
				System.out.printf(fmt, id, name);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
