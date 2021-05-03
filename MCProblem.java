package chinesechecker;

import java.util.ArrayList;
import java.util.Scanner;

// Base class for game agent (reused over multiple Chinese checkers assignments)
public abstract class MCProblem {

    protected Move opp_last_move = null;
    protected Player current_player;
    protected Player my_player;
    protected String name;
    protected String opp_name;
    protected Scanner stdin;
    protected CCState state = new CCState();

    public MCProblem(String p_name) {
        name = p_name;
        stdin = new Scanner(System.in);
    }

    protected abstract Action nextMove(CCState state);

    public void playGame() throws CloneNotSupportedException {
        // Identify myself
        System.out.println("#name " + name);
        System.out.flush();

        // Main game loop
        for (;;) {

            if (current_player == my_player) {
                // My turn

                // Check if game is over
                if (state.isGameOverLong()) {
                    System.err.println("I, " + name + ", have lost");
                    System.err.flush();
                    continue;
                }

                // Determine next move
                Action a = nextMove(state);

                // Apply it locally
                state.applyAction(a);

            }
        }
    }

    // Sends a msg to stdout and verifies that the next message to come in is it
    // echoed back. This is how the server validates moves
}
