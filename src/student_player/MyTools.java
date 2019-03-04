package student_player;

import java.util.*;
import java.time.*;

import boardgame.Move;
import tablut.TablutBoardState;
import tablut.TablutPlayer;
import tablut.TablutMove;
import coordinates.Coord;
import coordinates.Coordinates;

public class MyTools {
    
//    private static Random rand = new Random();co
//    private static tableOfBestMoves b = new tableOfBestMoves(0);
//    private static tableOfBestMoves w = new tableOfBestMoves(1);
    
//    public static double getSomething() {
//        return Math.random();
//    }
    
//    public static boolean boardStatesEqual(TablutBoardState s1, TablutBoardState s2){
//        if (s1.getTurnPlayer()==s2.getTurnPlayer() && s1.getTurnNumber()==s2.getTurnNumber()){
//            if (s1.getPlayerPieceCoordinates().equals(s2.getPlayerPieceCoordinates()) && s1.getOpponentPieceCoordinates().equals(s2.getOpponentPieceCoordinates())){
//                        return true;
//                    } else {
//                        return false;
//                    }
//        } else {
//            return false;
//        }
//    }
    
    public static Move greedy(TablutBoardState bs) {
        List<TablutMove> options = bs.getAllLegalMoves();
        int player_id = bs.getTurnPlayer();

        // Set an initial move as some random one.
        Random rand = new Random();
        TablutMove bestMove = options.get(rand.nextInt(options.size()));
        
        // This greedy player seeks to capture as many opponents as possible.
        int opponent = bs.getOpponent();
        int minNumberOfOpponentPieces = bs.getNumberPlayerPieces(opponent);
        boolean moveCaptures = false;

        // Iterate over move options and evaluate them.
        for (TablutMove move : options) {
            // To evaluate a move, clone the boardState so that we can do modifications on
            // it.
            TablutBoardState cloneBS = (TablutBoardState) bs.clone();

            // Process that move, as if we actually made it happen.
            cloneBS.processMove(move);

            // Check how many opponent pieces there are now, maybe we captured some!
            int newNumberOfOpponentPieces = cloneBS.getNumberPlayerPieces(opponent);

            // If this move caused some capturing to happen, then do it! Greedy!
            if (newNumberOfOpponentPieces < minNumberOfOpponentPieces) {
                bestMove = move;
                minNumberOfOpponentPieces = newNumberOfOpponentPieces;
                moveCaptures = true;
            }

            /*
             * If we also want to check if the move would cause us to win, this works for
             * both! This will check if black can capture the king, and will also check if
             * white can move to a corner, since if either of these things happen then a
             * winner will be set.
             */
            if (cloneBS.getWinner() == player_id) {
                bestMove = move;
                moveCaptures = true;
                break;
            }
        }

        /*
         * The king-functionality below could be included in the above loop to improve
         * efficiency. But, this is written separately for the purpose of exposition to
         * students.
         */

        // If we are SWEDES we also want to check if we can get closer to the closest
        // corner. Greedy!
        // But let's say we'll only do this if we CANNOT do a capture.
        if (player_id == TablutBoardState.SWEDE && !moveCaptures) {
            Coord kingPos = bs.getKingPosition();

            // Don't do a move if it wouldn't get us closer than our current position.
            int minDistance = Coordinates.distanceToClosestCorner(kingPos);

            // Iterate over moves from a specific position, the king's position!
            for (TablutMove move : bs.getLegalMovesForPosition(kingPos)) {
                /*
                 * Here it is not necessary to actually process the move on a copied boardState.
                 * Note that it is more efficient NOT to copy the boardState. Consider this
                 * during implementation...
                 */
                int moveDistance = Coordinates.distanceToClosestCorner(move.getEndPosition());
                if (moveDistance < minDistance) {
                    minDistance = moveDistance;
                    bestMove = move;
                }
            }
        }
        return bestMove;
    } 
    
    public static Move greedyNew(TablutBoardState bs, TablutMove m) {
        List<TablutMove> options = bs.getAllLegalMoves();
        int player_id = bs.getTurnPlayer();

        // Set an initial move as some random one.
        TablutMove original = m;
        TablutMove bestMove = m;
        
        // This greedy player seeks to capture as many opponents as possible.
        int opponent = bs.getOpponent();
        int minNumberOfOpponentPieces = bs.getNumberPlayerPieces(opponent);
        boolean moveCaptures = false;

        // Iterate over move options and evaluate them.
        for (TablutMove move : options) {
            // To evaluate a move, clone the boardState so that we can do modifications on
            // it.
            TablutBoardState cloneBS = (TablutBoardState) bs.clone();

            // Process that move, as if we actually made it happen.
            cloneBS.processMove(move);

            // Check how many opponent pieces there are now, maybe we captured some!
            int newNumberOfOpponentPieces = cloneBS.getNumberPlayerPieces(opponent);

            // If this move caused some capturing to happen, then do it! Greedy!
            if (newNumberOfOpponentPieces < minNumberOfOpponentPieces) {
                bestMove = move;
                minNumberOfOpponentPieces = newNumberOfOpponentPieces;
                moveCaptures = true;
            }

            /*
             * If we also want to check if the move would cause us to win, this works for
             * both! This will check if black can capture the king, and will also check if
             * white can move to a corner, since if either of these things happen then a
             * winner will be set.
             */
            if (cloneBS.getWinner() == player_id) {
                bestMove = move;
                moveCaptures = true;
                break;
            }
        }

        /*
         * The king-functionality below could be included in the above loop to improve
         * efficiency. But, this is written separately for the purpose of exposition to
         * students.
         */

        // If we are SWEDES we also want to check if we can get closer to the closest
        // corner. Greedy!
        // But let's say we'll only do this if we CANNOT do a capture.
        if (player_id == TablutBoardState.SWEDE && !moveCaptures) {
            Coord kingPos = bs.getKingPosition();

            // Don't do a move if it wouldn't get us closer than our current position.
            int minDistance = Coordinates.distanceToClosestCorner(kingPos);

            // Iterate over moves from a specific position, the king's position!
            for (TablutMove move : bs.getLegalMovesForPosition(kingPos)) {
                /*
                 * Here it is not necessary to actually process the move on a copied boardState.
                 * Note that it is more efficient NOT to copy the boardState. Consider this
                 * during implementation...
                 */
                int moveDistance = Coordinates.distanceToClosestCorner(move.getEndPosition());
                if (moveDistance < minDistance) {
                    minDistance = moveDistance;
                    bestMove = move;
                }
            }
        }
        
        TablutBoardState bsc = (TablutBoardState) bs.clone();
        bsc.processMove((TablutMove) bestMove);
        for (Move mf: bsc.getAllLegalMoves()){
            TablutBoardState bscc = (TablutBoardState) bsc.clone();
            bscc.processMove((TablutMove) mf);
            if (bscc.gameOver() && bscc.getWinner() == opponent){
                return original;
            }
        }
        return bestMove;
    } 
       
//    public static TablutMove getBestMove(TablutBoardState s, int turnNumber, int turnPlayer){
//        
//        List<TablutBoardState> pres;
//        List<TablutMove> moves;
//        
//        if (turnPlayer==0){
//            // black's turn
//            if (turnNumber==0){
//                pres = b.prefirst;
//                moves = b.first;
//            } else {
//                pres = b.presecond;
//                moves = b.second;
//            }
//        } else {
//            // white's turn
//            if (turnNumber==0){
//                pres = w.prefirst;
//                moves = w.first;
//            } else {
//                pres = w.presecond;
//                moves = w.second;
//            }
//        }
//        
//        for (int i=0; i<pres.size(); i++){
//            if (boardStatesEqual(pres.get(i), s)){
//                return moves.get(i);
//            }
//        }
//        return (TablutMove)greedy(s);
//    }
    
    public static TablutMove blackChooseMove(TablutBoardState s){
        Instant before = Instant.now();
        
        float min = (float) 1.0;
        TablutMove bestMove = (TablutMove) greedy(s);
        
        for (Move m : s.getAllLegalMoves()){
            TablutBoardState sc = (TablutBoardState) s.clone();
            sc.processMove((TablutMove) m);
            int win = 0;
            for (int i=0; i<12; i++) {
                TablutBoardState scc = (TablutBoardState) sc.clone();
                while(!scc.gameOver()){
                    try {
                        TablutMove mc = (TablutMove) greedy(scc);
                        scc.processMove(mc);
                    } catch (Exception e) {
                    }
                }
                win += scc.getWinner();
            }
            float rate = (float) win/12;
            if (rate <  min){
            	min = rate;
                bestMove = (TablutMove) m;
            }
            if (Duration.between(before, Instant.now()).toMillis() > 1800){
                break;
            }
        }

        System.out.println(bestMove.toTransportable() + " " + Duration.between(before, Instant.now()).toMillis());
        return bestMove;
        
    }
    
    public static TablutMove whiteChooseMove(TablutBoardState s){
        
        
        Instant before = Instant.now();
        
        float max = (float) 0.0;
        TablutMove bestMove = (TablutMove) greedy(s);
        
        for (Move m : s.getAllLegalMoves()){
            TablutBoardState sc = (TablutBoardState) s.clone();
            sc.processMove((TablutMove) m);
            int win = 0;
            for (int i=0; i<12; i++) {
                TablutBoardState scc = (TablutBoardState) sc.clone();
                while(!scc.gameOver()){
                    try {
                        TablutMove mc = (TablutMove) greedy(scc);
                        scc.processMove(mc);
                    } catch (Exception e) {
                    }
                }
                win += scc.getWinner();
            }
            float rate = (float) win/12;
            if (rate > max){
            	max = rate;
                bestMove = (TablutMove) m;
            }
            if (Duration.between(before, Instant.now()).toMillis() > 1800){
                System.out.println("broke");
                break;
            }
        }
        System.out.println(bestMove.toTransportable() + " " + Duration.between(before, Instant.now()).toMillis());
        return bestMove;
    }
    
    public static TablutMove chooseMove(TablutBoardState s){
        int turn = s.getTurnNumber();
        int player = s.getTurnPlayer();
        if (turn < 0){
//            return getBestMove(s, turn, player);
            return (TablutMove) greedy(s);
        } else {
            TablutMove best;
            if (player==0){
            	best = blackChooseMove(s);
            } else {
            	best =  whiteChooseMove(s);
            }
            return (TablutMove) greedyNew(s, best) ;
        }
    }
    
    public static void main(String[] args) {
        
        int win = 0;
        for (int i=0; i<10; i++) {
            TablutBoardState s = new TablutBoardState();
            while (!s.gameOver()) {
                s.processMove(chooseMove(s));
                try {
                    s.processMove((TablutMove) greedy(s));
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
                s.processMove((TablutMove) greedy(s));
                try {
                    s.processMove(chooseMove(s));
                } catch (Exception e) {
                    
                }
            }
            win += s.getWinner();
            System.out.println(win);
        }
        System.out.println((float) win/10);
    }
}




