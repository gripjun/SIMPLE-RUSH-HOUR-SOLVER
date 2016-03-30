package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author mathew3
 */
public class InputReader {

	@SuppressWarnings("resource")
	public static List<Vehicle> readInput() {
		return processInput(new Scanner(System.in).nextLine().split("[ ()]+"));
	}

	/**
	 * processing input
	 * input format: ((color length row column direction)(...)(...)), eg. ((red 2 2 2 h))
	 * row & column positions are indexed from 1 to 6 !!
	 * @param tokens - input from console
	 * @return list of vehicles
	 */
	public static List<Vehicle> processInput(String[] tokens) {
		
		int id = 0;
		List<Vehicle> vehicles = new ArrayList<Vehicle>();

		for (int i = 0; i < tokens.length; i++)
			if (tokens[i].isEmpty() == false && i % 5 == 1) {
				vehicles.add(new Vehicle(id, new String(tokens[i]), Integer.parseInt(tokens[i + 1]),
						Integer.parseInt(tokens[i + 2]) - 1, Integer.parseInt(tokens[i + 3]) - 1,
						tokens[i + 4].charAt(0)));
				id++;
			}
		
		return vehicles;
	}

}
