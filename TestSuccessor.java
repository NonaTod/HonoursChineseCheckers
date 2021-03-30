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

public static void main(String[] arg)
{

CCState state=new CCState();
//System.out.println(state.toString());

List<ActionStatePair> children = state.successor(MiniMaxPlayerRole.MAX);

System.out.print("\nChildren:\n");
System.out.println(children);

} //end method
} //end class   

