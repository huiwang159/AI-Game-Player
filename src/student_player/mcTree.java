package student_player;

import java.util.Random;

import boardgame.Move;
import tablut.TablutBoardState;
import tablut.TablutPlayer;
import tablut.TablutMove;
import coordinates.Coord;
import coordinates.Coordinates;
import java.util.*;

class mcTree {
	public static Node root;
	
	public mcTree(TablutBoardState s){
		root = new Node(null, null);
		
		for (TablutMove m : s.getAllLegalMoves()){
			Node child = new Node(root, (TablutMove) m);
			root.children.add(child);
			TablutBoardState sc = (TablutBoardState) s.clone();
			sc.processMove(m);
			
			for (TablutMove mc: sc.getAllLegalMoves()){
				Node childsChild = new Node(child, (TablutMove) mc);
				child.children.add(childsChild);
			}
		}
	}
	
	

	public static void main(String[] args) {
		TablutBoardState b = new TablutBoardState();
		mcTree mcts = new mcTree(b);
//		System.out.println(mcTree.root.children.get(0).children.get(1).move.toTransportable());
		
		
		
	}
}
