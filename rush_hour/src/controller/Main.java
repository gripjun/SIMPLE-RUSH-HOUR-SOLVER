package controller;

import java.util.Map;

import model.BreadthFS;
import model.DepthFS;
import model.InputReader;
import model.Path;
import model.State;

/**
 * @author mathew3
 */
public class Main {

	public static Map<String, Boolean> visitedStates;

	public static void main(String[] args) {

		State startingState = new State(InputReader.readInput(), 0);

		// DFS SOLUTION
		System.out.println("---> DFS SOLUTION:");
		printResult(DepthFS.dfs(startingState, -1));

		// BFS SOLUTION
		System.out.println("---> BFS SOLUTION:");
		printResult(BreadthFS.bfs(startingState, -1));

	}

	static void printResult(State state) {
		if (state != null) {
			Path path = Path.backtrackPath(state);

			System.out.println("--- SOLUTION FOUND ---");
			System.out.println("--- CURRENT DEPTH: " + state.getDepth());
			System.out.println("--- TOTAL MOVES: " + path.getTotalMoves());
			System.out.println("--- TOTAL COST: " + path.getTotalCost());
			System.out.println("--- PATH:\n");
			System.out.println(path.getPath());
		} else {
			System.out.println("--- NO SOLUTION :(");
		}
	}

}