/**
 * 
 */
package dao;

import java.util.List;

/**
 * @author ebru-gyte
 * Database Acess Object(DAO) Interface
 *
 */
public interface AnimalDAO {
	
	public static AnimalDAO getInstance() {
		return new DerbyDatabaseAnimalDAO();
	}

	List<Animal> findAll();
	void add(Animal t);
	void deleteOnID(int id);
	void updateOnID(int id, String newName);
}
