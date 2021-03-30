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
public class CCProblem extends MiniMaxSearch {

    @Override
    public boolean isTerminal(State state1) {
        CCState state = (CCState) state1;

        return state.isGameOver();

    } //end method

    /**
     * Calculate the utility value of a state towards a player.
     *
     * @param state1	The board state.
     * @param role	The player role.
     * @return	The score of the state towards this player.
     */
    @Override
    public double utility(State state1, MiniMaxPlayerRole role) {
//        System.out.println("In utility fn");
        CCState state = (CCState) state1;
        double cost = 0;
        int distance = 0;

        for (int x = 0; x < state.gb.length; x++) {
            for (int y = 0; y < state.gb[0].length; y++) {
                if (state.gb[x][y] != null && state.gb[x][y].isMinimax) {
                    
                    Piece piece = state.gb[x][y];
                    

                    if (role == MiniMaxPlayerRole.MAX && piece.isMax) {

                        System.out.println("In utility.\n x:y = " + x + ":" + y + "\n Is piece max: " + piece.isMax);
                        
                        if (piece.getColour() == Colour.GREEN) {
                            
                            //the distance between the pieces and the tip of the red triangle
                            distance = distance + Math.abs(x - 12) + Math.abs(y - 16);
                            System.out.println("Distance: " + distance);

                        } else if (piece.getColour() == Colour.BLACK) {

                            distance = distance + Math.abs(x - 24) + Math.abs(y - 12);

                        } else if (piece.getColour() == Colour.YELLOW) {

                            distance = distance + Math.abs(x - 24) + Math.abs(y - 4);

                        } else if (piece.getColour() == Colour.RED) {

                            distance = distance + Math.abs(x - 12) + Math.abs(y);

                        } else if (piece.getColour() == Colour.BLUE) {

                            distance = distance + Math.abs(x) + Math.abs(y - 4);

                        } else if (piece.getColour() == Colour.WHITE) {

                            distance = distance + Math.abs(x) + Math.abs(y - 12);
                        }
                        cost = distance * 100;

                    } else if (role == MiniMaxPlayerRole.MIN && !piece.isMax) {
                        
                        if (piece.getColour() == Colour.GREEN) {
                            //the distance between the pieces and the tip of the red triangle
                            distance = distance + Math.abs(x - 12) + Math.abs(y - 16);

                        } else if (piece.getColour() == Colour.BLACK) {

                            distance = distance + Math.abs(x - 24) + Math.abs(y - 12);

                        } else if (piece.getColour() == Colour.YELLOW) {

                            distance = distance + Math.abs(x - 24) + Math.abs(y - 4);

                        } else if (piece.getColour() == Colour.RED) {

                            distance = distance + Math.abs(x - 12) + Math.abs(y);

                        } else if (piece.getColour() == Colour.BLUE) {

                            distance = distance + Math.abs(x) + Math.abs(y - 4);

                        } else if (piece.getColour() == Colour.WHITE) {

                            distance = distance + Math.abs(x) + Math.abs(y - 12);
                        }
                        cost = -1 * distance * 100;

                    }
                }
            }
        }

        System.out.println("Utility cost for player: " + role + ": " + cost);
        return cost;
    } //end method

//    /**
//     * Calculate the utility score w.r.t. a symbol.
//     *
//     * @param state	The board state.
//     * @param symbol	The player symbol (i.e. X/O).
//     * @return	The utility score of the state towards the symbol.
//     */
//    private double utility(chinesechecker.State state, Symbol symbol) {
//        double result = 0.0;
//        int[] count = new int[4];
//        CCState tttState = (CCState) state;
//
//        for (int i = 0; i < count.length; i++) {
//            count[i] = 0;
//        }
//
//        int symbolCount = 0;
//        for (int y = 0; y < tttState.board.length; y++) {
//            symbolCount = rowSymbolCount(tttState, y, symbol);
//            count[symbolCount]++;
//        }
//
//        for (int x = 0; x < tttState.board[0].length; x++) {
//            symbolCount = columnSymbolCount(tttState, x, symbol);
//            count[symbolCount]++;
//        }
//        symbolCount = upDiagonalSymbolCount(tttState, symbol);
//        count[symbolCount]++;
//
//        symbolCount = downDiagonalSymbolCount(tttState, symbol);
//        count[symbolCount]++;
//
//        result += count[3] * 1000 + count[2] * 10 + count[1] * 1;
//        return result;
//    } //end method
} //end class

