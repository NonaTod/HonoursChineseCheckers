/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechecker;

import javax.swing.ImageIcon;

/**
 *
 * @author nonit
 */
public class MiniMaxPlayer extends Player {

    MiniMaxPlayerRole role;

    public MiniMaxPlayer(String name, Colour colour, MiniMaxPlayerRole role) {
        super(name, colour);
        this.role = role;
    }

    public MiniMaxPlayerRole getRole() {
        return role;
    }

    public void setRole(MiniMaxPlayerRole role) {
        this.role = role;
    }

    public Action getAction(MiniMaxSearch problem, State state) {
        System.out.println(this.name + "'s turn.");
        ActionScorePair result = problem.search(state, this.role);		//find a move for the current state and role
        if (result == null) //this should not happen. Just for safety.
        {
            return null;
        }
        System.out.println("Node visited: " + problem.nodeVisited);	//print some statistics in making this move
        System.out.println("Action score: " + result.score);
        return result.action;										//return the action decided
    } //end method
    
} //end class

