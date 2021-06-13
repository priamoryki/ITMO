package game;

import java.util.HashMap;

/**
 * @author Pavel Lymar
 */
public class Tournament {
	private int numOfCircles, numOfGames, m, n, k;
	Player[] players;
	private HashMap<Player, Integer> scores;
	
	Tournament(Player[] players, int c, int w, int m, int n, int k) {
		this.numOfCircles = c;
		this.numOfGames = w;
		this.m = m;
		this.n = n;
		this.k = k;
		this.players = players;
		this.scores = new HashMap<>();
		for (int i = 0; i < players.length; i++) {
        	Player pl = players[i];
        	scores.put(pl, 0);
        }
	}
	
	public void simulateOneTour() {
		if (numOfCircles > 0) {
			for (int i = 0; i < players.length; i++) {
	            for (int j = i + 1; j < players.length; j++) {
	            	Match match = new Match(players[i], players[j], i, j, numOfGames, m, n, k);
	            	int result = match.simulateMatch();
	            	if (result == 0) {
	            		scores.replace(players[i], scores.get(players[i]) + 1);
	                    scores.replace(players[j], scores.get(players[j]) + 1);
	            	} else if (result == 1) {
	            		scores.replace(players[i], scores.get(players[i]) + 3);
	            	} else if (result == 2) {
	            		scores.replace(players[j], scores.get(players[j]) + 3);
	            	}
	            }
	        }
	        numOfCircles--;
		}
	}
	
	public void simulateFullTournament() {
		while (numOfCircles > 0) {
			simulateOneTour();
		}
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder("Final scores:\n");
        for (int i = 0; i < players.length; i++) {
        	s.append((i + 1) + "__________" + scores.get(players[i]) + '\n');
        }
        return s.toString();
	}
}