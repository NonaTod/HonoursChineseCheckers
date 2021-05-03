package chinesechecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.nio.cs.ext.GBK;

public class PudgeAgent extends MCProblem {

    private double EPSILON = 0.25;
    private double EXPLORE_FACTOR = 100;
    private int DELAY = 9500;
    private int DEPTH = 13;
    private int EXPAND_THRESHOLD = 100;
    private int move_c;
    private double W = 0.1;
    boolean DEBUG = true;
    Random rnd = new Random();

    List<Action> possible_moves = new ArrayList<Action>();
    List<Node> game_tree = new ArrayList<Node>();
    Map<Action, Double> score_history = new HashMap<Action, Double>();
    Map<Action, Integer> play_history = new HashMap<Action, Integer>();

    public PudgeAgent() {
        super("MC Player");
        move_c = 1;
    }

    // High level function for computing the best move
    protected Action nextMove(CCState state) {
        game_tree.clear();
        game_tree.add(new Node(0, 0, null, current_player));
        expand(0, state);
        Action a = null;
        boolean over = false;
        System.err.println("Finding Move " + move_c + "...");
        score_history.clear();
        play_history.clear();
        MoveTimer t = new MoveTimer(DELAY);
        t.start();
        while (!t.isDone() && !over) {
            MoveReturn mr = null;
            try {
                mr = getBestMove();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(PudgeAgent.class.getName()).log(Level.SEVERE, null, ex);
            }
            a = mr.a;
            over = mr.gg;
        }
        if (DEBUG) {
            System.err.println("Total samples " + game_tree.get(0).visits);
            System.err.println("Best move: " + a);
            int start = game_tree.get(0).child_start;
            int end = start + game_tree.get(0).num_children;
            for (int j = start; j < end; j++) {
                Node n = game_tree.get(j);
            }
        }
        move_c++;
        return a;
    }

    // Adds 1 to move's win history, creates key if it doesn't exist
    private void addScore(Action a, double score) {
        if (score_history.containsKey(a)) {
            score_history.put(a, score_history.get(a) + score);
        } else {
            score_history.put(a, score);
        }
    }

    // Adds 1 to move's play history, creates key if it doesn't exist
    private void addPlay(Action a) {
        if (play_history.containsKey(a)) {
            play_history.put(a, play_history.get(a) + 1);
        } else {
            play_history.put(a, 1);
        }
    }

    // Gets move's win value, returns 0 if no value found
    private double getScore(Action a) {
        Double j = score_history.get(a);
        if (j == null) {
            return 0.0;
        } else {
            return j;
        }
    }

    // Gets number of times a move was played, returns 1 if no value found
    private int getPlay(Action a) {
        Integer j = play_history.get(a);
        if (j == null) {
            return 1;
        } else {
            return j;
        }
    }

    List<Integer> visited = new ArrayList<Integer>();
    List<Action> child_moves = new ArrayList<Action>();

    private class MoveReturn {

        public boolean gg;
        public Action a;

        public MoveReturn(Action a, boolean gg) {
            this.gg = gg;
            this.a = a;
        }
    }

    // High level function for computing the best move
    private MoveReturn getBestMove() throws CloneNotSupportedException {

        //Creating a copy of the state
        CCState t_state = (CCState) state.copyState();

        possible_moves.clear();
        List<ActionStatePair> moves = t_state.successor(PlayerRole.MC);

        //Add the ACTIONSTATE pairs to the possible_moves array
        for (ActionStatePair a : moves) {
            possible_moves.add(a.action);
        }

        //Apply the action to the state
        for (Action a : possible_moves) {
            CCState tempState = (CCState) t_state.copyState();
            tempState.applyAction(a);

            if (tempState.winner() == state.currPlayer.role.MC) {
                return new MoveReturn(a, true);
            }
        }

        // Selection
        visited.clear();
        int i = 0;
        visited.add(i);
        while (!isLeaf(i)) {
            i = selectBestChild(i);
            visited.add(i);
            t_state.applyAction(game_tree.get(i).last_move);
        }
        // Expansion
        possible_moves.clear();
        List<ActionStatePair> moves2 = t_state.successor(PlayerRole.MC);

        //Add the ACTIONSTATE pairs to the possible_moves array
        for (ActionStatePair a : moves2) {
            possible_moves.add(a.action);
        }

        double value = Double.NaN;

        if (!possible_moves.isEmpty()) {
            // get list of moves we've already expanded
            child_moves.clear();
            for (int j = game_tree.get(i).child_start; j < game_tree.get(i).num_children; j++) {
                child_moves.add(game_tree.get(j).last_move);
            }
            // if we have unseen moves
            if (possible_moves.size() > child_moves.size()) {
                possible_moves.removeAll(child_moves);
                addPlay(possible_moves.get(0));
                t_state.applyAction(possible_moves.get(0));
                value = doPlayout(t_state);
                // expand if we've seen this move enough
                if (getPlay(possible_moves.get(0)) > EXPAND_THRESHOLD) {
                    singleExpand(i, possible_moves.get(0), t_state, value);
                }
            } else {
                int best = selectBestChild(i);
                t_state.applyAction(game_tree.get(best).last_move);
                visited.add(best);
                value = doPlayout(t_state);
            }
        } else {
            value = doPlayout(t_state);
        }
        // Back propagation
        for (int j : visited) {
            updateValue(j, value);
        }
//        System.out.println("GAME TREE Size " + game_tree.size());
//        System.out.println("GAME TREE  " + game_tree.get(selectBestChild(0)));
//        System.out.println("GAME TREE 2 is  " + game_tree.get(selectBestChild(0)).last_move);
        return new MoveReturn(game_tree.get(selectBestChild(0)).last_move, false);
    }

    // Expand the designated node with a node for m
    private void singleExpand(int node, Action a, CCState s, double value) {
        game_tree.add(new Node(1, value, a, s.currPlayer));
    }

    // Expand the designated node and add its children to the tree
    private void expand(int node, CCState s) {
        if (!isLeaf(node)) {
            System.out.println("PROBLEM: INTERNAL NODE EXPANDED");
        }
        possible_moves.clear();
        List<ActionStatePair> moves = s.successor(PlayerRole.MC);
        
//        System.out.println("Successors " + moves.toString() );
        for (ActionStatePair a : moves) {
            possible_moves.add(a.action);
        }

        n = game_tree.get(node);
        n.child_start = game_tree.size();
        n.num_children = possible_moves.size();
//        System.out.println("Possible moves size is " + n.num_children);
        for (Action a : possible_moves) {
//            System.out.println("Action 1 added is:" + a.toString());

            game_tree.add(new Node(0, 0, a, s.currPlayer));
//            System.out.println("Action added is:" + a.toString());
        }
    }

    // Update the value of a node with the score, negative for the other 
    private void updateValue(int node, double score) {
        Node n = game_tree.get(node);
        if (my_player == n.player) {
            n.value += score;
        } else {
            n.value -= score;
        }
        n.visits++;
    }

    // Use the our modified UCB rule to find the best child
    private int selectBestChild(int node) {
        int start = game_tree.get(node).child_start;
        int end = start + game_tree.get(node).num_children;
        int selected = -1;
        double ucb_value;
        double best_value = -Double.MAX_VALUE;
        for (int i = start; i < end; i++) {
            ucb_value = getUCBProgressiveVal(i, node);
            if (ucb_value > best_value) {
                best_value = ucb_value;
                selected = i;
            }
        }
        return selected;
    }

    Node n, p;
    // Get the modified UCB value of a given node
    double sa;

    private double getUCBProgressiveVal(int node, int parent) {
        n = game_tree.get(node);
        p = game_tree.get(parent);

        double v = (n.value / n.visits)
                + (EXPLORE_FACTOR * (Math.sqrt(Math.log(p.visits) / n.visits)))
                + ((getScore(n.last_move) / getPlay(n.last_move))
                * (W / (n.visits * 109 - n.value + 1)));

        if (Double.isNaN(v)) {
            // Unvisited nodes come first
            return Double.MAX_VALUE;
        } else {
            //System.err.println(v);
            return v;
        }
    }

    // Is the designated node a leaf
    private boolean isLeaf(int node) {
        return game_tree.get(node).num_children == 0;
    }

    // play out the game, returning the 1 for win 0 for loss
    private double doPlayout(CCState s) throws CloneNotSupportedException {
        Action a = null;
        int d = 0;
        CCState r = (CCState) s.copyState();
        while (d < DEPTH && !r.isGameOverLong()) {
            d++;
            a = nextEpsilonGreedyMove(r, EPSILON, false);
            if (a != null) {
                r.applyAction(a);
            } else {
                a = nextEpsilonGreedyMove(r, EPSILON, true);
            }
        }
        // get score for side we're on
        double multi = r.currPlayer == s.currPlayer ? -1.0 : 1.0;
        double score = distanceEvaluate(r);
        // update heuristic knowledge
        if (score > 0) {
            addScore(a, score);
        }
        addPlay(a);
        return score * multi;
    }

    private Action nextEpsilonGreedyMove(CCState s, double epsilon, boolean all_moves) {
        possible_moves.clear();
        if (all_moves) {
            List<ActionStatePair> moves = s.successor(PlayerRole.MC);
            for (ActionStatePair a : moves) {
                possible_moves.add(a.action);
            }
        } else {
            List<ActionStatePair> moves = s.successor(PlayerRole.MC);

            for (ActionStatePair a : moves) {
                possible_moves.add(a.action);
            }
        }
        if (possible_moves.isEmpty()) {
            return null;
        }
        double e = rnd.nextDouble();
        if (e < epsilon) {
            return possible_moves.get(rnd.nextInt(possible_moves.size()));
        } else {
            return possible_moves.get(0);
        }
    }

    public double distanceEvaluate(CCState s) {
        int score = 0;
        for (int x = 0; x < s.gb.length; x++) {
            for (int y = 0; y < s.gb[x].length; y++) {
                if (s.gb[x][y] != null) {
                    if (s.gb[x][y].isMC) {
                        score += (Math.abs(12 - x)) * 100;
                        score += (y + 1) * 50;
                    } else {
                        score += 20;
                    }
                }
            }
        }
        return score;
    }

}
