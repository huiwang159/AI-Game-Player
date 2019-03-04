package student_player;

import java.util.*;
import java.time.*;

import boardgame.Move;
import tablut.TablutBoardState;
import tablut.TablutPlayer;
import tablut.TablutMove;
import coordinates.Coord;
import coordinates.Coordinates;

/** A player file submitted by a student. */
public class StudentPlayer extends TablutPlayer {
    
    /**
     * You must modify this constructor to return your student number. This is
     * important, because this is what the code that runs the competition uses to
     * associate you with your agent. The constructor should do nothing else.
     */
    public StudentPlayer() {
        super("260620369");
    }

    /**
     * This is the primary method that you need to implement. The ``boardState``
     * object contains the current state of the game, which your agent must use to
     * make decisions.
     */
    public Move chooseMove(TablutBoardState boardState) {
        return MyTools.chooseMove(boardState);
    }
    
    public static void main(String[] args) {
        int win = 0;
        for (int i=0; i<10; i++) {
            TablutBoardState s = new TablutBoardState();
            while (!s.gameOver()) {
                s.processMove(MyTools.chooseMove(s));
                try {
                    s.processMove((TablutMove) MyTools.greedy(s));
                } catch (Exception e) {
                    
                }
            }
            win += s.getWinner();
            System.out.println(win);
        }
        System.out.println((float) win/10);
        
        win = 0;
        for (int i=0; i<10; i++) {
            TablutBoardState s = new TablutBoardState();
            while (!s.gameOver()) {
                s.processMove((TablutMove) MyTools.greedy(s));
                try {
                    s.processMove(MyTools.chooseMove(s));
                } catch (Exception e) {
                    
                }
            }
            win += s.getWinner();
            System.out.println(win);
        }
        System.out.println((float) win/10);
    }
    
}