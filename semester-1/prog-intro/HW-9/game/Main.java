package game;

import java.util.Scanner;

/**
 * @author Pavel Lymar
 */
public class Main {
    public static void main(String[] args) {
    	int m = 3;
    	int n = 3;
    	int k = 3;
    	//HumanPlayer()
    	//RandomPlayer(m, n)
        /*final Game game = new Game(true, new RandomPlayer(m, n), new RandomPlayer(m, n));
        int result;
        do {
            result = game.play(new TicTacToeBoard(m, n, k));
            System.out.println("Game result: " + result);
        } while (result == -1);*/
        
    	
        //...Tournament...//
    	Reader reader = new Reader(new Scanner(System.in));
        int t = reader.readInt("Enter the number of players:");
        int c = reader.readInt("Enter the number of circles:");
        int r = reader.readInt("Enter the number of matches:");
        
        Player[] players = new Player[t];
        for (int i = 0; i < t; i++) {
        	Player pl = new RandomPlayer(m, n);
        	players[i] = pl;
        }
        
        Tournament tour = new Tournament(players, c, r, m, n, k);
        tour.simulateFullTournament();
        System.out.println(tour);
    }
}