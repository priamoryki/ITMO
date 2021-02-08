package game;

import java.util.Scanner;

public class Reader {
	private Scanner in;
	Reader(Scanner in) {
		this.in = in;
	}
	
	public int readInt(String massage) {
    	while (true) {
    		System.out.println(massage);
    		if (in.hasNextInt()) {
    			return in.nextInt();
    		} else if (in.hasNext()) {
    			in.next();
    		}
    	}
    }
}