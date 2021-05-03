/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechecker;

import java.util.*;

public abstract class DFSearch {

    /**
     * This attribute counts the number of nodes visited. This includes the root
     * node and other generated nodes. This value is set to 0 when the
     * {@link SearchProblem} is created. Invoking the
     * {@link SearchProblem#search() search()} method does not reset the value.
     * You can manually reset it if you want.
     */
    public int nodeVisited;

    /**
     * The initial state of the problem.
     */
    protected CCState startState;	//the initial state
//protected Set<State> visitedState;	//keeping the history of visited state, not node!

    /**
     * Create a {@link SearchProblem} instance with an initial state.
     *
     * @param start The initial state of the search problem.
     */
    public DFSearch(CCState start) {
        this.startState = start;
        this.nodeVisited = 0;
    } //end method

    /**
     * Perform a search. The current implementation uses breadth-first search.
     *
     * @return null if no solution is found. Or a {@link Path} object otherwise.
     */
    public DFNode search() throws CloneNotSupportedException {
        List<DFNode> fringe = new LinkedList<DFNode>();     	

        DFNode winningNode = null;
        fringe.add(new DFNode(this.startState, null, null));	
        this.nodeVisited++;

        if (fringe.isEmpty()) 
        {
            return null;		
        }

        DFNode node = fringe.remove(0);		
        
        List<ActionStatePair> childrenNodes = node.state.successor(PlayerRole.DF);	
        addChildrenNodes(fringe, node, childrenNodes);	
        
        
        double score = Double.POSITIVE_INFINITY;
        double minScore = Double.POSITIVE_INFINITY;
        while (!fringe.isEmpty()) {
            CCProblem problem = new CCAlphaBetaProblem();
            DFNode node1 = fringe.remove(0);
            CCState child = node1.state;
            score = problem.utility(child, PlayerRole.DF);

            if (score < minScore) {
                minScore = score;
                winningNode = node1;
            }
        }

        return winningNode;
//            }
//            k--;

    } //end method

    /**
     * Given a list of action-state pairs, add them as children of the current
     * node.
     *
     * @param fringe The fringe of the tree to explore.
     * @param parentNode The parent node of these children.
     * @param children A list of children as action-state pairs.
     */
    protected void addChildrenNodes(List<DFNode> fringe, DFNode parentNode, List<ActionStatePair> children) {
        Object[] childrenArray = children.toArray();

        for (int i = 0; i < childrenArray.length; i++) {
            ActionStatePair actionState = (ActionStatePair) childrenArray[i];
            CCAction action = (CCAction) actionState.action;							//get the action component
            CCState childState = actionState.state;							//get the state component
            DFNode childNode = new DFNode(childState, parentNode, action);	//create new child node
            this.addChild(fringe, childNode);							//add child node to end of fringe
            this.nodeVisited++;
        }
    } //end method

    /**
     * Adding a child {@link Node} into the fringe list. Note that it is a
     * {@link Node}, not a {@link State} that we are adding into the fringe. The
     * first node in the fringe list is the next one to be expanded. The default
     * strategy is adding the child to the end of the fringe, giving a
     * breadth-first behaviour. By overriding this method in a subclass, you can
     * change how the search tree is expanded.
     *
     * In a more sophisticated version, you can sort the fringe list according
     * to a criteria. For example, if you insert the child right before a
     * {@link Node} whose cost is greater than the child, the fringe will be
     * sorted in ascending order of the cost.
     *
     * @param fringe The list of {@link Node} awaiting to be expanded.
     * @param childNode The child {@link Node} to be added to the fringe list.
     */
    protected void addChild(List<DFNode> fringe, DFNode childNode) {
        fringe.add(childNode);
    } //end method

    /**
     * Given a goal Node, construct the Path from the root to this goal by
     * tracing the parentNode pointer/reference in each Node.
     *
     * @param node A Node from which to trace to the root.
     * @return A Path leading from the root to this Node.
     */
    protected Path constructPath(DFNode node) {
        if (node == null) {
            return null;
        }

        Path result = new Path();
        result.cost = node.getCost();
        while (node.parent != null) {
            result.add(0, new ActionStatePair(node.action, node.state));	//add state to the beginning of list
            node = node.parent;
        }
        result.head = node.state;	//now node is the head of the path

        return result;
    } //end method

    /**
     * Test if a state is goal.
     *
     * @param state The goal to test.
     * @return <code>true</code> if <code>state</code> is a goal.
     * <code>false</code> otherwise.
     */
    public abstract boolean isGoal(CCState state);
} //end interface

