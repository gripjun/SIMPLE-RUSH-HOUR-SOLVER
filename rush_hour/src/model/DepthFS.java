package model;

import java.util.HashMap;

import controller.Main;

/**
 * @author mathew3
 */
public class DepthFS {
	
	public static State dfs(State initial, int counter) {
		
		if (counter == -1) Main.visitedStates = new HashMap<String, Boolean>();
		
		counter++;
		
		if (counter > 1000)
			return null;

		if (initial.isFinal())
			return initial;

		for (State state : State.generateAvailableStates(initial)) {

			state.setParent(initial);
			State finalState = dfs(state, counter);

			if (finalState != null)
				return finalState;
		}

		return null;
	}

}
