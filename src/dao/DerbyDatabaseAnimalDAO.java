package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * A concrete implementation of the DAO interface
 *
 */
public class DerbyDatabaseAnimalDAO implements AnimalDAO {
	
	//constructor
	public DerbyDatabaseAnimalDAO() {
		
		JDBC_Utils.rebuildTable();
		JDBC_Utils.printTable();
		
	}
	
	@Override
	public List<Animal> findAll() {
		
		List<Animal> list = new ArrayList<>();
		try(Connection connection = JDBC_Utils.createConnection();
			Statement stm = connection.createStatement();
			ResultSet res = stm.executeQuery("select * from zoo")) {

			while(res.next()) {
				int id = res.getInt(1); // columnIndex as a parameter
				String name = res.getString(2);
				
				list.add(new Animal(id, name));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public void add(Animal animal) {
		try(Connection connection = JDBC_Utils.createConnection();
			Statement stm = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
			ResultSet.CONCUR_UPDATABLE);
			ResultSet res = stm.executeQuery("select * from zoo")) {
		
//			res.last();
//		    int id = res.getInt("id")+1;
//		    animal.setID(id);
		    
			res.moveToInsertRow();
			
			//res.updateInt(1, animal.getID()); // id
			res.updateString(2, animal.getName()); 
			res.insertRow();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		//JDBC_Utils.printTable();
	}

	@Override
	public void deleteOnID(int id) {
		try(Connection connection = JDBC_Utils.createConnection();
			Statement stm = connection.createStatement()) {

			int rows = stm.executeUpdate("delete from zoo where id=" + id);
			System.out.println("Number of deleted rows: " + rows); 
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void updateOnID(int id, String newName) {
		try(Connection connection = JDBC_Utils.createConnection();
				Statement stm = connection.createStatement()) {

				int rows = stm.executeUpdate("update zoo set name='"+newName+"' where id=" + id);
				
				System.out.println("Number of updated rows: " + rows); 
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

}
