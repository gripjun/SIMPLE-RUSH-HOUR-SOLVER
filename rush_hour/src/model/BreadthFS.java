package model;

import java.util.HashMap;
import java.util.LinkedList;
import controller.Main;

/**
 * @author mathew3
 */
public class BreadthFS {
	
	public static State bfs(State initial, int counter) {
		
		Main.visitedStates = new HashMap<String, Boolean>();

		LinkedList<State> queue = new LinkedList<State>();
		queue.add(initial);
		
		while (!queue.isEmpty()) {

			State temp = queue.poll();

			if (temp.isFinal())
				return temp;
			
			for (State state : State.generateAvailableStates(temp)) {
				state.setParent(temp);
				queue.add(state);
			}
		}

		return null;
	}

}
