/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechecker;

import javax.swing.JLabel;

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
    public boolean  isMC;
    public boolean  isDF;
    public boolean isMax;
    

    public Piece(int x, int y) {
        this.piece = new JLabel();
        this.x = x;
        this.y = y;
        this.colour = Colour.BLANK;
        this.isMinimax = false;
        this.isMax = false;
        this.isMC = false;
        this.isDF = false;
    }
    
    public Piece(Piece newPiece) {
        this.piece = new JLabel(); 
        this.piece.setIcon(newPiece.piece.getIcon());
        this.x = newPiece.x;
        this.y = newPiece.y;
        this.colour = newPiece.colour;
        this.isMinimax = newPiece.isMinimax;
        this.isMax = newPiece.isMax;
        this.isMC = newPiece.isMC;
        this.isDF = newPiece.isDF;
    }
    
@Override
public Object clone() {
    try {
        return (Piece) super.clone();
    } catch (CloneNotSupportedException e) {
        return new Piece( this.getPieceX(), this.getPieceY());
    }
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isIsMinimax() {
        return isMinimax;
    }

    public void setIsMinimax(boolean isMinimax) {
        this.isMinimax = isMinimax;
    }

    public boolean isIsMax() {
        return isMax;
    }

    public void setIsMax(boolean isMax) {
        this.isMax = isMax;
    }

    public boolean isIsDF() {
        return isDF;
    }

    public void setIsDF(boolean isDF) {
        this.isDF = isDF;
    }

    public boolean isIsMC() {
        return isMC;
    }

    public void setIsMC(boolean isMC) {
        this.isMC = isMC;
    }
    
    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    @Override
    public String toString() {
        return "Piece{" + ", x=" + x + ", y=" + y + ", colour=" + colour + ", isMinimax=" + isMinimax + ", isMax=" + isMax + "piece=" + piece  + '}';
    }
    
    
    
    
}
