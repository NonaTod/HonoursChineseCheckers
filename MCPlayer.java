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
public class MCPlayer extends Player {

    PlayerRole role;

    public MCPlayer(String name, Colour colour, PlayerRole role) {
        super(name, colour, role);
    }

    @Override
    public Action getAction(MiniMaxSearch problem, State state) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
