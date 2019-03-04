package student_player;

import boardgame.Move;
import tablut.TablutBoardState;
import tablut.TablutPlayer;
import tablut.TablutMove;
import coordinates.Coord;
import coordinates.Coordinates;

import java.util.*;

public class Node {
	
	Node parent;
	List<Node> children;
	int player;
	
	TablutMove move;
	String transportable;
	
	List<Float> qsa = new ArrayList<Float>();	// value of childen moves
	List<Float> ns = new ArrayList<Float>();	// number of times we have visited this node in simulations
	List<Float> nsa = new ArrayList<Float>();	// number of times we have simulated the child's move
	
	public Node(Node par, TablutMove m) {
		parent = par;
		children = new ArrayList<Node>();
		try {
			player = m.getPlayerID();
		} catch (Exception e) {
			player = 0;
		}
		move = m;
		
		qsa = (float) 0.0;
		ns  = 0;
		nsa = 0;
		
		if (move != null){
			transportable = move.toTransportable();
		}
	}
	
	public static void main(String[] args) {
		TablutBoardState n = new TablutBoardState();
		Node root = new Node(null, null);
		System.out.println(root.children.size());
	}
}
