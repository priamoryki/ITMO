package game;

import java.util.Random;

/**
 * @author Pavel Lymar
 */
public class Match {
	private final Player player1, player2;
	private final int i, j, m, n, k, numOfGames;
	
	Match(Player player1, Player player2, int i, int j, int numOfGames, int m, int n, int k) {
		this.player1 = player1;
		this.player2 = player2;
		this.i = i;
		this.j = j;
		this.numOfGames = numOfGames;
		this.m = m;
		this.n = n;
		this.k = k;
	}
	
	public int simulateMatch() {
		int result1 = 0;
		int result2 = 0;
		System.out.print("-------------------------\n");
		for (int e = 0; e < numOfGames; e++) {
			final Game game;
	    	int r = new Random().nextInt(2);
	    	if (r == 0) {
	    		game = new Game(false, player1, player2);
	    		System.out.print((i + 1) + " playing with " + (j + 1) + ' ');
	    	} else {
	    		game = new Game(false, player2, player1);
	    		System.out.print((j + 1) + " playing with " + (i + 1) + ' ');
	    	}
	        int result;
	        do {
	        	result = game.play(new TicTacToeBoard(m, n, k));
	            System.out.println("Game result: " + result);
	        } while (result == -1);
	        
	        if (result == 1) {
	        	if (r == 0) {
	        		result1 += 1;
	        	} else {
	        		result2 += 1;
	        	}
	        } else if (result == 2) {
	        	if (r == 0) {
	        		result2 += 1;
	        	} else {
	        		result1 += 1;
	        	}
	        }
		}
		
		if (result1 == result2) {
			return 0;
		} else if (result1 > result2) {
			return 1;
		} else {
			return 2;
		}
	}
}
