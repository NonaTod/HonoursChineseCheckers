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
public class TestChineseCheckers {

    public static void main(String[] arg) throws CloneNotSupportedException {

        CCState gameState = new CCState();	//create initial state of empty board
        MiniMaxSearch cc = new CCProblem();	//create minimax problem instance
        //CCAlphaBetaProblem cc=new CCAlphaBetaProblem();
        
        Player currPlayer = gameState.currPlayer;
        ArrayList<Player> turnOrder = gameState.turnOrder;

        while (!cc.isTerminal(gameState)) {
            System.out.println("Curr player:" + currPlayer.getName());
            Action action = currPlayer.getAction(cc, gameState);
            System.out.println("Action: " + action.toString());
            gameState = gameState.applyAction((CCAction) action);
            int currIndex = 0;

            for (int i = 0; i < turnOrder.size(); i++) {
                if (turnOrder.get(i) == currPlayer) {
                    currIndex = i;
                    System.out.println("Current index: " + currIndex);
                }
            }

            // if the number of players exceeds 3 then the way
            // or is determined changes 
            System.out.println("In that loop");
            if (currIndex < (turnOrder.size() - 1)) {
                currPlayer = turnOrder.get(currIndex + 1);
            } else {
                currPlayer = turnOrder.get(0);
            }
        } //end while

        String greenArray = "";
        String redArray = "";
        for (int x = 0; x < gameState.gb.length; x++) {
            for (int y = 0; y < gameState.gb[0].length; y++) {
                if (gameState.gb[x][y] != null) {

                    if (gameState.gb[x][y].getColour() == Colour.GREEN) {
                        greenArray += "x: " + x + ", y: " + y + "\n";
                    }
                    if (gameState.gb[x][y].getColour() == Colour.RED) {
                        redArray += "x: " + x + ", y: " + y + "\n";
                    }

                }
            }
        }
        System.out.println("Green array: \n" + greenArray);
        System.out.println("Red array: \n" + redArray);

        System.out.println("Game Ended!");
    } //end method
} //end class

