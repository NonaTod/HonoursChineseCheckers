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
public class CCAlphaBetaProblem extends CCProblem {

    /**
     * This updated version of search(...) calls maxValue(...) and minValue(...)
     * with the default values of alpha and beta.
     *
     * @param state The current state of the game.
     * @param role The player's role. i.e. max or min.
     */
    @Override
    public ActionScorePair search(State state, PlayerRole role) {
        int k = 2;
        this.nodeVisited = 0;			//reset node count to 0

        if (role == PlayerRole.MAX) {
            return maxValue(k, state, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        }
        if (role == PlayerRole.MIN) {
            return minValue(k, state, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        }
        return null;
    } //end method

    /**
     * This method find the score and action of a max node with the given alpha
     * and beta value.
     *
     * @param k depth
     * @param state The current state of the game.
     * @param alpha The alpha value.
     * @param beta The beta value.
     * @return The best action as an action-score pair.
     */
    protected ActionScorePair maxValue(int k, State state, double alpha, double beta) {
        k--;
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
                CCState child = actionStatePair.state;

                double childScore = minValue(k, child, alpha, beta).score;
//                System.out.println("CHILD: " + child.toStringByIcons());
//                System.out.println("SCORE: " + childScore);
//                System.out.println("CHILDREN: " + Arrays.toString(childrenArray));
//                System.out.println("LENGTH " + childrenArray.length);
                if (score < childScore) //this child has a higher score. Good!
                {
                    score = childScore;												//update score to higher children score
                    action = actionStatePair.action;									//update action to higher score action
                }
                if (score >= beta) //if score>beta, it will be ignored by min above. Stop wasting time in further children.
                {
                    return new ActionScorePair(action, score);						//just return this score and action
                }
                alpha = Math.max(alpha, score);								//otherwise, update alpha and continue with other children
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
                if (score >= beta) {
                    return new ActionScorePair(action, score);						//just return this score and action
                }
                alpha = Math.max(alpha, score);
            }
        }
        return new ActionScorePair(action, score);								//return ActionScorePair with the max action and score
    } //end method

    /**
     * This method find the score and action of a min node with the given alpha
     * and beta value.
     *
     * @param k
     * @param state The current state of the game.
     * @param alpha The alpha value.
     * @param beta The beta value.
     * @return The best action as an action-score pair.
     */
    protected ActionScorePair minValue(int k, State state, double alpha, double beta) {
        k--;
        this.nodeVisited++;														//increment node count by 1
        if (this.isTerminal(state)) //check for terminal state
        {
//            return new ActionScorePair(null, this.utility(state, PlayerRole.MC));				//if it is a terminal, return score, no action needed
            return new ActionScorePair(null, this.utility(state, PlayerRole.MIN));				//if it is a terminal, return score, no action needed
//            return new ActionScorePair(null, this.utility(state, PlayerRole.DF));				//if it is a terminal, return score, no action needed
        }
        double score = Double.POSITIVE_INFINITY;									//otherwise prepare to search
        Action action = null;

        if (k > 0) {
            List<ActionStatePair> childrenList = state.successor(PlayerRole.MIN);		//find all successors of a MIN level
//            List<ActionStatePair> childrenList = state.successor(PlayerRole.MC);		//find all successors of a MIN level
//            List<ActionStatePair> childrenList = state.successor(PlayerRole.DF);		//find all successors of a MIN level
            Object[] childrenArray = childrenList.toArray();

            for (int i = 0; i < childrenArray.length; i++) {
                ActionStatePair actionStatePair = (ActionStatePair) childrenArray[i];
                State child = (State) actionStatePair.state;
                double childScore = maxValue(k, child, alpha, beta).score;							//recursively find max score from this child state
                if (score > childScore) //child has a lower score. good!
                {
                    score = childScore;
                    action = actionStatePair.action;
                }
                if (score <= alpha) {
                    return new ActionScorePair(action, score);						//return action and score so far
                }
                beta = Math.min(beta, score);								//otherwise update beta, continue with remaining children
            }
        } else {
            List<ActionStatePair> childrenList = state.successor(PlayerRole.MIN);		//find all successors of a MIN level
//            List<ActionStatePair> childrenList = state.successor(PlayerRole.MC);		//find all successors of a MIN level
//            List<ActionStatePair> childrenList = state.successor(PlayerRole.DF);		//find all successors of a MIN level
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
                if (score <= alpha) //if score<=alpha, it will be ignored by max above. Stop wasting time.
                {
                    return new ActionScorePair(action, score);						//return action and score so far
                }
                beta = Math.min(beta, score);								//otherwise update beta, continue with remaining children
            }

        }
        return new ActionScorePair(action, score);								//return action and score
    } //end method
} //end class
