
/**
 * File: Checkers.java
 * --------------------
 */

import acm.program.*;
import acm.graphics.*;

@SuppressWarnings("serial")
public class Checkers extends ConsoleProgram {
	
	private static final int CANVAS_WIDTH = 500;
	private static final int CANVAS_HEIGHT = 500;
	
	
	public void main() {
		
		new Checkers().start();
	}
	
	public void init() {
		
		CheckersGraphics graphics = new CheckersGraphics(CANVAS_WIDTH, CANVAS_HEIGHT);
		
		add(graphics);
		graphics.initialize(true);
	}
	
	public void run() {
		
		
	}
}