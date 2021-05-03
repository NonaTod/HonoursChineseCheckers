package chinesechecker;

public class Node {
	public Player player;
	public double value;
	public int visits, num_children, child_start;
	public Action last_move;
	
	public Node(int visits, double value, Action last_move, Player player) {
		this.visits = visits;
		this.value = value;
		this.last_move = last_move;
		this.player = player;
		num_children = 0;
	}
}