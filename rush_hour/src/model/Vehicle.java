package model;

/**
 * @author mathew3
 */
public class Vehicle {
	
	public enum Direction {
		VERTICAL, HORIZONTAL
	}

	private int id, length;
	private String color;
	private Position position;
	private Direction direction;

	public Vehicle(int id, String color, int length, int row, int column, char direction) {
		this.id = id;
		this.color = color;
		this.length = length;
		setPosition(row, column);
		setDirection(direction);
	}

	public void setID(int id) {
		this.id = id;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getID() {
		return this.id;
	}

	public String getColor() {
		return this.color;
	}

	public int getLength() {
		return this.length;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(int row, int column) {
		position = new Position(row, column);
	}

	public void setDirection(char direction) {
		if (direction == 'h')
			this.direction = Direction.HORIZONTAL;
		else
			this.direction = Direction.VERTICAL;
	}

	public Direction getDirection() {
		return direction;
	}

	public String getInfo() {
		return (getID() + " " + getColor() + " " + getLength() + " " + position.getRow() + " " + position.getColumn() + " " + getDirection());
	}

	public Vehicle createCopy() {
		char dir = this.direction == Direction.HORIZONTAL ? 'h' : 'v';
		return (new Vehicle(id, color, length, position.getRow(), position.getColumn(), dir));
	}

}
