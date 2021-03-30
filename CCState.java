
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechecker;

import java.util.*;
import chinesechecker.Board;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import sun.nio.cs.ext.GBK;

/**
 *
 * @author nonit
 */
public class CCState implements State, Cloneable {

    public Piece gb[][];

    public ImageIcon o_grn, o_blk, o_wht, o_yel, o_blu, o_red, o_blank;
    public ImageIcon s_grn, s_blk, s_wht, s_yel, s_blu, s_red;
    public ImageIcon h_grn, h_blk, h_wht, h_yel, h_blu, h_red;
    public ImageIcon d_grn, d_blk, d_wht, d_yel, d_blu, d_red;

    public ArrayList<Player> turnOrder;
    public Player currPlayer;
    public Player winner;
    public boolean gameStarted;
    public boolean gameOver;

    public int[][] grn_arr;
    public int[][] blk_arr;
    public int[][] wht_arr;
    public int[][] yel_arr;
    public int[][] blu_arr;
    public int[][] red_arr;

    int nextIndexGrn;
    int nextIndexBlk;
    int nextIndexWht;
    int nextIndexYel;
    int nextIndexBlu;
    int nextIndexRed;

    /**
     * Create an empty game board.
     */
    public CCState() {

        this.gb = new Piece[25][17];

        grn_arr = new int[2][10];
        blk_arr = new int[2][10];
        wht_arr = new int[2][10];
        yel_arr = new int[2][10];
        blu_arr = new int[2][10];
        red_arr = new int[2][10];

        nextIndexGrn = 0;
        nextIndexBlk = 0;
        nextIndexWht = 0;
        nextIndexYel = 0;
        nextIndexBlu = 0;
        nextIndexRed = 0;

        initImageIcons();
        autoInitGame();
    } //end method

    public void initImageIcons() {

        o_grn = new ImageIcon(getClass().getResource("../images/o_green_34.png"));
        o_blk = new ImageIcon(getClass().getResource("../images/o_black_34.png"));
        o_wht = new ImageIcon(getClass().getResource("../images/o_white_34.png"));
        o_yel = new ImageIcon(getClass().getResource("../images/o_yellow_34.png"));
        o_blu = new ImageIcon(getClass().getResource("../images/o_blue_34.png"));
        o_red = new ImageIcon(getClass().getResource("../images/o_red_34.png"));
        o_blank = new ImageIcon(getClass().getResource("../images/o_blank_34.png"));

        s_grn = new ImageIcon(getClass().getResource("../images/s_green_34.png"));
        s_blk = new ImageIcon(getClass().getResource("../images/s_black_34.png"));
        s_wht = new ImageIcon(getClass().getResource("../images/s_white_34.png"));
        s_yel = new ImageIcon(getClass().getResource("../images/s_yellow_34.png"));
        s_blu = new ImageIcon(getClass().getResource("../images/s_blue_34.png"));
        s_red = new ImageIcon(getClass().getResource("../images/s_red_34.png"));

        h_grn = new ImageIcon(getClass().getResource("../images/h_green_34.png"));
        h_blk = new ImageIcon(getClass().getResource("../images/h_black_34.png"));
        h_wht = new ImageIcon(getClass().getResource("../images/h_white_34.png"));
        h_yel = new ImageIcon(getClass().getResource("../images/h_yellow_34.png"));
        h_blu = new ImageIcon(getClass().getResource("../images/h_blue_34.png"));
        h_red = new ImageIcon(getClass().getResource("../images/h_red_34.png"));

        d_grn = new ImageIcon(getClass().getResource("../images/d_green_34.png"));
        d_blk = new ImageIcon(getClass().getResource("../images/d_black_34.png"));
        d_wht = new ImageIcon(getClass().getResource("../images/d_white_34.png"));
        d_yel = new ImageIcon(getClass().getResource("../images/d_yellow_34.png"));
        d_blu = new ImageIcon(getClass().getResource("../images/d_blue_34.png"));
        d_red = new ImageIcon(getClass().getResource("../images/d_red_34.png"));

    } // end of initImageIcons

    public void autoInitGame() {
        gameOver = false;
        gameStarted = true;
        winner = null;
        turnOrder = new ArrayList<Player>();

        Player player1 = new MiniMaxPlayer("Computer 1", Colour.GREEN, MiniMaxPlayerRole.MAX);
        Player player2 = new MiniMaxPlayer("Computer 2", Colour.RED, MiniMaxPlayerRole.MIN);
        currPlayer = player1;

        turnOrder.add(player1);
        turnOrder.add(player2);

        buildBoard();

    } // end of initGame

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

        //Creating the black pieces
        createPiece(0, 4, o_blk);
        createPiece(2, 4, o_blk);
        createPiece(4, 4, o_blk);
        createPiece(6, 4, o_blk);
        createPiece(1, 5, o_blk);
        createPiece(3, 5, o_blk);
        createPiece(5, 5, o_blk);
        createPiece(2, 6, o_blk);
        createPiece(4, 6, o_blk);
        createPiece(3, 7, o_blk);

        //Creating the yellow pieces
        createPiece(3, 9, o_yel);
        createPiece(2, 10, o_yel);
        createPiece(4, 10, o_yel);
        createPiece(1, 11, o_yel);
        createPiece(3, 11, o_yel);
        createPiece(5, 11, o_yel);
        createPiece(0, 12, o_yel);
        createPiece(2, 12, o_yel);
        createPiece(4, 12, o_yel);
        createPiece(6, 12, o_yel);

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
        createPiece(18, 4, o_wht);
        createPiece(20, 4, o_wht);
        createPiece(22, 4, o_wht);
        createPiece(24, 4, o_wht);
        createPiece(19, 5, o_wht);
        createPiece(21, 5, o_wht);
        createPiece(23, 5, o_wht);
        createPiece(20, 6, o_wht);
        createPiece(22, 6, o_wht);
        createPiece(21, 7, o_wht);

        //Creating the blue pieces
        createPiece(21, 9, o_blu);
        createPiece(20, 10, o_blu);
        createPiece(22, 10, o_blu);
        createPiece(19, 11, o_blu);
        createPiece(21, 11, o_blu);
        createPiece(23, 11, o_blu);
        createPiece(18, 12, o_blu);
        createPiece(20, 12, o_blu);
        createPiece(22, 12, o_blu);
        createPiece(24, 12, o_blu);

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

    public void createPiece(int x, int y, ImageIcon icon) {
//        System.out.println("Create piece: x:" + x);
//        System.out.println("y:" + y + " ImageIcon: " + icon);

        this.gb[x][y] = new Piece(x, y);
        this.gb[x][y].piece.setIcon(icon);

        if (icon == o_grn) {

            this.gb[x][y].setColour(Colour.GREEN);
            grn_arr[0][nextIndexGrn] = x;
            grn_arr[1][nextIndexGrn] = y;
            nextIndexGrn++;

        } else if (icon == o_blk) {

            this.gb[x][y].setColour(Colour.BLACK);
            blk_arr[0][nextIndexBlk] = x;
            blk_arr[1][nextIndexBlk] = y;
            nextIndexBlk++;

        } else if (icon == o_wht) {

            this.gb[x][y].setColour(Colour.WHITE);
            wht_arr[0][nextIndexWht] = x;
            wht_arr[1][nextIndexWht] = y;
            nextIndexWht++;

        } else if (icon == o_yel) {

            this.gb[x][y].setColour(Colour.YELLOW);
            yel_arr[0][nextIndexYel] = x;
            yel_arr[1][nextIndexYel] = y;
            nextIndexYel++;

        } else if (icon == o_blu) {

            this.gb[x][y].setColour(Colour.BLUE);
            blu_arr[0][nextIndexBlu] = x;
            blu_arr[1][nextIndexBlu] = y;
            nextIndexBlu++;

        } else if (icon == o_red) {

            this.gb[x][y].setColour(Colour.RED);
            red_arr[0][nextIndexRed] = x;
            red_arr[1][nextIndexRed] = y;
            nextIndexRed++;
        }

        //temporary code to assign minimax
        if (icon == o_grn) {
            this.gb[x][y].isMinimax = true;
            this.gb[x][y].isMax = true;
        }
        if (icon == o_red) {
            this.gb[x][y].isMinimax = true;
            this.gb[x][y].isMax = false;
        }

    }

    public void moveMarble(int currentX, int currentY, int nextX, int nextY) {
        Piece piece = this.gb[currentX][currentY];

        this.gb[nextX][nextY].piece.setIcon(piece.piece.getIcon());
        this.gb[nextX][nextY].setColour(piece.getColour());

        this.gb[currentX][currentY].piece.setIcon(o_blank);
        this.gb[currentX][currentY].setColour(Colour.BLANK);

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
            return true; 	// -->
        }
        return false;
    } // end of inRange

    public boolean isMarble(Piece p) {
        if (p != null){
            if (p.colour != Colour.BLANK){
                return true;
            }
        }
        return false;
    } // end of isMarble

    public boolean isHighlight(JLabel p) {
        if (p.getIcon() == h_grn) {
            return true;
        }
        if (p.getIcon() == h_wht) {
            return true;
        }
        if (p.getIcon() == h_blu) {
            return true;
        }
        if (p.getIcon() == h_red) {
            return true;
        }
        if (p.getIcon() == h_yel) {
            return true;
        }
        if (p.getIcon() == h_blk) {
            return true;
        }
        return false;
    } // end of isHighlight

    public boolean isDot(Piece piece) {
        JLabel p = piece.piece;
        if (p.getIcon() == d_grn) {
            return true;
        }
        if (p.getIcon() == d_wht) {
            return true;
        }
        if (p.getIcon() == d_blu) {
            return true;
        }
        if (p.getIcon() == d_red) {
            return true;
        }
        if (p.getIcon() == d_yel) {
            return true;
        }
        if (p.getIcon() == d_blk) {
            return true;
        }
        return false;
    } // end of isDot

    public ImageIcon getImageIcon(int i, char c) {

        ///////////////////////////////////////
        // Highlight
        if (c == 'h') {
            if (0 == i) {
                return h_grn;
            } else if (1 == i) {
                return h_wht;
            } else if (2 == i) {
                return h_blu;
            } else if (3 == i) {
                return h_red;
            } else if (4 == i) {
                return h_yel;
            } else if (5 == i) {
                return h_blk;
            }
        }

        ///////////////////////////////////////
        // Dot
        if (c == 'd') {
            if (0 == i) {
                return d_grn;
            } else if (1 == i) {
                return d_wht;
            } else if (2 == i) {
                return d_blu;
            } else if (3 == i) {
                return d_red;
            } else if (4 == i) {
                return d_yel;
            } else if (5 == i) {
                return d_blk;
            }
        }

        ///////////////////////////////////////
        // Select
        if (c == 's') {
            if (0 == i) {
                return s_grn;
            } else if (1 == i) {
                return s_wht;
            } else if (2 == i) {
                return s_blu;
            } else if (3 == i) {
                return s_red;
            } else if (4 == i) {
                return s_yel;
            } else if (5 == i) {
                return s_blk;
            }
        }

        ///////////////////////////////////////
        // Original
        if (c == 'o') {
            if (0 == i) {
                return o_grn;
            } else if (1 == i) {
                return o_wht;
            } else if (2 == i) {
                return o_blu;
            } else if (3 == i) {
                return o_red;
            } else if (4 == i) {
                return o_yel;
            } else if (5 == i) {
                return o_blk;
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

                    if (inRange(piece, gb[x][y]) || canJump(piece, gb[x][y])) {
                        if (piece != gb[x][y] && !isMarble(gb[x][y])) {

//                        System.out.println("Show moves, is in range: " + inRange(piece, gb[x][y]));
//                        System.out.println("Show moves, can jump: " + canJump(piece, gb[x][y]));
                            //System.out.println("Is a potential move X:Y:  " + x + ":" + y);

                            gb[x][y].piece.setIcon(getImageIcon(getColorInt(piece), 'h'));
//                        System.out.println("Just set highlight to: " + x + " " + y);
//                        System.out.println("Is it highlight: " + isHighlight(gb[x][y].piece));
                        }
                    }
                }
            }
        }
    } // end of showMoves

    public boolean isGameOver() {
        String lineSeparator = System.lineSeparator();
        StringBuilder sb = new StringBuilder();

        if (checkIfAllAreTheSame(grn_arr, o_grn)) {
            return true;
        } else if (checkIfAllAreTheSame(wht_arr, o_wht)) {
            return true;
        } else if (checkIfAllAreTheSame(blk_arr, o_blk)) {
            return true;
        } else if (checkIfAllAreTheSame(yel_arr, o_yel)) {
            return true;
        } else if (checkIfAllAreTheSame(blu_arr, o_blu)) {
            return true;
        } else if (checkIfAllAreTheSame(red_arr, o_red)) {
            return true;
        }

        return false;

    } // end of isGameOver

    public boolean checkIfAllAreTheSame(int[][] array, ImageIcon icon) {

        Piece first = gb[array[0][0]][array[1][0]];
//        System.out.println("checkIfAllAreTheSame: " + array[0][0] + " " + array[1][0]);
//
//        System.out.println("First icon: " + first.piece.getIcon());
//        System.out.println("Icon" + icon);

        if (first.piece.getIcon() == icon) {
            return false;
        }

        if (first.piece.getIcon() == o_blank) {
            return false;
        }

        for (int i = 1; i < 10; i++) {

            Piece second = gb[array[0][i]][array[1][i]];

            //if the color image of the first icon does not match all, 
            //then game is not over
            if (!(first.piece.getIcon() == second.piece.getIcon())) {
                return false;
            }

        }

        return true;
    }

    public int getColorInt(Piece piece) {
        JLabel p = piece.piece;
        if (p.getIcon() == o_grn || p.getIcon() == s_grn) {
            return 0;
        }
        if (p.getIcon() == o_wht || p.getIcon() == s_wht) {
            return 1;
        }
        if (p.getIcon() == o_blu || p.getIcon() == s_blu) {
            return 2;
        }
        if (p.getIcon() == o_red || p.getIcon() == s_red) {
            return 3;
        }
        if (p.getIcon() == o_yel || p.getIcon() == s_yel) {
            return 4;
        }
        if (p.getIcon() == o_blk || p.getIcon() == s_blk) {
            return 5;
        }
        if (p.getIcon() == o_blank) {
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
        if (p.getIcon() == s_wht) {
            return o_wht;
        }
        if (p.getIcon() == s_blu) {
            return o_blu;
        }
        if (p.getIcon() == s_red) {
            return o_red;
        }
        if (p.getIcon() == s_yel) {
            return o_yel;
        }
        if (p.getIcon() == s_blk) {
            return o_blk;
        }
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
//            System.out.println("Piece is marble: " + piece.getPieceX() + " "
//                    + piece.getPieceY());
            return true;
        }

        return false;
    } // end of pieceIsMarble

    public List<ActionStatePair> successor() {
        return null;
    } //end method

    public CCState applyAction(Action action) throws CloneNotSupportedException {

        CCAction ccAction = (CCAction) action;
//        System.out.println("In apply action: (" + this.board.gb[ccAction.currentX][ccAction.currentY].getColour() + ") " + ccAction.currentX + ":" + ccAction.currentY + " to " + ccAction.nextX + ":" + ccAction.nextY);

        //System.out.println("IN APPLY ACTION: this: \n" + this.toString());
        CCState nextState1 = (CCState) this.copyState();
        //System.out.println("IN APPLY ACTION: nextState1: \n" + nextState1.toString());

        Piece piece = nextState1.gb[ccAction.currentX][ccAction.currentY];

        nextState1.gb[ccAction.nextX][ccAction.nextY].piece.setIcon(piece.piece.getIcon());
        nextState1.gb[ccAction.nextX][ccAction.nextY].setColour(piece.getColour());
        //System.out.println("IN APPLY ACTION BEFORE SETTING TO BLANK: this: \n" + this.toString());
        //System.out.println("IN APPLY ACTION BEFORE SETTING TO BLANK: nextState1: \n" + nextState1.toString());

        nextState1.gb[ccAction.currentX][ccAction.currentY].piece.setIcon(o_blank);
        nextState1.gb[ccAction.currentX][ccAction.currentY].setColour(Colour.BLANK);

        //   nextState1.moveMarble(ccAction.currentX, ccAction.currentY, ccAction.nextX, ccAction.nextY);
        //System.out.println("IN APPLY ACTION AFTER ACTION: this: \n" + this.toString());
        //System.out.println("IN APPLY ACTION AFTER ACTION: nextState1: \n" + nextState1.toString());

//        System.out.println("NEXT STATE 1 IS BELOW From " + ccAction.currentX + ":" + ccAction.currentY + " to " + ccAction.nextX + ":" + ccAction.nextY + " \n" + nextState1.toString());
        return nextState1;													//return new state

    } //end method

    public Object copyState() throws CloneNotSupportedException {

        CCState clone = new CCState();

        for (int x = 0; x < gb.length; x++) {
            for (int y = 0; y < gb[x].length; y++) {
                if (gb[x][y] != null) {
                    Piece piece = new Piece((Piece) gb[x][y].clone());
                    clone.gb[x][y] = piece;
                }
            }
        }
        return clone;

    } //end method

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

    public String toMarbleString() {
        String result = "\n";

        for (int y = 0; y < this.gb[0].length; y++) //scan through the columns in a row
        {
            for (int x = 0; x < this.gb.length; x++) //scan through the rows
            {
                if (this.gb[x][y] == null) {
                    result += " ";
                } else if (isMarble(this.gb[x][y])) {
                    result += "T";
                } else if (!isMarble(this.gb[x][y])) {
                    result += "F";
                }
            } //end for
            result += "\n";								//add newline at end of each row
        }
        return result;
    }
    @Override
    public List<ActionStatePair> successor(MiniMaxPlayerRole role
    ) {

        List<ActionStatePair> result = new ArrayList<>();		//create empty list which will hold all next states

        //loop through the board
        for (int x = 0; x < this.gb.length; x++) {
            for (int y = 0; y < this.gb[x].length; y++) {

                if (this.gb[x][y] != null) {

                    if (this.gb[x][y].isMinimax == true) {

                        if (this.gb[x][y].isMax && role == MiniMaxPlayerRole.MAX) {

//                            System.out.println("Current MAX Piece is: " + x + " " + y);
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

                        if (!this.gb[x][y].isMax && role == MiniMaxPlayerRole.MIN) {
                            showMoves(gb[x][y]);

                            //loop through the board again
                            for (int x1 = 0; x1 < this.gb.length; x1++) {
                                for (int y1 = 0; y1 < this.gb[x1].length; y1++) {
                                    if (this.gb[x1][y1] != null) {
                                        if (this.isHighlight(this.gb[x1][y1].piece)) {
                                            CCAction action = new CCAction(x, y, x1, y1);		//put player symbol there
                                            //System.out.println("Action: " + action.toString());
                                            CCState nextState = null;
                                            try {
                                                nextState = this.applyAction(action); //apply action to get next state
                                            } catch (CloneNotSupportedException ex) {
                                                Logger.getLogger(CCState.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                            result.add(new ActionStatePair(action, nextState));	//add action-state pair into result list

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;			//return list
    } //end method
} //end class
