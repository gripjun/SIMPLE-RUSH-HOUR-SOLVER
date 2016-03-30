package model;

import java.util.List;

import controller.Main;
import model.Vehicle.Direction;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author mathew3
 */
public class State {

	public static final int mapWIDTH = 6, mapHEIGHT = 6;

	public enum moves {
		UP, DOWN, LEFT, RIGHT
	}

	private State parent;
	private List<Vehicle> vehicles;
	private boolean[][] map;
	private int depth;
	private String moveMade;

	public State(List<Vehicle> vehicles, int depth) {
		if (depth == 0)
			this.parent = null;
		this.vehicles = vehicles;
		this.depth = depth;
		createMap();
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	public void setParent(State parent) {
		this.parent = parent;
	}

	public State getParent() {
		return parent;
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setMoveMade(String move) {
		this.moveMade = move;
	}

	public String getMoveMade() {
		return moveMade;
	}

	public boolean[][] getMap() {
		return map;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	/**
	 * method creates unique string of current state
	 * later it's added to hash table, so we avoid generating duplicate states (that'd lead to infinite loop)
	 * @param state
	 * @return unique string
	 */
	public String createUniqueString(State state) {
		StringBuilder string = new StringBuilder();

		for (Vehicle vehicle : state.getVehicles())
			string.append(vehicle.getInfo());

		return string.toString();
	}

	/**
	 * @return copy of this object
	 */
	public State createCopy() {

		List<Vehicle> newVehicles = new ArrayList<Vehicle>();

		for (Vehicle vehicle : getVehicles())
			newVehicles.add(vehicle.createCopy());

		State newState = new State(newVehicles, getDepth());
		newState.setParent(getParent());
		newState.setMoveMade(getMoveMade());

		return newState;
	}

	/**
	 * creates empty map and add vehicles
	 */
	private void createMap() {
		map = new boolean[mapWIDTH][mapHEIGHT];

		for (int i = 0; i < mapWIDTH; i++)
			for (int j = 0; j < mapHEIGHT; j++)
				map[i][j] = false;

		updateMap(vehicles);
	}
	
	/**
	 * method generates each possible move for each vehicle in current state
	 * @param initial - starting state
	 * @return lsit of all available states
	 */
	public static List<State> generateAvailableStates(State initial) {

		List<State> newStates = new LinkedList<State>();

		for (Vehicle vehicle : initial.getVehicles()) {

			if (vehicle.getDirection() == Direction.HORIZONTAL) {

				// check left
				for (int i = vehicle.getPosition().getColumn() - 1, shift = 1; i >= 0; i--, shift++) {
					if (initial.getMap()[vehicle.getPosition().getRow()][i] == true)
						break;
					addGeneratedStates(initial, vehicle, newStates, shift, moves.LEFT);
				}

				// check right
				for (int i = vehicle.getPosition().getColumn()
						+ vehicle.getLength(), shift = 1; i < mapWIDTH; i++, shift++) {
					if (initial.getMap()[vehicle.getPosition().getRow()][i] == true)
						break;
					addGeneratedStates(initial, vehicle, newStates, shift, moves.RIGHT);
				}

			} else {

				// check up
				for (int i = vehicle.getPosition().getRow() - 1, shift = 1; i >= 0; i--, shift++) {
					if (initial.getMap()[i][vehicle.getPosition().getColumn()] == true)
						break;
					addGeneratedStates(initial, vehicle, newStates, shift, moves.UP);
				}

				// check down
				for (int i = vehicle.getPosition().getRow()
						+ vehicle.getLength(), shift = 1; i < mapHEIGHT; i++, shift++) {
					if (initial.getMap()[i][vehicle.getPosition().getColumn()] == true)
						break;
					addGeneratedStates(initial, vehicle, newStates, shift, moves.DOWN);
				}

			}
		}

		return newStates;
	}

	/**
	 * checks if state is terminal
	 * @return true if it's terminal state - first car is at the edge of crossroad
	 */
	public boolean isFinal() {
		if (vehicles.get(0).getDirection() == Direction.HORIZONTAL
				&& vehicles.get(0).getPosition().getColumn() + vehicles.get(0).getLength() - 1 == (mapWIDTH - 1))
			return true;
		else
			return false;
	}

	public void printMap() {
		for (int i = 0; i < mapWIDTH; i++) {
			for (int j = 0; j < mapHEIGHT; j++)
				System.out.printf("%7s", getMap()[i][j] + " ");
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * updates map  - if there's a vehicle on certain [row][column] position, sets value to true
	 * @param vehicles
	 */
	public void updateMap(List<Vehicle> vehicles) {
		for (Vehicle vehicle : vehicles) {
			
			if (vehicle.getDirection() == Direction.HORIZONTAL)
				for (int i = 0; i < vehicle.getLength(); i++)
					map[vehicle.getPosition().getRow()][vehicle.getPosition().getColumn() + i] = true;
			else
				for (int i = 0; i < vehicle.getLength(); i++)
					map[vehicle.getPosition().getRow() + i][vehicle.getPosition().getColumn()] = true;
			
		}
	}
	
	/*
	 * method creates possible move and checks, if it's already in hash table
	 */
	public static void addGeneratedStates(State initial, Vehicle vehicle, List<State> newStates, int shift,
			moves move) {

		State newState = initial.createCopy();
		Vehicle newVehicle = newState.getVehicles().get(vehicle.getID());

		switch (move) {
		case UP:
			newVehicle.getPosition().setRow(newVehicle.getPosition().getRow() - shift);
			break;
		case DOWN:
			newVehicle.getPosition().setRow(newVehicle.getPosition().getRow() + shift);
			break;
		case LEFT:
			newVehicle.getPosition().setColumn(newVehicle.getPosition().getColumn() - shift);
			break;
		case RIGHT:
			newVehicle.getPosition().setColumn(newVehicle.getPosition().getColumn() + shift);
			break;
		}

		newState.createMap();
		newState.setDepth(initial.getDepth() + 1);
		newState.setMoveMade(move + "(" + newVehicle.getColor() + ", " + shift + ")");

		if (!Main.visitedStates.containsKey(initial.createUniqueString(newState))) {
			Main.visitedStates.put(initial.createUniqueString(newState), true);
			newStates.add(newState);
		}
	}

}
