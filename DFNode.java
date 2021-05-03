package chinesechecker;

public class DFNode {

    public CCState state;
    public DFNode parent;	
    public CCAction action;	

    
    /**
     * Creates a {@link Node} object.
     *
     * @param state The state of the problem this node represents.
     * @param parent The parent node leading to this node.
     * @param action The action that leads us from the parent to this node.
     */
    public DFNode(CCState state, DFNode parent, CCAction action) {
        this.state = state;
        this.parent = parent;
        this.action = action;
    } //end method

    /**
     * Return the cost of the path from the root node to this node.
     *
     * @return	The cost of the path (i.e. sum of all actions) from the root to
     * this node.
     */
    public double getCost() {
        if (this.parent == null) //this is the root node
        {
            return 0.0;			//0 cost to here
        }
        return this.action.cost + this.parent.getCost();	//otherwise recursively calculate the cost up to the root
    } //end method

    /**
     * Return the depth of this node.
     *
     * @return	The depth of the node from root. The root node has a depth of 0.
     */
    public int getDepth() {
        if (this.parent == null) //this is the root node
        {
            return 0;			//depth is 0
        }
        return this.parent.getDepth() + 1;
    } //end method
} //end class
