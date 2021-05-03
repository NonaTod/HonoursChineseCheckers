package chinesechecker;
import java.util.*;


public class Path extends LinkedList<ActionStatePair>
{
/**
 * The root node of the path.
 */
public CCState head;

/**
 * The cost of the path from root to the final node/goal.
 */
public double cost;

/**
 * Creates an empty path with no head node and 0 cost.
 */
public Path()
{
this.head=null;
this.cost=0.0;
}

/**
 * Prints the path, with each node and action.
 * The output is controlled by the {@link cm3038.search.State#toString() toString()} method
 * of the {@link State} objects and {@link Action} objects,
 * which can be customised in the domain specific sub-classes.
 */
public void print()
{
if (head==null)
	return;
System.out.println(head.toString()+"\n");
Iterator<ActionStatePair> i=this.iterator();
while (i.hasNext())
	{
	ActionStatePair next=i.next();
	System.out.println(next.action.toString());
	System.out.println(next.state.toString());
	System.out.println();
	}
} //end method
} //end class
