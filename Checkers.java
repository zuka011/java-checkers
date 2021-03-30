
/**
 * File: Checkers.java
 * --------------------
 */

import acm.program.*;
import acm.graphics.*;

@SuppressWarnings("serial")
public class Checkers extends ConsoleProgram {
	
	public void main() {
		
		new Checkers().start();
	}
	
	public void init() {
		
		CheckersGraphics graphics = new CheckersGraphics(500, 500);
		
		add(graphics);
		graphics.initialize(false);
	}
	
	public void run() {
		
		
	}
}