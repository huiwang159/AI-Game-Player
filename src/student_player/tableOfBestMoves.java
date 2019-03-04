package student_player;

import java.util.Random;

import boardgame.Move;
import tablut.TablutBoardState;
import tablut.TablutPlayer;
import tablut.TablutMove;
import coordinates.Coord;
import coordinates.Coordinates;

import java.util.*;
import java.util.Scanner;
import java.io.*;

class tableOfBestMoves {
	
	TablutBoardState s = new TablutBoardState();;
	List<TablutMove> first = new ArrayList<TablutMove>();
	List<TablutMove> second = new ArrayList<TablutMove>();
	List<TablutBoardState> prefirst = new ArrayList<TablutBoardState>();
	List<TablutBoardState> presecond = new ArrayList<TablutBoardState>();
	
	public tableOfBestMoves(int player){
		if (player==0){
		// load black's best moves
			// first move
			TablutBoardState sc = (TablutBoardState)s.clone();
			prefirst.add((TablutBoardState) sc);
			
			TablutMove blackFirst = new TablutMove(3, 0, 3, 3, 0);
			first.add(blackFirst);
			
			// second move
			File file = new File("black_second.csv");
			try {
				Scanner scn = new Scanner(file);
				while(scn.hasNextLine()){
					
					String moves = scn.nextLine();
					
					String whiteFirst = moves.substring(0,4);
					String blackSecond = moves.substring(5,9);
					
					TablutMove whiteFirstMove = new TablutMove(Character.getNumericValue(whiteFirst.charAt(0)), Character.getNumericValue(whiteFirst.charAt(1)), Character.getNumericValue(whiteFirst.charAt(2)), Character.getNumericValue(whiteFirst.charAt(3)), 1);
					
					TablutBoardState scc = (TablutBoardState)s.clone();
					scc.processMove(new TablutMove(3, 0, 3, 3, 0));
					scc.processMove((TablutMove) whiteFirstMove);
					presecond.add((TablutBoardState) scc);	
					
					TablutMove blackSecondMove = new TablutMove(Character.getNumericValue(blackSecond.charAt(0)), Character.getNumericValue(blackSecond.charAt(1)), Character.getNumericValue(blackSecond.charAt(2)), Character.getNumericValue(blackSecond.charAt(3)), 0);
					
					second.add(blackSecondMove);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			
		} else {
		// load white's best moves
			// load white's best first moves
			File file = new File("white_first.csv");
			try {
				Scanner scn = new Scanner(file);
				while(scn.hasNextLine()){
					
					String moves = scn.nextLine();
					
					String blackFirst = moves.substring(0,4);
					String whiteFirst = moves.substring(5,9);
					
					TablutMove blackFirstMove = new TablutMove(Character.getNumericValue(blackFirst.charAt(0)), Character.getNumericValue(blackFirst.charAt(1)), Character.getNumericValue(blackFirst.charAt(2)), Character.getNumericValue(blackFirst.charAt(3)), 0);
					TablutMove whiteFirstMove = new TablutMove(Character.getNumericValue(whiteFirst.charAt(0)), Character.getNumericValue(whiteFirst.charAt(1)), Character.getNumericValue(whiteFirst.charAt(2)), Character.getNumericValue(whiteFirst.charAt(3)), 1);
					
					TablutBoardState sc = (TablutBoardState)s.clone();
					sc.processMove((TablutMove) blackFirstMove);
					prefirst.add((TablutBoardState) sc);
					
					first.add((TablutMove) whiteFirstMove);					
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			
			// load white's best second moves
			file = new File("white_second.csv");
			try {
				Scanner scn = new Scanner(file);
				while(scn.hasNextLine()){
										
					String moves = scn.nextLine();
										
					String blackFirst = moves.substring(0,4);
					String whiteFirst = moves.substring(5,9);
					String blackSecond = moves.substring(10,14);
					String whiteSecond = moves.substring(15,19);
					
					TablutBoardState sc = (TablutBoardState)s.clone();
					
					TablutMove blackFirstMove = new TablutMove(Character.getNumericValue(blackFirst.charAt(0)), Character.getNumericValue(blackFirst.charAt(1)), Character.getNumericValue(blackFirst.charAt(2)), Character.getNumericValue(blackFirst.charAt(3)), 0);
					
					TablutMove whiteFirstMove = new TablutMove(Character.getNumericValue(whiteFirst.charAt(0)), Character.getNumericValue(whiteFirst.charAt(1)), Character.getNumericValue(whiteFirst.charAt(2)), Character.getNumericValue(whiteFirst.charAt(3)), 1);
					
					TablutMove blackSecondMove = new TablutMove(Character.getNumericValue(blackSecond.charAt(0)), Character.getNumericValue(blackSecond.charAt(1)), Character.getNumericValue(blackSecond.charAt(2)), Character.getNumericValue(blackSecond.charAt(3)), 0);
									
					sc.processMove((TablutMove) blackFirstMove);
					sc.processMove((TablutMove) whiteFirstMove);
					sc.processMove((TablutMove) blackSecondMove);
					presecond.add(sc);
					
					TablutMove whiteSecondMove = new TablutMove(Character.getNumericValue(whiteSecond.charAt(0)), Character.getNumericValue(whiteSecond.charAt(1)), Character.getNumericValue(whiteSecond.charAt(2)), Character.getNumericValue(whiteSecond.charAt(3)), 1);
					second.add((TablutMove) whiteSecondMove);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
	public static void main(String[] args) {
		tableOfBestMoves b = new tableOfBestMoves(0);
		tableOfBestMoves w = new tableOfBestMoves(1);
		
		System.out.println(b.first.size() + " " + b.prefirst.size());
		System.out.println(b.second.size() + " " + b.presecond.size());
		System.out.println(w.first.size() + " " + w.prefirst.size());
		System.out.println(w.second.size() + " " + w.presecond.size());
	}
}



