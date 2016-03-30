package model;

/**
 * @author mathew3
 */
public class Path {
	
	private StringBuilder path = new StringBuilder();
	private int totalCost, totalMoves;
	
	public Path(StringBuilder path, int cost, int moves) {
		this.path = path;
		this.totalCost = cost;
		this.totalMoves = moves;
	}
	
	public StringBuilder getPath() {
		return path;
	}

	public void setPath(StringBuilder path) {
		this.path = path;
	}

	public int getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(int totalCost) {
		this.totalCost = totalCost;
	}

	public int getTotalMoves() {
		return totalMoves;
	}

	public void setTotalMoves(int totalMoves) {
		this.totalMoves = totalMoves;
	}
	
	/**
	 * backtracking path from final to initial state
	 * @param finalState
	 * @return object path, which contains all relevant data
	 */
	public static Path backtrackPath(State finalState) {
		
		StringBuilder path = new StringBuilder();
		State temp = finalState.createCopy();
		int cost = 0, op = 0;
		
		while (temp != null) {
			if (temp.getMoveMade() != null) {
				op++;
				path.insert(0, temp.getMoveMade() + "\n");
				cost += temp.getMoveMade().toString().replaceAll("[^0-9]", "").length() > 0 ? Integer.parseInt(temp.getMoveMade().toString().replaceAll("[^0-9]", "")) : 0;
			}
			temp = temp.getParent();
		}
		
		return new Path(path, cost, op);
	}
	

}
