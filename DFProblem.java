/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechecker;

public class DFProblem extends DFSearch {
    

    public DFProblem(CCState initialState) {
        super(initialState);
    } //end method

    @Override
    public boolean isGoal(CCState state) {
        return state.isGameOverLong();
    } //end method
} //end class

