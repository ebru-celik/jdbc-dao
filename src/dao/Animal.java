package dao;

public class Animal {

	private int id;
	private String name;
	
	public Animal(String name) {
		this.name = name;
	}
	
	public Animal(int id, String name) {
		this.id = id;
		this.name = name;
	}

	
	public void setID(int id) {
		this.id = id;
	}

	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}
	
}
