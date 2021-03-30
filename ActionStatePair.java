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
public class ActionStatePair {

    /**
     * The action part of this pair.
     */
    public Action action;
    /**
     * The state after the action is applied to the current state.
     */
    public State state;

    /**
     * Creates an action-state object.
     *
     * @param action The action component of the pair.
     * @param state The state component of the pair.
     */
    public ActionStatePair(Action action, State state) {
        this.action = action;
        this.state = state;
    } //end method

    /**
     * This convenient method converts an action-state pair into a String (for
     * printing?).
     */
    public String toString() {
        return action.toString() + "\n" + state.toString();
    } //end method
} //end class
