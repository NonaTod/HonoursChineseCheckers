/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechecker;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author nonit
 */
public abstract class MiniMaxSearch {

    /**
     * This attribute counts the number of nodes visited during the search.
     */
    public int nodeVisited = 0;

    /**
     * Perform a minimax search on the current state and player role.
     *
     * @param state The current state.
     * @param role	The role of the player. It must be PlayerRole.MAX or
     * PlayerRole.MIN.
     * @return Return an ActionScorePair. The action attribute is the action to
     * take. The score attribute is the utility value.
     */
    public ActionScorePair search(State state, PlayerRole role) {
        int k = 2;
        this.nodeVisited = 0;			//reset node count to 0

        System.out.println("ROLE: " + role);
        
        if (role == PlayerRole.MAX) //base on the role, call the appropriate method
        {
            System.out.println("IN MAX IF");
            return maxValue(k, state);
        }
        if (role == PlayerRole.MIN) {
            return minValue(k, state);
        }
        return null;
    } //end method

    /**
     * Find the action to take at a max level.
     *
     * @param state The current state.
     * @return An ActionScorePair. The action attribute contains the action to
     * take.
     */
    protected ActionScorePair maxValue(int k, State state) {
        
        this.nodeVisited++;														//increment node count by 1
        if (this.isTerminal(state)) //if current state is a terminal state
        {
            return new ActionScorePair(null, this.utility(state, PlayerRole.MAX));				//return the utility score in an ActionScorePair. But there is no action to take.
        }
        double score = Double.NEGATIVE_INFINITY;									//otherwise need to search children. Set score to -infinity so that any score will be bigger.
        Action action = null;														//initially no action to take

        if (k > 0) {
            List<ActionStatePair> childrenList = state.successor(PlayerRole.MAX);		//generate all successors of a MAX level
            Object[] childrenArray = childrenList.toArray();
//            System.out.println("Children: " + childrenList.toString());
            //convert list of ActionStatePair to an array for easier manipulation

            for (int i = 0; i < childrenArray.length; i++) //loop through all children
            {
                ActionStatePair actionStatePair = (ActionStatePair) childrenArray[i];	//get a child
                State child = (State) actionStatePair.state;

                double childScore = minValue(k, child).score;

                if (score < childScore) //this child has a higher score. Good!
                {
                    score = childScore;												//update score to higher children score
                    action = actionStatePair.action;									//update action to higher score action
                }
                //otherwise, update alpha and continue with other children
            }
        } else {
            List<ActionStatePair> childrenList = state.successor(PlayerRole.MAX);		//generate all successors of a MAX level
            Object[] childrenArray = childrenList.toArray();							//convert list of ActionStatePair to an array for easier manipulation
//            System.out.println("Children: " + childrenList.toString());

            for (int i = 0; i < childrenArray.length; i++) //loop through all children
            {
                ActionStatePair actionStatePair = (ActionStatePair) childrenArray[i];	//get a child
                State child = (State) actionStatePair.state;

                double childScore = utility(child, PlayerRole.MIN);

                if (score < childScore) //this child has a higher score. Good!
                {
                    score = childScore;												//update score to higher children score
                    action = actionStatePair.action;									//update action to higher score action
                }

            }
        }
        k--;
        return new ActionScorePair(action, score);								//return ActionScorePair with the max action and score
    } //end method

    /**
     * Find the action to take at a min level.
     *
     * @param state The current state.
     * @return An ActionScorePair. The action attribute contains the action to
     * take.
     */
    protected ActionScorePair minValue(int k, State state) {
        this.nodeVisited++;														//increment node count by 1
        if (this.isTerminal(state)) //check for terminal state
        {
            return new ActionScorePair(null, this.utility(state, PlayerRole.MIN));				//if it is a terminal, return score, no action needed
        }
        double score = Double.POSITIVE_INFINITY;									//otherwise prepare to search
        Action action = null;

        if (k > 0) {
            List<ActionStatePair> childrenList = state.successor(PlayerRole.MIN);		//find all successors of a MIN level
            Object[] childrenArray = childrenList.toArray();

            for (int i = 0; i < childrenArray.length; i++) {
                ActionStatePair actionStatePair = (ActionStatePair) childrenArray[i];
                State child = (State) actionStatePair.state;
                double childScore = maxValue(k, child).score;							//recursively find max score from this child state
                if (score > childScore) //child has a lower score. good!
                {
                    score = childScore;
                    action = actionStatePair.action;
                }
            }
        } else {
            List<ActionStatePair> childrenList = state.successor(PlayerRole.MIN);		//find all successors of a MIN level
            Object[] childrenArray = childrenList.toArray();
            for (int i = 0; i < childrenArray.length; i++) {
                ActionStatePair actionStatePair = (ActionStatePair) childrenArray[i];
                State child = (State) actionStatePair.state;
                double childScore = utility(child, PlayerRole.MAX);							//recursively find max score from this child state
                if (score > childScore) //child has a lower score. good!
                {
                    score = childScore;
                    action = actionStatePair.action;
                }
                //otherwise update beta, continue with remaining children
            }

        }
        k--;
        return new ActionScorePair(action, score);								//return action and score
    } //end method

    /**
     * Check if a state is terminal. This method is domain-dependent and must be
     * implemented by a class that extends the MiniMaxSearch abstract class.
     *
     * @param state The state to check.
     * @return true if it is a terminal state. false otherwise.
     */
    public abstract boolean isTerminal(State state);

    /**
     * Find the utility score of a state.This method is domain-dependent and
     * must be implemented by a class that extends the MiniMaxSearch abstract
     * class.
     *
     * @param state The state.
     * @param role The player role
     * @return The utility score.
     */
    public abstract double utility(State state, PlayerRole role);
} //end class
