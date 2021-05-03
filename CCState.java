
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechecker;

import java.util.*;
import chinesechecker.Board;
import static chinesechecker.GameState.rnd;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author nonit
 */
public class CCState implements State, Cloneable {

    public Piece gb[][];

    public ImageIcon o_grn, o_red, o_blank; //, o_blk, o_wht, o_yel, o_blu;
    public ImageIcon s_grn, s_red; // s_blk, s_wht, s_yel, s_blu;
    public ImageIcon h_grn, h_red; // h_blk, h_wht, h_yel, h_blu;
    public ImageIcon d_grn, d_red; //d_blk, d_wht, d_yel, d_blu;

    public ArrayList<Player> turnOrder;
    public Player currPlayer;
    public Player winner;
    public boolean gameStarted;
    public boolean gameOver;
    public int visits;
    public int wins;
    public State parent;

    public int[][] grn_arr;
    public int[][] red_arr;
//    public int[][] blk_arr;
//    public int[][] wht_arr;
//    public int[][] yel_arr;
//    public int[][] blu_arr;

    int nextIndexGrn;
    int nextIndexRed;
//    int nextIndexBlk;
//    int nextIndexWht;
//    int nextIndexYel;
//    int nextIndexBlu;

    Player player1;
    Player player2;
    List<Move> possible_moves = new ArrayList<Move>();
    List<CCState> game_tree = new ArrayList<CCState>();
    Map<Move, Double> score_history = new HashMap<Move, Double>();
    Map<Move, Integer> play_history = new HashMap<Move, Integer>();
    ArrayList<Piece> jump_visited = new ArrayList<Piece>();

    /**
     * Create an empty game board.
     */
    public CCState() {

        this.gb = new Piece[25][17];

        grn_arr = new int[2][10];
        red_arr = new int[2][10];
//        blk_arr = new int[2][10];
//        wht_arr = new int[2][10];
//        yel_arr = new int[2][10];
//        blu_arr = new int[2][10];

        nextIndexGrn = 0;
        nextIndexRed = 0;
//        nextIndexBlk = 0;
//        nextIndexWht = 0;
//        nextIndexYel = 0;
//        nextIndexBlu = 0;

        visits = 0;
        wins = 0;
//        parent = new CCState();

        initImageIcons();
        autoInitGame();
    } //end method

    public void initImageIcons() {

        o_grn = new ImageIcon(getClass().getResource("../images/o_green_34.png"));
        o_red = new ImageIcon(getClass().getResource("../images/o_red_34.png"));
//        o_blk = new ImageIcon(getClass().getResource("../images/o_black_34.png"));
//        o_wht = new ImageIcon(getClass().getResource("../images/o_white_34.png"));
//        o_yel = new ImageIcon(getClass().getResource("../images/o_yellow_34.png"));
//        o_blu = new ImageIcon(getClass().getResource("../images/o_blue_34.png"));
        o_blank = new ImageIcon(getClass().getResource("../images/o_blank_34.png"));

        s_grn = new ImageIcon(getClass().getResource("../images/s_green_34.png"));
//        s_blk = new ImageIcon(getClass().getResource("../images/s_black_34.png"));
//        s_wht = new ImageIcon(getClass().getResource("../images/s_white_34.png"));
//        s_yel = new ImageIcon(getClass().getResource("../images/s_yellow_34.png"));
//        s_blu = new ImageIcon(getClass().getResource("../images/s_blue_34.png"));
        s_red = new ImageIcon(getClass().getResource("../images/s_red_34.png"));

        h_grn = new ImageIcon(getClass().getResource("../images/h_green_34.png"));
//        h_blk = new ImageIcon(getClass().getResource("../images/h_black_34.png"));
//        h_wht = new ImageIcon(getClass().getResource("../images/h_white_34.png"));
//        h_yel = new ImageIcon(getClass().getResource("../images/h_yellow_34.png"));
//        h_blu = new ImageIcon(getClass().getResource("../images/h_blue_34.png"));
        h_red = new ImageIcon(getClass().getResource("../images/h_red_34.png"));

        d_grn = new ImageIcon(getClass().getResource("../images/d_green_34.png"));
//        d_blk = new ImageIcon(getClass().getResource("../images/d_black_34.png"));
//        d_wht = new ImageIcon(getClass().getResource("../images/d_white_34.png"));
//        d_yel = new ImageIcon(getClass().getResource("../images/d_yellow_34.png"));
//        d_blu = new ImageIcon(getClass().getResource("../images/d_blue_34.png"));
        d_red = new ImageIcon(getClass().getResource("../images/d_red_34.png"));

    } // end of initImageIcons

    public void autoInitGame() {
        gameOver = false;
        gameStarted = true;
        winner = null;
        turnOrder = new ArrayList<>();

        //Player 1 options
        player1 = new MiniMaxPlayer("Computer 1 - MAX - GREEN ", Colour.GREEN, PlayerRole.MAX);
//        Player player1 = new MCPlayer("Computer 1 - MC - GREEN ", Colour.GREEN, PlayerRole.MC);
//        player1 = new MCPlayer("Computer 1 - DF - GREEN ", Colour.GREEN, PlayerRole.DF);

        //Player 2 options
        Player player2 = new MiniMaxPlayer("Computer 2 - MIN - RED ", Colour.RED, PlayerRole.MIN);
//        player2 = new MCPlayer("Computer 2 - MC - RED ", Colour.RED, PlayerRole.MC);
//        player2 = new MCPlayer("Computer 2 - DF - RED ", Colour.RED, PlayerRole.DF);

        //Add the players to the turn array and set green to go first
        currPlayer = player1;
        turnOrder.add(player1);
        turnOrder.add(player2);

        buildBoard();

    } // end of initGame

    //CREATING THE PIECES ON THE BOARD BY COORDIANTES
    public void buildBoard() {

        //Creating the green pieces
        createPiece(12, 0, o_grn);
        createPiece(11, 1, o_grn);
        createPiece(13, 1, o_grn);
        createPiece(10, 2, o_grn);
        createPiece(12, 2, o_grn);
        createPiece(14, 2, o_grn);
        createPiece(9, 3, o_grn);
        createPiece(11, 3, o_grn);
        createPiece(13, 3, o_grn);
        createPiece(15, 3, o_grn);

//        //Creating the black pieces
//        createPiece(0, 4, o_blk);
//        createPiece(2, 4, o_blk);
//        createPiece(4, 4, o_blk);
//        createPiece(6, 4, o_blk);
//        createPiece(1, 5, o_blk);
//        createPiece(3, 5, o_blk);
//        createPiece(5, 5, o_blk);
//        createPiece(2, 6, o_blk);
//        createPiece(4, 6, o_blk);
//        createPiece(3, 7, o_blk);
//        //Creating the yellow pieces
//        createPiece(3, 9, o_yel);
//        createPiece(2, 10, o_yel);
//        createPiece(4, 10, o_yel);
//        createPiece(1, 11, o_yel);
//        createPiece(3, 11, o_yel);
//        createPiece(5, 11, o_yel);
//        createPiece(0, 12, o_yel);
//        createPiece(2, 12, o_yel);
//        createPiece(4, 12, o_yel);
//        createPiece(6, 12, o_yel);
        //Creating the red pieces
        createPiece(9, 13, o_red);
        createPiece(11, 13, o_red);
        createPiece(13, 13, o_red);
        createPiece(15, 13, o_red);
        createPiece(10, 14, o_red);
        createPiece(12, 14, o_red);
        createPiece(14, 14, o_red);
        createPiece(11, 15, o_red);
        createPiece(13, 15, o_red);
        createPiece(12, 16, o_red);

        //Creating the white pieces
//        createPiece(18, 4, o_wht);
//        createPiece(20, 4, o_wht);
//        createPiece(22, 4, o_wht);
//        createPiece(24, 4, o_wht);
//        createPiece(19, 5, o_wht);
//        createPiece(21, 5, o_wht);
//        createPiece(23, 5, o_wht);
//        createPiece(20, 6, o_wht);
//        createPiece(22, 6, o_wht);
//        createPiece(21, 7, o_wht);
//        //Creating the blue pieces
//        createPiece(21, 9, o_blu);
//        createPiece(20, 10, o_blu);
//        createPiece(22, 10, o_blu);
//        createPiece(19, 11, o_blu);
//        createPiece(21, 11, o_blu);
//        createPiece(23, 11, o_blu);
//        createPiece(18, 12, o_blu);
//        createPiece(20, 12, o_blu);
//        createPiece(22, 12, o_blu);
//        createPiece(24, 12, o_blu);
        //create blank pieces - upper half
        int start = 8;
        int end = 17;

        for (int y = 4; y < 9; y++) {
            for (int x = start; x < end; x = x + 2) {
                createPiece(x, y, o_blank);
            }
            start--;
            end++;
        }

        //create blank pieces - lower half 
        int start1 = 5;
        int end1 = 20;

        for (int y = 9; y < 13; y++) {
            for (int x = start1; x < end1; x = x + 2) {
//                System.out.println("x y: " + x + " " + y);
                createPiece(x, y, o_blank);
            }
            start1++;
            end1--;
        }
//        System.out.println(Arrays.deepToString(gb));

    } // end of buildBoard

    //CREATE PIECE FUNCTION
    public void createPiece(int x, int y, ImageIcon icon) {
//        System.out.println("Create inner: x:" + x);
//        System.out.println("y:" + y + " ImageIcon: " + icon);

        this.gb[x][y] = new Piece(x, y);
        this.gb[x][y].piece.setIcon(icon);

        if (icon == o_grn) {

            this.gb[x][y].setColour(Colour.GREEN);
            grn_arr[0][nextIndexGrn] = x;
            grn_arr[1][nextIndexGrn] = y;
            nextIndexGrn++;

        } else if (icon == o_red) {

            this.gb[x][y].setColour(Colour.RED);
            red_arr[0][nextIndexRed] = x;
            red_arr[1][nextIndexRed] = y;
            nextIndexRed++;
        }
//        else if (icon == o_blk) {
//
//            this.gb[x][y].setColour(Colour.BLACK);
//            blk_arr[0][nextIndexBlk] = x;
//            blk_arr[1][nextIndexBlk] = y;
//            nextIndexBlk++;
//
//        } else if (icon == o_wht) {
//
//            this.gb[x][y].setColour(Colour.WHITE);
//            wht_arr[0][nextIndexWht] = x;
//            wht_arr[1][nextIndexWht] = y;
//            nextIndexWht++;
//
//        } else if (icon == o_yel) {
//
//            this.gb[x][y].setColour(Colour.YELLOW);
//            yel_arr[0][nextIndexYel] = x;
//            yel_arr[1][nextIndexYel] = y;
//            nextIndexYel++;
//
//        } else if (icon == o_blu) {
//
//            this.gb[x][y].setColour(Colour.BLUE);
//            blu_arr[0][nextIndexBlu] = x;
//            blu_arr[1][nextIndexBlu] = y;
//            nextIndexBlu++;
//
//        } 

        //Code for player 1 
        if (icon == o_grn) {
//            if (player1.role == PlayerRole.MAX) {
                this.gb[x][y].isMinimax = true;
                this.gb[x][y].isMax = true;
                this.gb[x][y].isMC = false;
                this.gb[x][y].isDF = false;

//            } else if (player1.role == PlayerRole.MC) {
//                this.gb[x][y].isMinimax = false;
//                this.gb[x][y].isMax = false;
//                this.gb[x][y].isMC = true;
//                this.gb[x][y].isDF = false;
//            } else if (player1.role == PlayerRole.DF) {
//            this.gb[x][y].isMinimax = false;
//            this.gb[x][y].isMax = false;
//            this.gb[x][y].isMC = false;
//            this.gb[x][y].isDF = true;
        }
//            }
//        }

        //Code for player 2
        if (icon == o_red) {
//            if (player1.role == PlayerRole.MAX) {
                this.gb[x][y].isMinimax = true;
                this.gb[x][y].isMax = false;
                this.gb[x][y].isMC = false;
                this.gb[x][y].isDF = false;

//else if (player1.role == PlayerRole.MC) {
//            this.gb[x][y].isMinimax = false;
//            this.gb[x][y].isMax = false;
//            this.gb[x][y].isMC = true;
//            this.gb[x][y].isDF = false;
//            } else if (player1.role == PlayerRole.DF) {
//                this.gb[x][y].isMinimax = false;
//                this.gb[x][y].isMax = false;
//                this.gb[x][y].isMC = false;
//                this.gb[x][y].isDF = true;
        }
//            } else {
//                System.out.println("Error in piece role assignment");
//            }
//        }

    }

    public boolean inRange(Piece current, Piece next) {
        int cX = current.getPieceX();
        int nX = next.getPieceX();

        int cY = current.getPieceY();
        int nY = next.getPieceY();

        if (((nX == cX - 1) && (nY == cY - 1))
                || ((nX == cX + 1) && (nY == cY - 1))
                || ((nX == cX + 1) && (nY == cY + 1))
                || ((nX == cX - 1) && (nY == cY + 1))
                || ((nX == cX - 2) && (nY == cY))
                || ((nX == cX + 2) && (nY == cY))) {
            return true;
        }
        return false;
    } // end of inRange

    public boolean isMarble(Piece p) {
        if (p != null) {
            if (p.colour != Colour.BLANK) {
                return true;
            }
        }
        return false;
    } // end of isMarble

    public boolean isHighlight(JLabel p) {
        if (p.getIcon() == h_grn) {
            return true;
        }
//        if (p.getIcon() == h_wht) {
//            return true;
//        }
//        if (p.getIcon() == h_blu) {
//            return true;
//        }
        if (p.getIcon() == h_red) {
            return true;
        }
//        if (p.getIcon() == h_yel) {
//            return true;
//        }
//        if (p.getIcon() == h_blk) {
//            return true;
//        }
        return false;
    } // end of isHighlight

    public boolean isDot(Piece piece) {
        JLabel p = piece.piece;
        if (p.getIcon() == d_grn) {
            return true;
        }
//        if (p.getIcon() == d_wht) {
//            return true;
//        }
//        if (p.getIcon() == d_blu) {
//            return true;
//        }
        if (p.getIcon() == d_red) {
            return true;
        }
//        if (p.getIcon() == d_yel) {
//            return true;
//        }
//        if (p.getIcon() == d_blk) {
//            return true;
//        }
        return false;
    } // end of isDot

    public ImageIcon getImageIcon(int i, char c) {

        ///////////////////////////////////////
        // Highlight
        if (c == 'h') {
            if (0 == i) {
                return h_grn;
//            } else if (1 == i) {
//                return h_wht;
//            } else if (2 == i) {
//                return h_blu;
            } else if (3 == i) {
                return h_red;
            }
//            else if (4 == i) {
//                return h_yel;
//            } else if (5 == i) {
//                return h_blk;
//            }
        }

        ///////////////////////////////////////
        // Dot
        if (c == 'd') {
            if (0 == i) {
                return d_grn;
//            } else if (1 == i) {
//                return d_wht;
//            } else if (2 == i) {
//                return d_blu;
            } else if (3 == i) {
                return d_red;
            }
//            else if (4 == i) {
//                return d_yel;
//            } else if (5 == i) {
//                return d_blk;
//            }
        }

        ///////////////////////////////////////
        // Select
        if (c == 's') {
            if (0 == i) {
                return s_grn;
//            } else if (1 == i) {
//                return s_wht;
//            } else if (2 == i) {
//                return s_blu;
            } else if (3 == i) {
                return s_red;
//            } else if (4 == i) {
//                return s_yel;
//            } else if (5 == i) {
//                return s_blk;
            }
        }

        ///////////////////////////////////////
        // Original
        if (c == 'o') {
            if (0 == i) {
                return o_grn;
//            } else if (1 == i) {
//                return o_wht;
//            } else if (2 == i) {
//                return o_blu;
            } else if (3 == i) {
                return o_red;
//            } else if (4 == i) {
//                return o_yel;
//            } else if (5 == i) {
//                return o_blk;
            } else if (6 == i) {
                return o_blank;
            }
        }
        ///////////////////////////////////////
        return null;
    } // end of getImageIcon

    public void showJumps(Piece piece) {
//        System.out.println("In show jumps func");
        JLabel p = piece.piece;
        for (int i = 0; i < gb.length; i++) {
            for (int y = 0; y < gb[0].length; y++) {
                if (gb[i][y] != null) {
                    if (canJump(piece, gb[i][y]) && piece != gb[i][y] && !isMarble(gb[i][y])) {
                        gb[i][y].piece.setIcon(getImageIcon(getColorInt(piece), 'h'));
                    }
                }
            }
        }
    } // end of showJumps

    public void showMoves(Piece piece) {

        for (int x = 0; x < gb.length; x++) {
            for (int y = 0; y < gb[0].length; y++) {

                if (gb[x][y] != null) {

                    boolean canJump = canJump(piece, gb[x][y]);
                    if (inRange(piece, gb[x][y]) || canJump) {
                        if (piece != gb[x][y] && !isMarble(gb[x][y])) {

                            gb[x][y].piece.setIcon(getImageIcon(getColorInt(piece), 'h'));

                            int jumpX = x;
                            int jumpY = y;

                            if (canJump) {
                                boolean jumping = true;

                                while (jumping) {
                                    for (int t = 0; t < gb.length; t++) {
                                        for (int r = 0; r < gb[0].length; r++) {
                                            if (gb[t][r] != null) {

                                                if (canJump(gb[jumpX][jumpY], gb[t][r])) {
                                                    gb[t][r].piece.setIcon(getImageIcon(getColorInt(piece), 'h'));
                                                    jumpX = t;
                                                    jumpY = r;
                                                } else {
                                                    jumping = false;
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    } // end of showMoves

    public boolean isGameOver() {
        if (checkIfAllAreTheSame(grn_arr, o_grn)) {
            return true;
        } else if (checkIfAllAreTheSame(red_arr, o_red)) {
            return true;
        }

//        } else if (checkIfAllAreTheSame(blk_arr, o_blk)) {
//            return true;
//        } else if (checkIfAllAreTheSame(yel_arr, o_yel)) {
//            return true;
//        } else if (checkIfAllAreTheSame(blu_arr, o_blu)) {
//            return true;
//        } else if (checkIfAllAreTheSame(wht_arr, o_wht)) {
//            return true;
//        }
        return false;

    } // end of isGameOver

    //IS GAME OVER WITH ADDITIONAL SWAP FUNCTIONALITY 
    public boolean isGameOverLong() {
        StringBuilder sb = new StringBuilder();

        if (shouldSwap(grn_arr, o_grn)) {

            for (int i = 0; i < 10; i++) {
                //Go through the array
                Piece inner = gb[grn_arr[0][i]][grn_arr[1][i]];
                //If they are not all marbles, return false
                if (inner.piece.getIcon().toString().equals(o_grn.toString())) {

                    for (int x = 8; x < 17; x = x + 2) {
                        if (isMarble(gb[x][4]) && !gb[x][4].piece.getIcon().toString().equals(o_grn.toString())) {

                            Piece tempHoldingOuterPiece = new Piece(gb[x][4]);

                            this.gb[x][4] = new Piece(this.gb[inner.getPieceX()][inner.getPieceY()]);
                            this.gb[x][4].setX(x);
                            this.gb[x][4].setY(4);

                            this.gb[inner.getPieceX()][inner.getPieceY()] = new Piece(tempHoldingOuterPiece);
                            this.gb[inner.getPieceX()][inner.getPieceY()].setX(inner.getPieceX());
                            this.gb[inner.getPieceX()][inner.getPieceY()].setY(inner.getPieceY());

                        }
                    }
                }
            }
        }

        if (shouldSwap(red_arr, o_red)) {

            for (int i = 0; i < 10; i++) {
                //Go through the array
                Piece inner = gb[red_arr[0][i]][red_arr[1][i]];
                //If they are not all marbles, return false
                if (inner.piece.getIcon().toString().equals(o_red.toString())) {

                    for (int x = 8; x < 17; x = x + 2) {
                        if (isMarble(gb[x][12]) && !gb[x][12].piece.getIcon().toString().equals(o_red.toString())) {

                            Piece tempHoldingOuterPiece = this.gb[x][12];

                            this.gb[x][12] = new Piece(this.gb[inner.getPieceX()][inner.getPieceY()]);
                            this.gb[x][12].setX(x);
                            this.gb[x][12].setY(12);

                            this.gb[inner.getPieceX()][inner.getPieceY()] = new Piece(tempHoldingOuterPiece);
                            this.gb[inner.getPieceX()][inner.getPieceY()].setX(inner.getPieceX());
                            this.gb[inner.getPieceX()][inner.getPieceY()].setY(inner.getPieceY());

                        }
                    }
                }
            }
        }

        if (checkIfAllAreTheSame(grn_arr, o_grn)) {
            return true;
        } else if (checkIfAllAreTheSame(red_arr, o_red)) {
            return true;
        }

//        } else if (checkIfAllAreTheSame(blk_arr, o_blk)) {
//            return true;
//        } else if (checkIfAllAreTheSame(yel_arr, o_yel)) {
//            return true;
//        } else if (checkIfAllAreTheSame(blu_arr, o_blu)) {
//            return true;
//        } else if (checkIfAllAreTheSame(wht_arr, o_wht)) {
//            return true;
//        }
        return false;

    } // end of isGameOver

    public boolean checkIfAllAreTheSame(int[][] array, ImageIcon icon) {

        Piece first = gb[array[0][0]][array[1][0]];
//        System.out.println("checkIfAllAreTheSame: " + array[0][0] + " " + array[1][0]);
//
//        System.out.println("First icon: " + first.inner.getIcon());
//        System.out.println("Icon" + icon);

        //Check if the first inner is not the original inner
        if (first.piece.getIcon().toString() == null ? icon.toString() == null : first.piece.getIcon().toString().equals(icon.toString())) {
            return false;
        }

        if (first.piece.getIcon().toString() == null ? o_blank.toString() == null : first.piece.getIcon().toString().equals(o_blank.toString())) {
            return false;
        }

        for (int i = 1; i < 10; i++) {
            Piece second = gb[array[0][i]][array[1][i]];

            //  System.out.println("Icon1 is  " + first.inner.getIcon() );
            // System.out.println("Icon2 is  " + second.inner.getIcon() );
            //if the color image of the first icon does not match all, 
            //then game is not over
            if (!(first.piece.getIcon().toString() == null ? second.piece.getIcon().toString() == null : first.piece.getIcon().toString().equals(second.piece.getIcon().toString()))) {
                return false;
            }
        }
        return true;
    }

    public boolean allAreMarbles(int[][] array) {

        for (int i = 0; i < 10; i++) {
            //Go through the array
            Piece piece = gb[array[0][i]][array[1][i]];
            //If they are not all marbles, return false
            if (!isMarble(piece)) {
                return false;
            }
        }
        return true;
    }

    public boolean shouldSwap(int[][] array, ImageIcon icon) {

        if (allAreMarbles(array)) {
//            System.out.println("All are marble.");
        } else {
//            System.out.println("They are not marble.");
            return false;
        }

        //If checking the green array
        if (icon.toString().equals(o_grn.toString())) {
            //check if the tip is green

            if (gb[12][0].piece.getIcon().toString().equals(o_grn.toString())
                    || gb[11][1].piece.getIcon().toString().equals(o_grn.toString())
                    || gb[13][1].piece.getIcon().toString().equals(o_grn.toString())) {
                //if the tip is green, check is the rest are not

                if (!gb[10][2].piece.getIcon().toString().equals(o_grn.toString())
                        && !gb[12][2].piece.getIcon().toString().equals(o_grn.toString())
                        && !gb[14][2].piece.getIcon().toString().equals(o_grn.toString())
                        && !gb[9][3].piece.getIcon().toString().equals(o_grn.toString())
                        && !gb[11][3].piece.getIcon().toString().equals(o_grn.toString())
                        && !gb[13][3].piece.getIcon().toString().equals(o_grn.toString())
                        && !gb[15][3].piece.getIcon().toString().equals(o_grn.toString())) {

                    for (int x = 8; x < 17; x = x + 2) {
                        if (isMarble(gb[x][4]) && !gb[x][4].piece.getIcon().toString().equals(o_grn.toString())) {
                            return true;
                        }
                    }
                }
            }
        }

        //If checking the green array
        if (icon.toString().equals(o_red.toString())) {
            //check if the tip is green

            if (gb[12][16].piece.getIcon().toString().equals(o_red.toString())
                    || gb[11][15].piece.getIcon().toString().equals(o_red.toString())
                    || gb[13][15].piece.getIcon().toString().equals(o_red.toString())) {
                //if the tip is green, check is the rest are not

                if (!gb[10][14].piece.getIcon().toString().equals(o_red.toString())
                        && !gb[12][14].piece.getIcon().toString().equals(o_red.toString())
                        && !gb[14][14].piece.getIcon().toString().equals(o_red.toString())
                        && !gb[9][13].piece.getIcon().toString().equals(o_red.toString())
                        && !gb[11][13].piece.getIcon().toString().equals(o_red.toString())
                        && !gb[13][13].piece.getIcon().toString().equals(o_red.toString())
                        && !gb[15][13].piece.getIcon().toString().equals(o_red.toString())) {
                    for (int x = 8; x < 17; x = x + 2) {
                        if (isMarble(gb[x][12]) && !gb[x][12].piece.getIcon().toString().equals(o_red.toString())) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;

    } // end of isGameOver

    public int getColorInt(Piece piece) {
        JLabel p = piece.piece;
        if ((p.getIcon().toString() == null ? o_grn.toString() == null : p.getIcon().toString().equals(o_grn.toString())) || (p.getIcon().toString() == null ? s_grn.toString() == null : p.getIcon().toString().equals(s_grn.toString()))) {
            return 0;
        }
//        if ((p.getIcon().toString() == null ? o_wht.toString() == null : p.getIcon().toString().equals(o_wht.toString())) || (p.getIcon().toString() == null ? s_wht.toString() == null : p.getIcon().toString().equals(s_wht.toString()))) {
//            return 1;
//        }
//        if ((p.getIcon().toString() == null ? o_blu.toString() == null : p.getIcon().toString().equals(o_blu.toString())) || (p.getIcon().toString() == null ? s_blu.toString() == null : p.getIcon().toString().equals(s_blu.toString()))) {
//            return 2;
//        }
        if ((p.getIcon().toString() == null ? o_red.toString() == null : p.getIcon().toString().equals(o_red.toString())) || (p.getIcon().toString() == null ? s_red.toString() == null : p.getIcon().toString().equals(s_red.toString()))) {
            return 3;
        }
//        if ((p.getIcon().toString() == null ? o_yel.toString() == null : p.getIcon().toString().equals(o_yel.toString())) || (p.getIcon().toString() == null ? s_yel.toString() == null : p.getIcon().toString().equals(s_yel.toString()))) {
//            return 4;
//        }
//        if ((p.getIcon().toString() == null ? o_blk.toString() == null : p.getIcon().toString().equals(o_blk.toString())) || (p.getIcon().toString() == null ? s_blk.toString() == null : p.getIcon().toString().equals(s_blk.toString()))) {
//            return 5;
//        }
        if (p.getIcon().toString() == null ? o_blank.toString() == null : p.getIcon().toString().equals(o_blank.toString())) {
            return 6;
        }

        return 7;
    } // end of getColorInt

    public void hideMoves(Piece piece) {
        JLabel p = piece.piece;
        // NEED TO ADD HIDE JUMPS TOO
        for (int i = 0; i < gb.length; i++) {
            for (int y = 0; y < gb[0].length; y++) {
                if (gb[i][y] != null) {
                    if (isHighlight(gb[i][y].piece)) {
                        gb[i][y].piece.setIcon(o_blank);
                    }
                }
            }
        }
    } // end of hideMoves

    public boolean isBlank(Piece p) {
        return 6 == getColorInt(p);
    } // end of isBlank

    public boolean isTurn(Piece s) {
        if (s.getColour() == currPlayer.getColour()) {
            return true;
        }
        return false;
    } // end of isTurn

    public ImageIcon getMarble(Piece piece) {
        JLabel p = piece.piece;
        if (p.getIcon() == s_grn) {
            return o_grn;
        }
//        if (p.getIcon() == s_wht) {
//            return o_wht;
//        }
//        if (p.getIcon() == s_blu) {
//            return o_blu;
//        }
        if (p.getIcon() == s_red) {
            return o_red;
        }
//        if (p.getIcon() == s_yel) {
//            return o_yel;
//        }
//        if (p.getIcon() == s_blk) {
//            return o_blk;
//        }
        return null;
    } // end of getMarble

    public boolean canJump(Piece current, Piece next) {

        JLabel s = current.piece;
        JLabel e = next.piece;
        // Calculate the distance between s and e
        int cX = current.getPieceX();
        int cY = current.getPieceY();

        int nX = next.getPieceX();
        int nY = next.getPieceY();

        int x = cX - nX;
        int y = cY - nY;

        // Determine the direction of jump
        //////////////////////////////////////////////////////////////////
        // direction can be 1, 2, 3, 4, 5 or 6
        int direction = 0;

        if (x > 0 && y > 0) {
            direction = 1; 			// + +
        } else if (x < 0 && y > 0) {
            direction = 2;  	// - +
        } else if (x < 0 && y < 0) {
            direction = 3;  	// - -
        } else if (x > 0 && y < 0) {
            direction = 4;  	// + -
        } else if (x > 0 && y == 0) {
            direction = 5; 	// <--
        } else if (x < 0 && y == 0) {
            direction = 6; 	// -->
        }

        if (((Math.abs(cX - nX) == 4) && (cY == nY))
                || ((Math.abs(cX - nX) == 2) && (Math.abs(cY - nY) == 2))) {

//            System.out.println("canJump func: current: " + cX + " " + cY);
//            System.out.println("canJump func: next: " + nX + " " + nY);
            switch (direction) {
                case 1: // + +
                    if (pieceIsMarble(gb[cX - 1][cY - 1])) {
                        if (isBlank(next)) {
//                            System.out.println("Direction 1, before return");
                            return true;
                        }
                    }
                    break;
                case 2: // - +
                    if (pieceIsMarble(gb[cX + 1][cY - 1])) {
                        if (isBlank(next)) {
//                            System.out.println("Direction 2, before return");
                            return true;
                        }
                    }
                    break;
                case 3: // - -
                    if (pieceIsMarble(gb[cX + 1][cY + 1])) {
                        if (isBlank(next)) {
//                            System.out.println("Direction 3, before return");
                            return true;
                        }
                    }
                    break;
                case 4: // + -
                    if (pieceIsMarble(gb[cX - 1][cY + 1])) {
                        if (isBlank(next)) {
//                            System.out.println("Direction 4, before return");
                            return true;
                        }
                    }
                    break;
                case 5: // <--
                    if (pieceIsMarble(gb[cX - 2][cY])) {
                        if (isBlank(next)) {
//                            System.out.println("Direction 5, before return");
                            return true;
                        }
                    }
                    break;
                case 6: // -->
                    if (pieceIsMarble(gb[cX + 2][cY])) {
                        if (isBlank(next)) {
//                            System.out.println("Direction 6, before return");
                            return true;
                        }
                    }
                    break;
                default:
                    return false;
            }
        }

        return false;
    } // end of canJump

    public boolean canJumpAgain(Piece piece) {
        JLabel p = piece.piece;
        for (int i = 0; i < gb.length; i++) {
            for (int y = 0; y < gb[0].length; y++) {
                if (gb[i][y] != null) {
                    if (canJump(piece, gb[i][y])) {
                        return true;
                    }
                }
            }
        }
        return false;
    } // end of canJumpAgain

    public boolean pieceIsMarble(Piece piece) {

        if (isMarble(piece)) {
//            System.out.println("Piece is marble: " + inner.getPieceX() + " "
//                    + inner.getPieceY());
            return true;
        }

        return false;
    } // end of pieceIsMarble

    //-----------------------------------------------------------------------------------------
    //PRINT FUNCTIONS -----------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------
    @Override
    public String toString() {
        String result = "\n";

        for (int y = 0; y < this.gb[0].length; y++) //scan through the columns in a row
        {
            for (int x = 0; x < this.gb.length; x++) //scan through the rows
            {

                if (this.gb[x][y] == null) {
                    result += " ";
                } else if (this.gb[x][y].getColour() == Colour.GREEN) {
                    result += "G";
                } else if (this.gb[x][y].getColour() == Colour.RED) {
                    result += "R";
                } else if (this.gb[x][y].getColour() == Colour.YELLOW) {
                    result += "Y";
                } else if (this.gb[x][y].getColour() == Colour.BLACK) {
                    result += "B";
                } else if (this.gb[x][y].getColour() == Colour.BLUE) {
                    result += "b";
                } else if (this.gb[x][y].getColour() == Colour.WHITE) {
                    result += "W";
                } else if (this.gb[x][y].getColour() == Colour.BLANK) {
                    result += "_";
                }
            } //end for
            result += "\n";								//add newline at end of each row
        }
        return result;
    }

    //this function checks if all the correctly coordinates correspond to the X and Y of the piece
    public String checkLocation() {
        String result = "\n";

        for (int y = 0; y < this.gb[0].length; y++) //scan through the columns in a row
        {
            for (int x = 0; x < this.gb.length; x++) //scan through the rows
            {

                if (this.gb[x][y] == null) {
                    result += " ";
                } else if (this.gb[x][y].getPieceX() == x && this.gb[x][y].getPieceY() == y) {
                    result += "$";
                } else if (!(this.gb[x][y].getPieceX() == x) && !(this.gb[x][y].getPieceY() == y)) {
                    result += "X";
                } else {
                    result += ".";
                }
            } //end for
            result += "\n";								//add newline at end of each row
        }
        return result;
    }

    public String toStringByHighlight() {
        String result = "\n";

        for (int y = 0; y < this.gb[0].length; y++) //scan through the columns in a row
        {
            for (int x = 0; x < this.gb.length; x++) //scan through the rows
            {
                if (this.gb[x][y] == null) {
                    result += " ";
                } else if (isHighlight(this.gb[x][y].piece)) {
                    result += "H";
                } else {
                    result += "-";
                }
            } //end for
            result += "\n";								//add newline at end of each row
        }
        return result;
    }

    public String toStringByIcons() {
        String result = "\n";

        for (int y = 0; y < this.gb[0].length; y++) //scan through the columns in a row
        {
            for (int x = 0; x < this.gb.length; x++) //scan through the rows
            {
                if (this.gb[x][y] == null) {
                    result += " ";
                } else if (this.gb[x][y].piece.getIcon().toString() == null ? o_grn.toString() == null : this.gb[x][y].piece.getIcon().toString().equals(o_grn.toString())) {
                    result += "G";
                } else if (this.gb[x][y].piece.getIcon().toString() == null ? o_red.toString() == null : this.gb[x][y].piece.getIcon().toString().equals(o_red.toString())) {
                    result += "R";
//                } else if (this.gb[x][y].inner.getIcon().toString() == null ? o_yel.toString() == null : this.gb[x][y].inner.getIcon().toString().equals(o_yel.toString())) {
//                    result += "Y";
//                } else if (this.gb[x][y].inner.getIcon().toString() == null ? o_blk.toString() == null : this.gb[x][y].inner.getIcon().toString().equals(o_blk.toString())) {
//                    result += "B";
//                } else if (this.gb[x][y].inner.getIcon().toString() == null ? o_blu.toString() == null : this.gb[x][y].inner.getIcon().toString().equals(o_blu.toString())) {
//                    result += "b";
//                } else if (this.gb[x][y].inner.getIcon().toString() == null ? o_wht.toString() == null : this.gb[x][y].inner.getIcon().toString().equals(o_wht.toString())) {
//                    result += "W";
                } else if (this.gb[x][y].piece.getIcon().toString() == null ? o_blank.toString() == null : this.gb[x][y].piece.getIcon().toString().equals(o_blank.toString())) {
                    result += "_";
                } else {
                    result += "+";
                }
            } //end for
            result += "\n";								//add newline at end of each row
        }
        return result;
    }

    public String toMarbleString() {
        String result = "\n";

        for (int y = 0; y < this.gb[0].length; y++) //scan through the columns in a row
        {
            for (int x = 0; x < this.gb.length; x++) //scan through the rows
            {
                if (this.gb[x][y] == null) {
                    result += " ";
//                } else if (isMarble(this.gb[x][y])) {
                } else if (this.gb[x][y].isDF) {
                    result += "T";
                } else if (!this.gb[x][y].isDF) {
                    result += "F";
                }
            } //end for
            result += "\n";								//add newline at end of each row
        }
        return result;
    }

    //-----------------------------------------------------------------------------------------
    //MINIMAX FUNCTIONS -----------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------
    @Override
    public List<ActionStatePair> successor(PlayerRole role) {

        List<ActionStatePair> result = new ArrayList<>();		//create empty list which will hold all next states

        //loop through the board
        for (int x = 0; x < this.gb.length; x++) {
            for (int y = 0; y < this.gb[x].length; y++) {

                if (this.gb[x][y] != null) {

                    //Successor for Minimax
                    if (this.gb[x][y].isMinimax == true) {
                        if (this.gb[x][y].isMax && role == PlayerRole.MAX) {
                            showMoves(this.gb[x][y]);
//                            System.out.println("Highlights " + this.toStringByHighlight());

                            for (int x1 = 0; x1 < this.gb.length; x1++) {
                                for (int y1 = 0; y1 < this.gb[x1].length; y1++) {
//                                    System.out.println("HIGHTLIGHTS" + this.toStringByHighlight());
                                    if (this.gb[x1][y1] != null) {
                                        if (isHighlight(this.gb[x1][y1].piece)) {
//                                            System.out.println("ORIGINAL " + x + ":" + y);
//                                            System.out.println("Highlights are: " + x1 + ":" + y1);

                                            Action action = new CCAction(x, y, x1, y1);
                                            CCState nextState = null;
                                            try {
                                                nextState = this.applyAction(action);
                                            } catch (CloneNotSupportedException ex) {
                                                Logger.getLogger(CCState.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                            //System.out.println("PRINTING NEXT STATE BEFORE ADDING: from " + x + ":" + y + " to " + x1 + ":" + y1 + "\n" + nextState.toMarbleString());
                                            //System.out.println("PRINING THIS STATE \n" + this.toString());
                                            result.add(new ActionStatePair(action, nextState));	//add action-state pair into result list
                                            this.gb[x1][y1].piece.setIcon(this.o_blank);
                                        }
                                    }
                                }
                            }
                        }

                        if (!this.gb[x][y].isMax && role == PlayerRole.MIN) {
                            showMoves(this.gb[x][y]);

                            //loop through the board again
                            for (int x1 = 0; x1 < this.gb.length; x1++) {
                                for (int y1 = 0; y1 < this.gb[x1].length; y1++) {

                                    if (this.gb[x1][y1] != null) {
                                        if (isHighlight(this.gb[x1][y1].piece)) {
//                                            System.out.println("ORIGINAL " + x + ":" + y);
//                                            System.out.println("Highlights are: " + x1 + ":" + y1);

                                            Action action = new CCAction(x, y, x1, y1);
                                            CCState nextState = null;
                                            try {
                                                nextState = this.applyAction(action);
                                            } catch (CloneNotSupportedException ex) {
                                                Logger.getLogger(CCState.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                            //System.out.println("PRINTING NEXT STATE BEFORE ADDING: from " + x + ":" + y + " to " + x1 + ":" + y1 + "\n" + nextState.toMarbleString());
                                            //System.out.println("PRINING THIS STATE \n" + this.toString());
                                            result.add(new ActionStatePair(action, nextState));	//add action-state pair into result list
                                            this.gb[x1][y1].piece.setIcon(this.o_blank);
                                        }
                                    }
                                }
                            }
                        }
                    } // end of isMinimax = true

                    //Successor for MC
                    if (this.gb[x][y].isMC && role == PlayerRole.MC) {
                        showMoves(this.gb[x][y]);

                        for (int x1 = 0; x1 < this.gb.length; x1++) {
                            for (int y1 = 0; y1 < this.gb[x1].length; y1++) {
                                if (this.gb[x1][y1] != null) {
                                    if (isHighlight(this.gb[x1][y1].piece)) {

                                        Action action = new CCAction(x, y, x1, y1);
                                        CCState nextState = null;
                                        try {
                                            nextState = this.applyAction(action);
                                        } catch (CloneNotSupportedException ex) {
                                            Logger.getLogger(CCState.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        result.add(new ActionStatePair(action, nextState));	//add action-state pair into result list
                                        this.gb[x1][y1].piece.setIcon(this.o_blank);
                                    }
                                }
                            }
                        }
                    } // end of isMC 

                    //Successor for DF
                    if (this.gb[x][y].isDF && role == PlayerRole.DF) {

                        showMoves(this.gb[x][y]);
//                        System.out.println("Highlights " + this.toStringByHighlight());

                        for (int x1 = 0; x1 < this.gb.length; x1++) {
                            for (int y1 = 0; y1 < this.gb[x1].length; y1++) {
                                if (this.gb[x1][y1] != null) {

                                    if (isHighlight(this.gb[x1][y1].piece)) {

                                        Action action = new CCAction(x, y, x1, y1);
                                        CCState nextState = null;
                                        try {
                                            nextState = this.applyAction(action);
                                        } catch (CloneNotSupportedException ex) {
                                            Logger.getLogger(CCState.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        result.add(new ActionStatePair(action, nextState));	//add action-state pair into result list
                                        this.gb[x1][y1].piece.setIcon(this.o_blank);
                                    }
                                }
                            }
                        }
                    } // end of isDF
                }
            }
        }
        return result;			//return list
    } //end method

    public List<ActionStatePair> successor() {
        return null;
    } //end method

    public CCState applyAction(Action action) throws CloneNotSupportedException {

        CCAction ccAction = (CCAction) action;
        CCState nextState1 = (CCState) this.copyState();
        Piece piece = nextState1.gb[ccAction.currentX][ccAction.currentY];

        nextState1.gb[ccAction.nextX][ccAction.nextY].piece.setIcon(piece.piece.getIcon());
        nextState1.gb[ccAction.nextX][ccAction.nextY].setColour(piece.getColour());
        nextState1.gb[ccAction.nextX][ccAction.nextY].setIsMinimax(piece.isMinimax);
        nextState1.gb[ccAction.nextX][ccAction.nextY].setIsMax(piece.isMax);
        nextState1.gb[ccAction.nextX][ccAction.nextY].setIsMC(piece.isMC);
        nextState1.gb[ccAction.nextX][ccAction.nextY].setIsDF(piece.isDF);
        nextState1.gb[ccAction.nextX][ccAction.nextY].setX(ccAction.nextX);
        nextState1.gb[ccAction.nextX][ccAction.nextY].setY(ccAction.nextY);

        nextState1.gb[ccAction.currentX][ccAction.currentY].piece.setIcon(o_blank);
        nextState1.gb[ccAction.currentX][ccAction.currentY].setColour(Colour.BLANK);
        nextState1.gb[ccAction.currentX][ccAction.currentY].setIsMinimax(false);
        nextState1.gb[ccAction.currentX][ccAction.currentY].setIsMax(false);
        nextState1.gb[ccAction.currentX][ccAction.currentY].setIsMC(false);
        nextState1.gb[ccAction.currentX][ccAction.currentY].setIsDF(false);

        //   nextSt.println("NEXT STATE 1 IS BELOW From " + ccAction.currentX + ":" + ccAction.currentY + " to " + ccAction.nextX + ":" + ccAction.nextY + " \n" + nextState1.toString());
        return nextState1;													//return new state

    } //end method

    public Object copyState() throws CloneNotSupportedException {

        CCState clone = new CCState();

        for (int x = 0; x < this.gb.length; x++) {
            for (int y = 0; y < this.gb[x].length; y++) {
                if (this.gb[x][y] != null) {

                    Piece piece = new Piece((Piece) this.gb[x][y].clone());
                    clone.gb[x][y] = piece;
                    clone.gb[x][y].piece.setIcon(this.gb[x][y].piece.getIcon());
                }
            }
        }

//        System.out.println("IN COPY STATE: THIS : " + this.toString());
//        System.out.println("IN COPY STATE: CLONE : " + clone.toStringByIcons());
        return clone;

    } //end method

    //-----------------------------------------------------------------------------------------
    //MCTS FUNCTIONS -----------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------    
    public PlayerRole winner() {
        if (this.checkIfAllAreTheSame(grn_arr, o_grn)) {
            return PlayerRole.MAX;
        }
        if (this.checkIfAllAreTheSame(red_arr, o_red)) {
            return PlayerRole.MC;
        }
        return PlayerRole.UNKNOWN; // No one has won
    }

} //end class
