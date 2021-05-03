/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechecker;

import java.util.List;

/**
 *
 * @author nonit
 */
public class TestSuccessor {

public static void main(String[] arg) throws CloneNotSupportedException
{

CCState gameState = new CCState();
   

Action action = new CCAction(11,3,10,4);
    
gameState = gameState.applyAction((CCAction) action);
 System.out.println("INITIAL STATE:" + gameState.toString());
 
List<ActionStatePair> children = gameState.successor(PlayerRole.MIN);


System.out.print("\nChildren:" + children.size() + "\n");
System.out.println(children);


} //end method
} //end class   

