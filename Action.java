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
public abstract class Action {

   
    public double cost;
    public int nodes;

    public Action() {
        this.cost = 1.0;
        this.nodes = 0;
    } 

    
    public abstract String toString();
}
