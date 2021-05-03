/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechecker;

import chinesechecker.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nonit
 */
public class TestChineseCheckers {

    public static void main(String[] arg) throws CloneNotSupportedException {

        CCState gameState = new CCState();

        //MINIMAX 
//        CCProblem cc = new CCProblem();	
        CCAlphaBetaProblem cc = new CCAlphaBetaProblem();

        //MONTE CARLO 
        MCProblem agent = new PudgeAgent();

        //DEPTH FIRST ITERATIVE DEEPENING A*
        DFSearch dfs;

        int moves = 0;
        int nodes = 0;
        int maxNodes = Integer.MIN_VALUE;
        int minNodes = Integer.MAX_VALUE;

        Player currPlayer = gameState.currPlayer;
        ArrayList<Player> turnOrder = gameState.turnOrder;
        System.out.println("\n\n~~~~~~~~~~ BEGINNING OF THE CHINESE CHECKERS GAME ~~~~~~~~~~\n\n");

        while (!cc.isTerminalLong(gameState)) {
            System.out.println("\n\n\n\n");
            Action action = new CCAction();

         
            if (currPlayer.role == PlayerRole.MAX || currPlayer.role == PlayerRole.MIN) {
                action = currPlayer.getAction(cc, gameState);
                System.out.println("Current MAX action: " + action.toString());
                gameState = gameState.applyAction((CCAction) action);

            } else if (currPlayer.role == PlayerRole.MC) {
                System.out.println("Current player MC");
                action = agent.nextMove(gameState);
                System.out.println("Current MC action: " + action.toString());
                gameState = gameState.applyAction(action);
                
            } else if (currPlayer.role == PlayerRole.DF) {
                dfs = new DFProblem(gameState);
                System.out.println("Current player DF");
                DFNode node = dfs.search();
                action = node.action;
                System.out.println("Current DF action: " + action.toString());
                gameState = gameState.applyAction((CCAction) action);
            }

            System.out.println("Game state after action:" + gameState.toStringByIcons());

            if (action.nodes > maxNodes) {
                maxNodes = action.nodes;
            }

            if (action.nodes < minNodes) {
                minNodes = action.nodes;
            }
            nodes += action.nodes;


            int currIndex = 0;
            for (int i = 0; i < turnOrder.size(); i++) {
                if (turnOrder.get(i) == currPlayer) {
                    currIndex = i;
                    System.out.println("Current index: " + currIndex);
                }
            }

            // if the number of players exceeds 3 then the way
            // or is determined changes 
            if (currIndex < (turnOrder.size() - 1)) {
                currPlayer = turnOrder.get(currIndex + 1);
            } else {
                currPlayer = turnOrder.get(0);
            }
            
            System.out.println("Move: " + moves++);
            System.out.println("Total moves: " + moves);
            System.out.println("Min nodes: " + minNodes);
            System.out.println("Max nodes: " + maxNodes);
            System.out.println("Total nodes: " + nodes);
            System.out.println("Average nodes checked: " + nodes / moves);

        } //end while

        System.out.println( "GAME STATE AFTER END by colours" + gameState.toString());
        System.out.println("Game Ended!");
        System.out.println("Total moves: " + moves);
        System.out.println("Min nodes: " + minNodes);
        System.out.println("Max nodes: " + maxNodes);
        System.out.println("Total nodes: " + nodes);
        System.out.println("Average nodes checked: " + nodes / moves);
    } //end method
} //end class

