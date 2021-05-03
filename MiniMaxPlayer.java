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


    public MiniMaxPlayer(String name, Colour colour, PlayerRole role) {
        super(name, colour, role);
    }

    
    @Override
    public Action getAction(MiniMaxSearch problem, State state) {
        System.out.println(this.name + "'s turn.");
        
        ActionScorePair result = problem.search(state, this.role);
//        ActionScorePair result = problem.search(state, PlayerRole.MAX);
        if (result == null) 
        {
            return null;
        }
        result.action.nodes = problem.nodeVisited;
        System.out.println("Node visited: " + problem.nodeVisited);	
        System.out.println("Action score: " + result.score);
        return result.action;										
    } //end method
    
} //end class

