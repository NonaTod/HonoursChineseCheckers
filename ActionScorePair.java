/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechecker;

/**
 *
 * @author nonit
 */

public class ActionScorePair
{
/**
 * The action.
 */
public Action action; 

/**
 * The score of the state that the action is leading to.
 */
public double score;

/**
 * Create an action-score pair.
 * @param action	The action.
 * @param score	The value of the node.
 */
public ActionScorePair(Action action,double score)
{
this.action=action;
this.score=score;
} //end method

    @Override
    public String toString() {
        return "ActionScorePair{" + "action=" + action.toString() + ", score=" + score + '}';
    }


} //end class
