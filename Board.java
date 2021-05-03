package chinesechecker;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;


public class Board extends JPanel {

    private int BOARD_SIZE = 620;
    public CCState state;

    //private Piece[] gb = new Piece[121];
    private JLabel modeLabels[] = new JLabel[4];
    private JLabel gameStatus1;
    private JLabel gameStatus2;
    private JButton endTurn;

    //Images
    private ImageIcon mode_0, mode_1, mode_2, mode_3, mode_4, mode_5, mode_6, mode_7, mode_8, mode_9;
    private ImageIcon boardBKG;
    private ImageIcon bkg;

    public int gameMode; // 0 - 8
    private JButton modes[] = new JButton[9];
    private SpringLayout boardLayout;

    //Handlers
    private BoardMouseHandler boardMouseHandler = new BoardMouseHandler();
    private OptionMouseHandler optionMouseHandler = new OptionMouseHandler();

    //////////////////////////////////////////////////////////////////////
    // Board constructor 
    public Board(CCState state) {
        this.state = state;
        boardLayout = new SpringLayout();
        setLayout(boardLayout);

        initModeImages();
        initGame();

    } // end of Board constructor
    //////////////////////////////////////////////////////////////////////
    // Based on the gameMode integer passed in it builds 

    public void setTurnOrder(int gameMode) {
        switch (gameMode) {

            // 2 Players
            case 0:
                Player maxPlayer = new MiniMaxPlayer("Max player", Colour.GREEN, PlayerRole.MAX);
                System.out.println("Player Colour after creation (MAX): " + maxPlayer.getName() + " " + maxPlayer.getColour());

                Player minPlayer = new MiniMaxPlayer("Min player", Colour.RED, PlayerRole.MIN);
                System.out.println("Player Colour after creation (MIN): " + minPlayer.getName() + " " + minPlayer.getColour());

                state.currPlayer = maxPlayer;

                state.turnOrder.add(maxPlayer);
                state.turnOrder.add(minPlayer);

                System.out.println("turnOrder: " + state.turnOrder.toString());

                break;

            case 1:
                Player maxPlayer1 = new MiniMaxPlayer("Max player", Colour.WHITE, PlayerRole.MAX);
                System.out.println("Player1 Colour after creation (MAX): " + maxPlayer1.getName() + " " + maxPlayer1.getColour());

                Player minPlayer1 = new MiniMaxPlayer("Min player", Colour.YELLOW, PlayerRole.MIN);
                System.out.println("Player2 Colour after creation (MIN): " + minPlayer1.getName() + " " + minPlayer1.getColour());

                state.currPlayer = maxPlayer1;

                state.turnOrder.add(maxPlayer1);
                state.turnOrder.add(minPlayer1);
                System.out.println("turnOrder: " + state.turnOrder.toString());

                break;

            case 2:
                Player maxPlayer2 = new MiniMaxPlayer("Max player", Colour.BLUE, PlayerRole.MAX);
                Player minPlayer2 = new MiniMaxPlayer("Min player", Colour.BLACK, PlayerRole.MIN);
                state.currPlayer = maxPlayer2;

                state.turnOrder.add(maxPlayer2);
                state.turnOrder.add(minPlayer2);

                break;

            /////////////////////
            // 3 Players
            case 3:
                Player Player1 = new HumanPlayer("TEST 1", Colour.GREEN);
                Player Player2 = new HumanPlayer("TEST 2", Colour.BLUE);
                Player Player3 = new HumanPlayer("TEST 3", Colour.YELLOW);
                state.currPlayer = Player1;

                state.turnOrder.add(Player1);
                state.turnOrder.add(Player2);
                state.turnOrder.add(Player3);

                break;

            case 4:
                Player Player4 = new HumanPlayer("TEST 4", Colour.WHITE);
                Player Player5 = new HumanPlayer("TEST 5", Colour.RED);
                Player Player6 = new HumanPlayer("TEST 6", Colour.BLACK);
                state.currPlayer = Player4;

                state.turnOrder.add(Player4);
                state.turnOrder.add(Player5);
                state.turnOrder.add(Player6);

                break;

            /////////////////////
            // 4 Players
            case 5:
                Player Player7 = new HumanPlayer("TEST 7", Colour.GREEN);
                Player Player8 = new HumanPlayer("TEST 8", Colour.WHITE);
                Player Player9 = new HumanPlayer("TEST 9", Colour.RED);
                Player Player10 = new HumanPlayer("TEST 10", Colour.YELLOW);

                state.currPlayer = Player7;

                state.turnOrder.add(Player7);
                state.turnOrder.add(Player8);
                state.turnOrder.add(Player9);
                state.turnOrder.add(Player10);

                break;

            case 6:
                Player Player11 = new HumanPlayer("TEST 11", Colour.WHITE);
                Player Player12 = new HumanPlayer("TEST 12", Colour.BLUE);
                Player Player13 = new HumanPlayer("TEST 13", Colour.YELLOW);
                Player Player14 = new HumanPlayer("TEST 14", Colour.BLACK);

                state.currPlayer = Player11;

                state.turnOrder.add(Player11);
                state.turnOrder.add(Player12);
                state.turnOrder.add(Player13);
                state.turnOrder.add(Player14);

                break;

            case 7:
                Player Player15 = new HumanPlayer("TEST 15", Colour.BLUE);
                Player Player16 = new HumanPlayer("TEST 16", Colour.RED);
                Player Player17 = new HumanPlayer("TEST 17", Colour.BLACK);
                Player Player18 = new HumanPlayer("TEST 18", Colour.GREEN);

                state.currPlayer = Player15;

                state.turnOrder.add(Player15);
                state.turnOrder.add(Player16);
                state.turnOrder.add(Player17);
                state.turnOrder.add(Player18);

                break;

            /////////////////////
            // 6 Players
            case 8:
                Player Player19 = new HumanPlayer("TEST 19", Colour.GREEN);
                Player Player20 = new HumanPlayer("TEST 20", Colour.WHITE);
                Player Player21 = new HumanPlayer("TEST 21", Colour.BLUE);
                Player Player22 = new HumanPlayer("TEST 22", Colour.RED);
                Player Player23 = new HumanPlayer("TEST 23", Colour.YELLOW);
                Player Player24 = new HumanPlayer("TEST 24", Colour.BLACK);

                state.currPlayer = Player19;

                state.turnOrder.add(Player19);
                state.turnOrder.add(Player20);
                state.turnOrder.add(Player21);
                state.turnOrder.add(Player22);
                state.turnOrder.add(Player23);
                state.turnOrder.add(Player24);

                break;

            default:
                break;
        }
    } // end of setTurnOrder

    //////////////////////////////////////////////////////////////////////
    // Initialize major game control variables
    public void initGame() {
        bkg = boardBKG;
        
        for (int x = 0; x < state.gb.length; x++) {
            for (int y = 0; y < state.gb[0].length; y++) {
                if (state.gb[x][y] != null) {
                    state.gb[x][y].piece.addMouseListener(boardMouseHandler);
                    add(state.gb[x][y].piece);
                }
            }
        }

        buildConstraints();

    } // end of initGame

    //////////////////////////////////////////////////////////////////////
    // Shows the game options that intially show up at start of game
    public void showOptions() {
        endTurn.setVisible(false);
        gameStatus1.setVisible(false);
        gameStatus2.setVisible(false);

        modeLabels[0].setVisible(true);
        modeLabels[1].setVisible(true);
        modeLabels[2].setVisible(true);
        modeLabels[3].setVisible(true);

        modes[0].setVisible(true);
        modes[1].setVisible(true);
        modes[2].setVisible(true);
        modes[3].setVisible(true);
        modes[4].setVisible(true);
        modes[5].setVisible(true);
        modes[6].setVisible(true);
        modes[7].setVisible(true);
        modes[8].setVisible(true);
    } // end of showOptions

    //////////////////////////////////////////////////////////////////////
    // Hides the game options that intially show up at start of game
    public void hideOptions() {
        endTurn.setVisible(true);
        gameStatus1.setVisible(true);
        gameStatus2.setVisible(true);

        modeLabels[0].setVisible(false);
        modeLabels[1].setVisible(false);
        modeLabels[2].setVisible(false);
        modeLabels[3].setVisible(false);

        modes[0].setVisible(false);
        modes[1].setVisible(false);
        modes[2].setVisible(false);
        modes[3].setVisible(false);
        modes[4].setVisible(false);
        modes[5].setVisible(false);
        modes[6].setVisible(false);
        modes[7].setVisible(false);
        modes[8].setVisible(false);
    } // end of hideOptions

    public void updatePlayer(Player player) {

        System.out.println("In Update Player. Colour: " + player.colour);
        if (player.getColour() != Colour.BLANK) {
            System.out.println("Player Colour1: " + player.getName() + " " + player.getColour());
            switch (player.getColour()) {
                case GREEN:
                    gameStatus1.setText("Green's");
                    gameStatus1.setForeground(Color.GREEN);
                    break;
                case WHITE:
                    gameStatus1.setText("White's");
                    gameStatus1.setForeground(Color.GRAY);
                    break;
                case BLUE:
                    gameStatus1.setText("Blue's");
                    gameStatus1.setForeground(Color.BLUE);
                    break;
                case RED:
                    gameStatus1.setText("Red's");
                    gameStatus1.setForeground(Color.RED);
                    break;
                case YELLOW:
                    gameStatus1.setText("Yellow's");
                    gameStatus1.setForeground(Color.YELLOW);
                    break;
                case BLACK:
                    gameStatus1.setText("Black's");
                    gameStatus1.setForeground(Color.BLACK);
                    break;
                default:
                    break;
            }
        }
    }
    //////////////////////////////////////////////////////////////////////
    // Builds the turn information elements to be displayed

    public void buildTurnInfo() {

        //"Player"
        gameStatus1 = new JLabel();
        //"Turn"
        gameStatus2 = new JLabel();

        gameStatus1.setFont(new Font("Tahoma", Font.BOLD, 30));
        gameStatus2.setFont(new Font("Tahoma", Font.BOLD, 30));

        //Setting the text for the labels
        System.out.println("Calling update Player here");
        updatePlayer(state.currPlayer);
        gameStatus2.setText("Turn");

        //Adding and formatting the labels on the board
        add(gameStatus1);
        add(gameStatus2);
        boardLayout.putConstraint(SpringLayout.NORTH, gameStatus1,
                10, SpringLayout.NORTH, this);
        boardLayout.putConstraint(SpringLayout.WEST, gameStatus1,
                10, SpringLayout.WEST, this);

        boardLayout.putConstraint(SpringLayout.NORTH, gameStatus2,
                0, SpringLayout.SOUTH, gameStatus1);
        boardLayout.putConstraint(SpringLayout.WEST, gameStatus2,
                0, SpringLayout.WEST, gameStatus1);

        endTurn = new JButton("End Turn");

        endTurn.setPreferredSize(new Dimension(135, 35));
        endTurn.setFocusPainted(false);
        endTurn.setMargin(new Insets(0, 0, 0, 0));
        endTurn.setFont(new Font("Tahoma", Font.BOLD, 25));
        endTurn.addMouseListener(boardMouseHandler);
        endTurn.setBackground(new Color(59, 89, 182));
        endTurn.setForeground(Color.WHITE);

        boardLayout.putConstraint(SpringLayout.SOUTH, endTurn,
                -10, SpringLayout.SOUTH, this);
        boardLayout.putConstraint(SpringLayout.EAST, endTurn,
                -10, SpringLayout.EAST, this);

        add(endTurn);

        endTurn.setVisible(false);
        gameStatus1.setVisible(false);
        gameStatus2.setVisible(false);

    } // end of buildTurnInfo

    //////////////////////////////////////////////////////////////////////
    // Builds the option objects for gameMode selection
    public void buildOptions() {

        for (int i = 0; i < 9; i++) {
            modes[i] = new JButton();
            modes[i].setPreferredSize(new Dimension(25, 25));
            modes[i].setFocusPainted(false);
            modes[i].setMargin(new Insets(0, 0, 0, 0));
            modes[i].setFont(new Font("Tahoma", Font.BOLD, 12));
            modes[i].addMouseListener(optionMouseHandler);
            modes[i].setBackground(new Color(59, 89, 182));
            modes[i].setForeground(Color.WHITE);
            add(modes[i]);
        }

        for (int i = 0; i < 4; i++) {
            modeLabels[i] = new JLabel();
            modeLabels[i].setFont(new Font("Tahoma", Font.BOLD, 14));
            add(modeLabels[i]);
        }

        //////////////////////////////////////////////////////////////////
        modeLabels[0].setText("2 PLAYERS");

        boardLayout.putConstraint(SpringLayout.NORTH, modeLabels[0],
                15, SpringLayout.NORTH, this);
        boardLayout.putConstraint(SpringLayout.WEST, modeLabels[0],
                15, SpringLayout.WEST, this);

        modes[0].setText("1");
        modes[1].setText("2");
        modes[2].setText("3");

        boardLayout.putConstraint(SpringLayout.NORTH, modes[0],
                5, SpringLayout.SOUTH, modeLabels[0]);
        boardLayout.putConstraint(SpringLayout.WEST, modes[0],
                0, SpringLayout.WEST, modeLabels[0]);

        boardLayout.putConstraint(SpringLayout.NORTH, modes[1],
                0, SpringLayout.NORTH, modes[0]);
        boardLayout.putConstraint(SpringLayout.WEST, modes[1],
                5, SpringLayout.EAST, modes[0]);

        boardLayout.putConstraint(SpringLayout.NORTH, modes[2],
                0, SpringLayout.NORTH, modes[1]);
        boardLayout.putConstraint(SpringLayout.WEST, modes[2],
                5, SpringLayout.EAST, modes[1]);

        //////////////////////////////////////////////////////////////////
        modeLabels[1].setText("3 PLAYERS");

        boardLayout.putConstraint(SpringLayout.NORTH, modeLabels[1],
                -62, SpringLayout.SOUTH, this);
        boardLayout.putConstraint(SpringLayout.WEST, modeLabels[1],
                15, SpringLayout.WEST, this);

        modes[3].setText("1");
        modes[4].setText("2");

        boardLayout.putConstraint(SpringLayout.NORTH, modes[3],
                5, SpringLayout.SOUTH, modeLabels[1]);
        boardLayout.putConstraint(SpringLayout.WEST, modes[3],
                0, SpringLayout.WEST, modeLabels[1]);

        boardLayout.putConstraint(SpringLayout.NORTH, modes[4],
                0, SpringLayout.NORTH, modes[3]);
        boardLayout.putConstraint(SpringLayout.WEST, modes[4],
                5, SpringLayout.EAST, modes[3]);

        //////////////////////////////////////////////////////////////////
        modeLabels[2].setText("4 PLAYERS");

        boardLayout.putConstraint(SpringLayout.NORTH, modeLabels[2],
                15, SpringLayout.NORTH, this);
        boardLayout.putConstraint(SpringLayout.EAST, modeLabels[2],
                -15, SpringLayout.EAST, this);

        modes[5].setText("1");
        modes[6].setText("2");
        modes[7].setText("3");

        boardLayout.putConstraint(SpringLayout.NORTH, modes[5],
                5, SpringLayout.SOUTH, modeLabels[2]);
        boardLayout.putConstraint(SpringLayout.WEST, modes[5],
                0, SpringLayout.WEST, modeLabels[2]);

        boardLayout.putConstraint(SpringLayout.NORTH, modes[6],
                0, SpringLayout.NORTH, modes[5]);
        boardLayout.putConstraint(SpringLayout.WEST, modes[6],
                5, SpringLayout.EAST, modes[5]);

        boardLayout.putConstraint(SpringLayout.NORTH, modes[7],
                0, SpringLayout.NORTH, modes[6]);
        boardLayout.putConstraint(SpringLayout.WEST, modes[7],
                5, SpringLayout.EAST, modes[6]);

        //////////////////////////////////////////////////////////////////
        modeLabels[3].setText("6 PLAYERS");

        boardLayout.putConstraint(SpringLayout.NORTH, modeLabels[3],
                -62, SpringLayout.SOUTH, this);
        boardLayout.putConstraint(SpringLayout.EAST, modeLabels[3],
                -15, SpringLayout.EAST, this);

        modes[8].setText("1");

        boardLayout.putConstraint(SpringLayout.NORTH, modes[8],
                5, SpringLayout.SOUTH, modeLabels[3]);
        boardLayout.putConstraint(SpringLayout.WEST, modes[8],
                0, SpringLayout.WEST, modeLabels[3]);
    } // end of buildOptions

    //////////////////////////////////////////////////////////////////////
    // builds the springlayout for the board
    public void buildConstraints() {
        int SIZE = 34;
        int BELOW = 0;
        int RIGHT = 5;
        int OFFSET = 20;

        //laying the pieces on the board
        boardLayout.putConstraint(SpringLayout.NORTH, state.gb[12][0].piece,
                20, SpringLayout.WEST, this);
        boardLayout.putConstraint(SpringLayout.WEST, state.gb[12][0].piece,
                295, SpringLayout.WEST, this);

        boardLayout.putConstraint(SpringLayout.NORTH, state.gb[11][1].piece,
                BELOW, SpringLayout.SOUTH, state.gb[12][0].piece);
        boardLayout.putConstraint(SpringLayout.NORTH, state.gb[13][1].piece,
                BELOW, SpringLayout.SOUTH, state.gb[12][0].piece);

        boardLayout.putConstraint(SpringLayout.WEST, state.gb[11][1].piece,
                -OFFSET, SpringLayout.WEST, state.gb[12][0].piece);
        boardLayout.putConstraint(SpringLayout.WEST, state.gb[13][1].piece,
                RIGHT, SpringLayout.EAST, state.gb[11][1].piece);

        boardLayout.putConstraint(SpringLayout.NORTH, state.gb[10][2].piece,
                BELOW, SpringLayout.SOUTH, state.gb[11][1].piece);
        boardLayout.putConstraint(SpringLayout.NORTH, state.gb[12][2].piece,
                BELOW, SpringLayout.SOUTH, state.gb[11][1].piece);
        boardLayout.putConstraint(SpringLayout.NORTH, state.gb[14][2].piece,
                BELOW, SpringLayout.SOUTH, state.gb[11][1].piece);

        boardLayout.putConstraint(SpringLayout.WEST, state.gb[10][2].piece,
                -OFFSET, SpringLayout.WEST, state.gb[11][1].piece);
        boardLayout.putConstraint(SpringLayout.WEST, state.gb[12][2].piece,
                RIGHT, SpringLayout.EAST, state.gb[10][2].piece);
        boardLayout.putConstraint(SpringLayout.WEST, state.gb[14][2].piece,
                RIGHT, SpringLayout.EAST, state.gb[12][2].piece);

        boardLayout.putConstraint(SpringLayout.NORTH, state.gb[9][3].piece,
                BELOW, SpringLayout.SOUTH, state.gb[10][2].piece);
        boardLayout.putConstraint(SpringLayout.NORTH, state.gb[11][3].piece,
                BELOW, SpringLayout.SOUTH, state.gb[10][2].piece);
        boardLayout.putConstraint(SpringLayout.NORTH, state.gb[13][3].piece,
                BELOW, SpringLayout.SOUTH, state.gb[10][2].piece);
        boardLayout.putConstraint(SpringLayout.NORTH, state.gb[15][3].piece,
                BELOW, SpringLayout.SOUTH, state.gb[10][2].piece);

        boardLayout.putConstraint(SpringLayout.WEST, state.gb[9][3].piece,
                -OFFSET, SpringLayout.WEST, state.gb[10][2].piece);
        boardLayout.putConstraint(SpringLayout.WEST, state.gb[11][3].piece,
                RIGHT, SpringLayout.EAST, state.gb[9][3].piece);
        boardLayout.putConstraint(SpringLayout.WEST, state.gb[13][3].piece,
                RIGHT, SpringLayout.EAST, state.gb[11][3].piece);
        boardLayout.putConstraint(SpringLayout.WEST, state.gb[15][3].piece,
                RIGHT, SpringLayout.EAST, state.gb[13][3].piece);

        for (int i = 0; i < 25; i = i + 2) {
            boardLayout.putConstraint(SpringLayout.NORTH, state.gb[i][4].piece,
                    BELOW, SpringLayout.SOUTH, state.gb[9][3].piece);
        }

        boardLayout.putConstraint(SpringLayout.EAST, state.gb[0][4].piece,
                -RIGHT, SpringLayout.WEST, state.gb[2][4].piece);
        boardLayout.putConstraint(SpringLayout.EAST, state.gb[2][4].piece,
                -RIGHT, SpringLayout.WEST, state.gb[4][4].piece);
        boardLayout.putConstraint(SpringLayout.EAST, state.gb[4][4].piece,
                -RIGHT, SpringLayout.WEST, state.gb[6][4].piece);
        boardLayout.putConstraint(SpringLayout.EAST, state.gb[6][4].piece,
                -RIGHT, SpringLayout.WEST, state.gb[8][4].piece);
        boardLayout.putConstraint(SpringLayout.WEST, state.gb[8][4].piece,
                -OFFSET, SpringLayout.WEST, state.gb[9][3].piece);
        boardLayout.putConstraint(SpringLayout.WEST, state.gb[10][4].piece,
                RIGHT, SpringLayout.EAST, state.gb[8][4].piece);
        boardLayout.putConstraint(SpringLayout.WEST, state.gb[12][4].piece,
                RIGHT, SpringLayout.EAST, state.gb[10][4].piece);
        boardLayout.putConstraint(SpringLayout.WEST, state.gb[14][4].piece,
                RIGHT, SpringLayout.EAST, state.gb[12][4].piece);
        boardLayout.putConstraint(SpringLayout.WEST, state.gb[16][4].piece,
                RIGHT, SpringLayout.EAST, state.gb[14][4].piece);
        boardLayout.putConstraint(SpringLayout.WEST, state.gb[18][4].piece,
                RIGHT, SpringLayout.EAST, state.gb[16][4].piece);
        boardLayout.putConstraint(SpringLayout.WEST, state.gb[20][4].piece,
                RIGHT, SpringLayout.EAST, state.gb[18][4].piece);
        boardLayout.putConstraint(SpringLayout.WEST, state.gb[22][4].piece,
                RIGHT, SpringLayout.EAST, state.gb[20][4].piece);
        boardLayout.putConstraint(SpringLayout.WEST, state.gb[24][4].piece,
                RIGHT, SpringLayout.EAST, state.gb[22][4].piece);

        boardLayout.putConstraint(SpringLayout.NORTH, state.gb[1][5].piece,
                BELOW, SpringLayout.SOUTH, state.gb[0][4].piece);
        boardLayout.putConstraint(SpringLayout.WEST, state.gb[1][5].piece,
                OFFSET, SpringLayout.WEST, state.gb[0][4].piece);

        for (int i = 3; i < 24; i = i + 2) {
            boardLayout.putConstraint(SpringLayout.NORTH, state.gb[i][5].piece,
                    BELOW, SpringLayout.SOUTH, state.gb[0][4].piece);
            boardLayout.putConstraint(SpringLayout.WEST, state.gb[i][5].piece,
                    RIGHT, SpringLayout.EAST, state.gb[i - 2][5].piece);
        }

        boardLayout.putConstraint(SpringLayout.NORTH, state.gb[2][6].piece,
                BELOW, SpringLayout.SOUTH, state.gb[1][5].piece);
        boardLayout.putConstraint(SpringLayout.WEST, state.gb[2][6].piece,
                OFFSET, SpringLayout.WEST, state.gb[1][5].piece);

        for (int i = 4; i < 23; i = i + 2) {
            boardLayout.putConstraint(SpringLayout.NORTH, state.gb[i][6].piece,
                    BELOW, SpringLayout.SOUTH, state.gb[1][5].piece);
            boardLayout.putConstraint(SpringLayout.WEST, state.gb[i][6].piece,
                    RIGHT, SpringLayout.EAST, state.gb[i - 2][6].piece);
        }

        boardLayout.putConstraint(SpringLayout.NORTH, state.gb[3][7].piece,
                BELOW, SpringLayout.SOUTH, state.gb[2][6].piece);
        boardLayout.putConstraint(SpringLayout.WEST, state.gb[3][7].piece,
                OFFSET, SpringLayout.WEST, state.gb[2][6].piece);

        for (int i = 5; i < 22; i = i + 2) {
            boardLayout.putConstraint(SpringLayout.NORTH, state.gb[i][7].piece,
                    BELOW, SpringLayout.SOUTH, state.gb[2][6].piece);
            boardLayout.putConstraint(SpringLayout.WEST, state.gb[i][7].piece,
                    RIGHT, SpringLayout.EAST, state.gb[i - 2][7].piece);
        }

        boardLayout.putConstraint(SpringLayout.NORTH, state.gb[4][8].piece,
                BELOW, SpringLayout.SOUTH, state.gb[3][7].piece);
        boardLayout.putConstraint(SpringLayout.WEST, state.gb[4][8].piece,
                OFFSET, SpringLayout.WEST, state.gb[3][7].piece);

        for (int i = 6; i < 21; i = i + 2) {
            boardLayout.putConstraint(SpringLayout.NORTH, state.gb[i][8].piece,
                    BELOW, SpringLayout.SOUTH, state.gb[3][7].piece);
            boardLayout.putConstraint(SpringLayout.WEST, state.gb[i][8].piece,
                    RIGHT, SpringLayout.EAST, state.gb[i - 2][8].piece);
        }

        boardLayout.putConstraint(SpringLayout.NORTH, state.gb[3][9].piece,
                BELOW, SpringLayout.SOUTH, state.gb[4][8].piece);
        boardLayout.putConstraint(SpringLayout.WEST, state.gb[3][9].piece,
                -OFFSET, SpringLayout.WEST, state.gb[4][8].piece);

        for (int i = 5; i < 22; i = i + 2) {
            boardLayout.putConstraint(SpringLayout.NORTH, state.gb[i][9].piece,
                    BELOW, SpringLayout.SOUTH, state.gb[4][8].piece);
            boardLayout.putConstraint(SpringLayout.WEST, state.gb[i][9].piece,
                    RIGHT, SpringLayout.EAST, state.gb[i - 2][9].piece);
        }

        boardLayout.putConstraint(SpringLayout.NORTH, state.gb[2][10].piece,
                BELOW, SpringLayout.SOUTH, state.gb[3][9].piece);
        boardLayout.putConstraint(SpringLayout.WEST, state.gb[2][10].piece,
                -OFFSET, SpringLayout.WEST, state.gb[3][9].piece);

        for (int i = 4; i < 23; i = i + 2) {
            boardLayout.putConstraint(SpringLayout.NORTH, state.gb[i][10].piece,
                    BELOW, SpringLayout.SOUTH, state.gb[3][9].piece);
            boardLayout.putConstraint(SpringLayout.WEST, state.gb[i][10].piece,
                    RIGHT, SpringLayout.EAST, state.gb[i - 2][10].piece);
        }

        boardLayout.putConstraint(SpringLayout.NORTH, state.gb[1][11].piece,
                BELOW, SpringLayout.SOUTH, state.gb[2][10].piece);
        boardLayout.putConstraint(SpringLayout.WEST, state.gb[1][11].piece,
                -OFFSET, SpringLayout.WEST, state.gb[2][10].piece);

        for (int i = 3; i < 24; i = i + 2) {
            boardLayout.putConstraint(SpringLayout.NORTH, state.gb[i][11].piece,
                    BELOW, SpringLayout.SOUTH, state.gb[2][10].piece);
            boardLayout.putConstraint(SpringLayout.WEST, state.gb[i][11].piece,
                    RIGHT, SpringLayout.EAST, state.gb[i - 2][11].piece);
        }

        boardLayout.putConstraint(SpringLayout.NORTH, state.gb[0][12].piece,
                BELOW, SpringLayout.SOUTH, state.gb[1][11].piece);
        boardLayout.putConstraint(SpringLayout.WEST, state.gb[0][12].piece,
                -OFFSET, SpringLayout.WEST, state.gb[1][11].piece);

        for (int i = 2; i < 25; i = i + 2) {
            boardLayout.putConstraint(SpringLayout.NORTH, state.gb[i][12].piece,
                    BELOW, SpringLayout.SOUTH, state.gb[1][11].piece);
            boardLayout.putConstraint(SpringLayout.WEST, state.gb[i][12].piece,
                    RIGHT, SpringLayout.EAST, state.gb[i - 2][12].piece);
        }

        boardLayout.putConstraint(SpringLayout.NORTH, state.gb[9][13].piece,
                BELOW, SpringLayout.SOUTH, state.gb[8][12].piece);
        boardLayout.putConstraint(SpringLayout.WEST, state.gb[9][13].piece,
                OFFSET, SpringLayout.WEST, state.gb[8][12].piece);

        for (int i = 11; i < 16; i = i + 2) {
            boardLayout.putConstraint(SpringLayout.NORTH, state.gb[i][13].piece,
                    BELOW, SpringLayout.SOUTH, state.gb[8][12].piece);
            boardLayout.putConstraint(SpringLayout.WEST, state.gb[i][13].piece,
                    RIGHT, SpringLayout.EAST, state.gb[i - 2][13].piece);
        }

        boardLayout.putConstraint(SpringLayout.NORTH, state.gb[10][14].piece,
                BELOW, SpringLayout.SOUTH, state.gb[9][13].piece);
        boardLayout.putConstraint(SpringLayout.WEST, state.gb[10][14].piece,
                OFFSET, SpringLayout.WEST, state.gb[9][13].piece);

        for (int i = 12; i < 15; i = i + 2) {
            boardLayout.putConstraint(SpringLayout.NORTH, state.gb[i][14].piece,
                    BELOW, SpringLayout.SOUTH, state.gb[9][13].piece);
            boardLayout.putConstraint(SpringLayout.WEST, state.gb[i][14].piece,
                    RIGHT, SpringLayout.EAST, state.gb[i - 2][14].piece);
        }

        boardLayout.putConstraint(SpringLayout.NORTH, state.gb[11][15].piece,
                BELOW, SpringLayout.SOUTH, state.gb[10][14].piece);
        boardLayout.putConstraint(SpringLayout.WEST, state.gb[11][15].piece,
                OFFSET, SpringLayout.WEST, state.gb[10][14].piece);

        boardLayout.putConstraint(SpringLayout.NORTH, state.gb[13][15].piece,
                BELOW, SpringLayout.SOUTH, state.gb[10][14].piece);
        boardLayout.putConstraint(SpringLayout.WEST, state.gb[13][15].piece,
                RIGHT, SpringLayout.EAST, state.gb[11][15].piece);

        boardLayout.putConstraint(SpringLayout.NORTH, state.gb[12][16].piece,
                BELOW, SpringLayout.SOUTH, state.gb[11][15].piece);
        boardLayout.putConstraint(SpringLayout.WEST, state.gb[12][16].piece,
                OFFSET, SpringLayout.WEST, state.gb[11][15].piece);
    }

    //////////////////////////////////////////////////////////////////////
    // returns true if all pieces of colourIndex1 are in the original spots of b
    //////////////////////////////////////////////////////////////////////
    // returns true if e is 1 move distance from s
    //////////////////////////////////////////////////////////////////////
    // returns true p has another jump move avaible
    //////////////////////////////////////////////////////////////////////
    // highlights only the jumpable spaces for JLabel p
    //////////////////////////////////////////////////////////////////////
    // returns an int to match p based on its original or select image
    //////////////////////////////////////////////////////////////////////
    // returns ImageIcon type, color or mode based on passed i and c
    //////////////////////////////////////////////////////////////////////
    // returns the original image for a passed in JLabel if
    // p is a selected marble
    //////////////////////////////////////////////////////////////////////
    // performs variable changes based on gameMode and currPlayer
    // also checks if the game is over based on last move
    public void turnOver() {

        int currIndex = 0;

        for (int i = 0; i < state.turnOrder.size(); i++) {
            if (state.turnOrder.get(i) == state.currPlayer) {
                currIndex = i;
                System.out.println("Current index: " + currIndex);
            }
        }

        // if the number of players exceeds 3 then the way
        // or is determined changes 
        if (gameMode > 4) {
            System.out.println("In this loop");
            if (currIndex == state.turnOrder.size() - 1) {
                state.currPlayer = state.turnOrder.get(0);
            } else {
                state.currPlayer = state.turnOrder.get(currIndex + 1);
            }
        } else {
            System.out.println("In that loop");
            if (currIndex < (state.turnOrder.size() - 1)) {
                state.currPlayer = state.turnOrder.get(currIndex + 1);
            } else {
                state.currPlayer = state.turnOrder.get(0);
            }
        }

        updatePlayer(state.currPlayer);

        if (state.isGameOver()) {
            state.winner = state.currPlayer;
            initGame();
            showOptions();
            System.out.printf("Player %d Wins!!!\n", state.winner);
            state.gameOver = true;
        }
    } // end of turnOver

    //////////////////////////////////////////////////////////////////////
    // paints the background image on the Board JPanel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Paints the background
        g.drawImage(bkg.getImage(), 0, 0, null);
    }

    //////////////////////////////////////////////////////////////////////
    // sets the size of the JPanel to match the size of board 
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_SIZE, BOARD_SIZE);
    }

    private void initModeImages() {
        mode_0 = new ImageIcon(getClass().getResource("../images/mode_0.jpg"));
        mode_1 = new ImageIcon(getClass().getResource("../images/mode_1.jpg"));
        mode_2 = new ImageIcon(getClass().getResource("../images/mode_2.jpg"));
        mode_3 = new ImageIcon(getClass().getResource("../images/mode_3.jpg"));
        mode_4 = new ImageIcon(getClass().getResource("../images/mode_4.jpg"));
        mode_5 = new ImageIcon(getClass().getResource("../images/mode_5.jpg"));
        mode_6 = new ImageIcon(getClass().getResource("../images/mode_6.jpg"));
        mode_7 = new ImageIcon(getClass().getResource("../images/mode_7.jpg"));
        mode_8 = new ImageIcon(getClass().getResource("../images/mode_8.jpg"));

        boardBKG = new ImageIcon(getClass().getResource("../images/board_620.jpg"));
    }

    //////////////////////////////////////////////////////////////////////
    // Mouse listener for board elements 
    class BoardMouseHandler implements MouseListener {

        private Piece start;
        private Piece end;
        private Piece jumping;
        private Piece firstMoved;

        private boolean hasJumped = false;

        // MOUSE PRESSED ON PIECE TO MOVE
        public void mousePressed(MouseEvent event) {
            if (event.getSource() != endTurn) {
                JLabel label = (JLabel) event.getSource();
                System.out.println("event: " + label.getIcon());

                start = findPieceFromJLabel(label);
                System.out.println("GAME OVER: " + state.gameOver);
                System.out.println("GAME STARTED: " + state.gameStarted);
                System.out.println("IS TURN: " + state.isTurn(start));
                System.out.println("START COLOUR: " + start.getColour());

//                System.out.println("start: " + start.getPieceX() + " " + start.getPieceY());
                if (!state.gameOver && state.gameStarted && state.isTurn(start)
                        && !state.isBlank(start)
                        && state.isMarble(start)
                        && firstMoved == null) {
//                    System.out.println("Im here 1");
                    start.piece.setIcon(state.getImageIcon(state.getColorInt(start), 's'));
                    state.showMoves(start);

                }

                //can jump more than once
                if (!state.gameOver && state.gameStarted && state.isTurn(start)
                        && !state.isBlank(start)
                        && state.isMarble(start)
                        && firstMoved == start) {
//                    System.out.println("Im here 2");
                    start.piece.setIcon(state.getImageIcon(state.getColorInt(start), 's'));
                    state.showJumps(start);
                }
            }

            if (event.getSource() == endTurn) {
                hasJumped = false;
                jumping = null;
                firstMoved = null;
                turnOver();
            }

        }

        // ENDING MOUSE RELEASE 
        public void mouseReleased(MouseEvent event) {
            boolean moveMade = false;

            try {
                //MOVES 
                if (state.isDot(end)) {
                    end.piece.setIcon(state.getMarble(start));
                    end.setColour(start.getColour());
                    start.setColour(Colour.BLANK);
                    moveMade = true;
                    if (!state.inRange(start, end)) {
                        hasJumped = true;
                    }
                    firstMoved = end;
                }
            } catch (NullPointerException e) {
                moveMade = false;
            }

            state.hideMoves(start);

            // MOVE if labels are not the same, 
            // is blankspace and move is in range
            if (!moveMade) { // IF NO MOVE MADE RESET START TO ORIGINAL
                start.piece.setIcon(state.getImageIcon(state.getColorInt(start), 'o'));
            } else { // ELSE SET START TO BLANK
                // if jump still available 
                if (state.canJumpAgain(end) && hasJumped) {
                    hasJumped = true;

                    start.piece.setIcon(state.o_blank); // blank image

                    start = end;
                    jumping = start;
                    end = null;

                } else {
                    hasJumped = false;
                    jumping = null;
                    firstMoved = null;
                    start.piece.setIcon(state.o_blank); // blank image
                    turnOver();
                }
            }
        }

        public Piece findPieceFromJLabel(JLabel label) {
            for (int i = 0; i < state.gb.length; i++) {
                for (int j = 0; j < state.gb[0].length; j++) {
                    if (state.gb[i][j] != null) {
                        if (state.gb[i][j].piece == label) {
                            return state.gb[i][j];
                        }
                    }
                }
            }
            return null;
        }

        // SETS THE END POINT 
        public void mouseEntered(MouseEvent event) {
            if (event.getSource() != endTurn) {
                JLabel label = (JLabel) event.getSource();
                end = findPieceFromJLabel(label);

                if (state.isHighlight(label)) {
                    if (label.getIcon() == state.h_grn) {
                        end.piece.setIcon(state.d_grn);
//                    } else if (label.getIcon() == state.h_wht) {
//                        end.piece.setIcon(state.d_wht);
//                    } else if (label.getIcon() == state.h_yel) {
//                        end.piece.setIcon(state.d_yel);
//                    } else if (label.getIcon() == state.h_blu) {
//                        end.piece.setIcon(state.d_blu);
                    } else if (label.getIcon() == state.h_red) {
                        end.piece.setIcon(state.d_red);
//                    } else if (label.getIcon() == state.h_blk) {
//                        end.piece.setIcon(state.d_blk);
                    }
                }
            }
        }

        // handle event when mouse exits area
        public void mouseExited(MouseEvent event) {
            if (event.getSource() != endTurn) {
                JLabel label = (JLabel) event.getSource();
                end = findPieceFromJLabel(label);
                if (state.isDot(end)) {
                    if (label.getIcon() == state.d_grn) {
                        end.piece.setIcon(state.h_grn);
//                    } else if (label.getIcon() == state.d_wht) {
//                        end.piece.setIcon(state.h_wht);
//                    } else if (label.getIcon() == state.d_yel) {
//                        end.piece.setIcon(state.h_yel);
//                    } else if (label.getIcon() == state.d_blu) {
//                        end.piece.setIcon(state.h_blu);
                    } else if (label.getIcon() == state.d_red) {
                        end.piece.setIcon(state.h_red);
//                    } else if (label.getIcon() == state.d_blk) {
//                        end.piece.setIcon(state.h_blk);
                    }
                }
            }
        }

        public void mouseClicked(MouseEvent event) {
        }

    } // end of BoardMouseHandler class

    //////////////////////////////////////////////////////////////////////
    // Mouse listener for game option buttons
    public class OptionMouseHandler implements MouseListener {

        public void mousePressed(MouseEvent e) {
            if (e.getSource() == modes[0]) {
                gameMode = 0;
                setTurnOrder(gameMode);
                updatePlayer(state.currPlayer);
                state.gameStarted = true;
                hideOptions();
            }
            if (e.getSource() == modes[1]) {
                gameMode = 1;
                setTurnOrder(gameMode);
                updatePlayer(state.currPlayer);
                state.gameStarted = true;
                hideOptions();
            }
            if (e.getSource() == modes[2]) {
                gameMode = 2;
                setTurnOrder(gameMode);
                updatePlayer(state.currPlayer);
                state.gameStarted = true;
                hideOptions();
            }
            if (e.getSource() == modes[3]) {
                gameMode = 3;
                setTurnOrder(gameMode);
                updatePlayer(state.currPlayer);
                state.gameStarted = true;
                hideOptions();
            }
            if (e.getSource() == modes[4]) {
                gameMode = 4;
                setTurnOrder(gameMode);
                updatePlayer(state.currPlayer);
                state.gameStarted = true;
                hideOptions();
            }
            if (e.getSource() == modes[5]) {
                gameMode = 5;
                setTurnOrder(gameMode);
                updatePlayer(state.currPlayer);
                state.gameStarted = true;
                hideOptions();
            }
            if (e.getSource() == modes[6]) {
                gameMode = 6;
                setTurnOrder(gameMode);
                updatePlayer(state.currPlayer);
                state.gameStarted = true;
                hideOptions();
            }
            if (e.getSource() == modes[7]) {
                gameMode = 7;
                setTurnOrder(gameMode);
                updatePlayer(state.currPlayer);
                state.gameStarted = true;
                hideOptions();
            }
            if (e.getSource() == modes[8]) {
                gameMode = 8;
                setTurnOrder(gameMode);
                updatePlayer(state.currPlayer);
                state.gameStarted = true;
                hideOptions();
            }

        }

        public void mouseReleased(MouseEvent e) {
        }

        //for the game modes
        public void mouseEntered(MouseEvent e) {
            if (e.getSource() == modes[0]) {
                bkg = state.getImageIcon(0, 'm');
            }
            if (e.getSource() == modes[1]) {
                bkg = state.getImageIcon(1, 'm');
            }
            if (e.getSource() == modes[2]) {
                bkg = state.getImageIcon(2, 'm');
            }
            if (e.getSource() == modes[3]) {
                bkg = state.getImageIcon(3, 'm');
            }
            if (e.getSource() == modes[4]) {
                bkg = state.getImageIcon(4, 'm');
            }
            if (e.getSource() == modes[5]) {
                bkg = state.getImageIcon(5, 'm');
            }
            if (e.getSource() == modes[6]) {
                bkg = state.getImageIcon(6, 'm');
            }
            if (e.getSource() == modes[7]) {
                bkg = state.getImageIcon(7, 'm');
            }
            if (e.getSource() == modes[8]) {
                bkg = state.getImageIcon(8, 'm');
            }

            repaint();
        }

        public void mouseExited(MouseEvent e) {
            bkg = boardBKG;
            repaint();
        }

        public void mouseClicked(MouseEvent e) {

        }
    } // end of OptionMouseHandler class

} // end of Board class

