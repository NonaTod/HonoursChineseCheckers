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
public class TestCC {

public static void main(String[] arg)
{
    
CCState initialState = new CCState();					//create initial state of empty board

CCAlphaBetaProblem cc=new CCAlphaBetaProblem();	//create minimax problem instance

System.out.println(cc.utility(initialState,PlayerRole.MIN));
} //end method
} //end class   