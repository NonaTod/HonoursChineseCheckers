 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechecker;

import javax.swing.ImageIcon;
import sun.security.krb5.internal.crypto.Nonce;

/**
 *
 * @author nonit
 */
public abstract class Player {

    public String name;
    public Colour colour;

    public Player(String name, Colour colour) {
        this.name = name;
        this.colour = colour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public abstract Action getAction(MiniMaxSearch problem, State state);

}
