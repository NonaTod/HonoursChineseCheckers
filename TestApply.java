/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechecker;

import chinesechecker.*;
import java.util.ArrayList;

/**
 *
 * @author nonit
 */
public class TestApply {

    public static void main(String[] arg) throws CloneNotSupportedException {

        CCState gameState = new CCState();	
        MiniMaxSearch cc = new CCProblem();
        
//        System.out.println("Initial State Colours: " + gameState.toString());
//        System.out.println("Initial State Icons: " + gameState.toStringByIcons());
//        System.out.println("Initial State Marbles: " + gameState.toMarbleString());
        
        System.out.println("First utility MAX: " + cc.utility(gameState,PlayerRole.MAX));
        System.out.println("First utility MIN: " + cc.utility(gameState,PlayerRole.MIN));
        
//        System.out.println("Copied State Colours: " + copiedState.toString());
//        System.out.println("Copied State Icons: " + copiedState.toStringByIcons());
//        System.out.println("Copied State Marbles: " + copiedState.toMarbleString());
//        
        
        Action action = new CCAction(11,3,10,4);
//           
//        System.out.println("Initial State Colours: " + gameState.toString());
//        System.out.println("Initial State Icons: " + gameState.toStringByIcons());
//        System.out.println("Initial State Marbles: " + gameState.toMarbleString());
//      
//        
        gameState = gameState.applyAction((CCAction) action);
        
        
         System.out.println("Second utility MAX: " + cc.utility(gameState,PlayerRole.MAX));
         System.out.println("Second utility MIN: " + cc.utility(gameState,PlayerRole.MIN));
         
         System.out.println("IS TERMINAL " + cc.isTerminal(gameState));
         System.out.println("IS STATE TERMINAL " + gameState.isGameOver());

//        System.out.println("After State Colours: " + gameState.toString());
//        System.out.println("After State Icons: " + gameState.toStringByIcons());
//        System.out.println("After State Marbles: " + gameState.toMarbleString());

        
//      
            
    } //end method
} //end class

