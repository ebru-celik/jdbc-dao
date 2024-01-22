package dao;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ZooApp {

	public static void main(String[] args) {
		
		AnimalDAO dao = AnimalDAO.getInstance();
		
		loops:
			while(true) {
				
				System.out.println();
				System.out.println("0. exit the App");
				System.out.println("1. show all animals");
				System.out.println("2. add animal");
				System.out.println("3. delete animal");
				System.out.println("4. update animal");
				
				int userWahl = getUserInputAsInt("Your Choice: ");
				switch(userWahl) {
					case 0:
						break loops;
					case 1:
						printAllAnimals(dao);
						break;
					case 2:
						insertAnimal(dao);
						break;
					case 3:
						deleteAnimal(dao);
						break;
					case 4:
						updateAnimal(dao);
						break;
					default:
						System.out.println("invalid Choice: " + userWahl);
						System.out.println("Please enter a number between 1 and 4 : ");
						break;
				}
			}

	} // end main
	
	static void updateAnimal(AnimalDAO dao) {
		int id = getUserInputAsInt("Enter ID of animal to update: ");
		String name = getUserInput("Enter new animal name: ");
		dao.updateOnID(id, name);
	}
	
	static void deleteAnimal(AnimalDAO dao) {
		int id = getUserInputAsInt("Enter ID of animal to delete: ");
		dao.deleteOnID(id);
	}
	
	static void insertAnimal(AnimalDAO dao) {
		String name = getUserInput("What is the name of the animal?: ");
		dao.add(new Animal(name));
	}
	
	static void printAllAnimals(AnimalDAO dao) {
		List<Animal> animalList = dao.findAll();
		String fmt = "|%3s|%8s|%n";
		
		System.out.printf(fmt, "id", "name");
		
		for (Animal animal : animalList) {
			System.out.printf(fmt, animal.getID(), animal.getName());
		}
	}
	
	@SuppressWarnings("resource")
	private static String getUserInput(String eingabeaufforderung) {
		System.out.print(eingabeaufforderung);
		return new Scanner(System.in).nextLine();
	}

	@SuppressWarnings("resource")
	private static int getUserInputAsInt(String eingabeaufforderung) {
		while(true) {
			System.out.print(eingabeaufforderung);
			try {
				return new Scanner(System.in).nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Bitte eine Zahl eingeben!");
			}
		}
	}

}
