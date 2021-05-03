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
public class CCAction extends Action {
    
    public int currentX;
    public int currentY;
    public int nextX;
    public int nextY;
    public int nodes;
    
    public CCAction(int currentX, int currentY, int nextX, int nextY)
    {
        this.currentX = currentX;
        this.currentY = currentY;
        this.nextX = nextX;
        this.nextY = nextY;
        this.nodes = 0;
    }
    
    public CCAction()
    {
        this.currentX = -1;
        this.currentY = -1;
        this.nextX = -1;
        this.nextY = -1;
        this.nodes = -1;
    }
    
    public String toString()
    {
        String output = "";
        output += "Moving marble from " + currentX + ":" + currentY;
        output += " to " + nextX + ":" + nextY;
        
        return output;
    }
    
}
