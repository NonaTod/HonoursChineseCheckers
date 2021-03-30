/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechecker;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import chinesechecker.Board;

/**
 *
 * @author nonit
 */
public class Piece implements Cloneable{

    public JLabel piece;
    public int x;
    public int y;
    public Colour colour;
    public boolean isMinimax;
    public boolean isMax;
    

    public Piece(int x, int y) {
        this.piece = new JLabel();
        this.x = x;
        this.y = y;
        this.colour = Colour.BLANK;
        this.isMinimax = false;
        this.isMax = false;
    }
    
    public Piece(Piece newPiece) {
        this.piece = newPiece.piece;
        this.x = newPiece.x;
        this.y = newPiece.y;
        this.colour = newPiece.colour;
        this.isMinimax = newPiece.isMinimax;
        this.isMax = newPiece.isMax;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException{
        return (Piece) super.clone();
    }
    
    public JLabel getPiece() {
        return piece;
    }

    public void setPiece(JLabel piece) {
        this.piece = piece;
    }

    public int getPieceX() {
        return x;
    }

    public int getPieceY() {
        return y;
    }  

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }
    
    
}
