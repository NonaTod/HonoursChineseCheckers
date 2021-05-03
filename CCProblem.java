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

    public boolean isTerminalLong(State state1) {
        CCState state = (CCState) state1;

        return state.isGameOverLong();

    } //end method

    /**
     * Calculate the utility value of a state towards a player.
     *
     * @param state1	The board state.
     * @param role	The player role.
     * @return	The score of the state towards this player.
     */
    @Override
    public double utility(State state1, PlayerRole role) {
//        System.out.println("In utility fn");
        CCState state = (CCState) state1;
        double cost = 0;
        int distance = 0;

        for (int x = 0; x < state.gb.length; x++) {
            for (int y = 0; y < state.gb[0].length; y++) {

                if (state.gb[x][y] != null) {
                    Piece piece = state.gb[x][y];

                    if (role == PlayerRole.DF && piece.isDF) {

                        if (piece.getColour() == Colour.RED) {

                            if (y > 3) {
                                distance = distance - (Math.abs(12 - x)) * 100;
                            }

                            if (y > 14 && y < 16) {
                                distance = distance + (16 - y) * 100;
                            } else if (y > 10) {
                                distance = distance + (16 - y) * 200;
                            } else if (y > 8) {
                                distance = distance + (16 - y) * 300;
                            } else if (y > 7) {
                                distance = distance + (16 - y) * 500;
                            } else if (y > 5) {
                                distance = distance + (16 - y) * 700;
                            } else if (y > 3) {
                                distance = distance + (16 - y) * 1000;
                            } else if (y < 4) {
                                distance = distance + (16 - y) * 2000;
                            }

                        } else if (piece.getColour() == Colour.GREEN) {

                            if (y < 13) {
                                distance = distance - (Math.abs(12 - x)) * 100;
                            }

                            if (y > 12) {
                                distance = distance + y * 2000;
                            } else if (y > 10) {
                                distance = distance + y * 1000;
                            } else if (y > 8) {
                                distance = distance + y * 700;
                            } else if (y > 7) {
                                distance = distance + y * 500;
                            } else if (y > 5) {
                                distance = distance + y * 300;
                            } else if (y > 3) {
                                distance = distance + y * 200;
                            } else if (y > 0) {
                                distance = distance + y * 100;
                            }
                        }

                        //END OF FIRST SIMPLE HEURISTIC
                        cost = -1 * distance;
                    }
                }
//----------------MINIMAX-------------------------------------------------------------------
                if (state.gb[x][y] != null && state.gb[x][y].isMinimax) {
                    Piece piece = state.gb[x][y];

                    if (role == PlayerRole.MAX && piece.isMax) {

                        if (piece.getColour() == Colour.GREEN) {

                            //FIRST SIMPLE HEURISTIC
                            distance = distance + (y + 1) * 500;
//                            if (y < 13) {
//                                distance = distance - (Math.abs(12 - x)) * 300;
//                            }

//                            if (y < 13) {
//                                distance = distance - (Math.abs(12 - x)) * 70;
//                            }

                            //Simple zones heuristic
//                            if (y > 12) {
//                                distance = distance + y * 100;
//                            } else if (y > 7) {
//                                distance = distance + y * 50;
//                            } else if (y > 5) {
//                                distance = distance + y * 30;
//                            } else if (y > 3) {
//                                distance = distance + y * 15;
//                            } else if (y > 0) {
//                                distance = distance + y * 5;
//                            }
//                            if (y > 12) {
//                                distance = distance + y * 2000;
//                            } else if (y > 10) {
//                                distance = distance + y * 1000;
//                            } else if (y > 8) {
//                                distance = distance + y * 700;
//                            } else if (y > 7) {
//                                distance = distance + y * 500;
//                            } else if (y > 5) {
//                                distance = distance + y * 300;
//                            } else if (y > 3) {
//                                distance = distance + y * 200;
//                            } else if (y > 0) {
//                                distance = distance + y * 100;
//                            }
                            //END OF FIRST SIMPLE HEURISTIC
                        } else if (piece.getColour() == Colour.BLACK) {

//                            distance = distance + Math.abs(x - 24) + Math.abs(y - 12);
                        } else if (piece.getColour() == Colour.YELLOW) {

//                            distance = distance + Math.abs(x - 24) + Math.abs(y - 4);
                        } else if (piece.getColour() == Colour.RED) {

//                            if (y > 12) {
//                                distance = distance + (16 - y) * 100;
//                            } else if (y > 7) {
//                                distance = distance + (16 - y) * 300;
//                            } else if (y < 4) {
//                                distance = distance + (16 - y) * 1000;
//                            }
//
//                            distance = distance + (5 - Math.abs(x - 12)) * 100;
                        } else if (piece.getColour() == Colour.BLUE) {

//                            distance = distance + Math.abs(x) + Math.abs(y - 4);
                        } else if (piece.getColour() == Colour.WHITE) {

//                            distance = distance + Math.abs(x) + Math.abs(y - 12);
                        }
                        cost = distance * 100;

                    } else if (role == PlayerRole.MIN && !piece.isMax) {
//                    } else if (role == PlayerRole.DF && !piece.isMax) {

                        if (piece.getColour() == Colour.GREEN) {

                        } else if (piece.getColour() == Colour.BLACK) {

//                            distance = distance + Math.abs(x - 24) + Math.abs(y - 12);
                        } else if (piece.getColour() == Colour.YELLOW) {

                            distance = distance + Math.abs(x - 24) + Math.abs(y - 4);

                        } else if (piece.getColour() == Colour.RED) {

                            //FIRST SIMPLE HEURISTIC
//                            distance = distance + (17 - y) * 500;
//                            if (y > 3) {
//                                distance = distance - (Math.abs(12 - x)) * 300;
//                            }
                            if (y > 3) {
                                distance = distance - (Math.abs(12 - x)) * 70;
                            }

                            //Simple zones heuristic
                            if (y > 14 && y < 16) {
                                distance = distance + (16 - y) * 5;
                            } else if (y > 10) {
                                distance = distance + (16 - y) * 15;
                            } else if (y > 8) {
                                distance = distance + (16 - y) * 30;
                            } else if (y > 3) {
                                distance = distance + (16 - y) * 50;
                            } else if (y < 4) {
                                distance = distance + (16 - y) * 100;
                            }
//                            Advanced zones heuristic
                            if (y > 14 && y < 16) {
                                distance = distance + (16 - y) * 100;
                            } else if (y > 10) {
                                distance = distance + (16 - y) * 200;
                            } else if (y > 8) {
                                distance = distance + (16 - y) * 300;
                            } else if (y > 7) {
                                distance = distance + (16 - y) * 500;
                            } else if (y > 5) {
                                distance = distance + (16 - y) * 700;
                            } else if (y > 3) {
                                distance = distance + (16 - y) * 1000;
                            } else if (y < 4) {
                                distance = distance + (16 - y) * 2000;
                            }
                            //END OF FIRST SIMPLE HEURISTIC

                        } else if (piece.getColour() == Colour.BLUE) {

//                            distance = distance + Math.abs(x) + Math.abs(y - 4);
                        } else if (piece.getColour() == Colour.WHITE) {

//                            distance = distance + Math.abs(x) + Math.abs(y - 12);
                        }
                        cost = -1 * distance * 100;

                    }
                }
            }
        }

//        System.out.println("Utility cost for player: " + role + ": " + cost);
        return cost;
    } //end method

} //end class

