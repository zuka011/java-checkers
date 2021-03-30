
/**
 * File: CheckersGraphics.java
 * --------------------
 */

import acm.program.*;
import acm.graphics.*;
import java.awt.*;

@SuppressWarnings("serial")
public class CheckersGraphics extends GCanvas {
	
	private static final int N_ROWS = 8;
	private static final int N_COLS = 8;
	private static final int N_CHECKER_ROWS = 3;
	
	private static final double CHECKER_SCALE = 0.7;
	
	private static final Color SQUARE_COLOR = Color.black;
	private static final Color DARK_COLOR = Color.gray;
	private static final Color LIGHT_COLOR = Color.red;
	
	public class Checker extends GCompound {
		
		public Checker(double width, double height, boolean dark) {

			double outer_diameter = min(width, height) * CHECKER_SCALE;
			double inner_diameter = outer_diameter * CHECKER_SCALE;
			
			GOval outer_circle = new GOval(outer_diameter, outer_diameter);
			GOval inner_cirlce = new GOval(inner_diameter, inner_diameter);
			
			add(outer_circle, (width - outer_diameter)/2, (height - outer_diameter)/2);
			add(inner_cirlce, (width - inner_diameter)/2, (height - inner_diameter)/2);
			
			outer_circle.setFilled(true);
			inner_cirlce.setFilled(true);
			
			if(dark) {
				
				outer_circle.setFillColor(DARK_COLOR);
				inner_cirlce.setFillColor(LIGHT_COLOR);
			} else {

				outer_circle.setFillColor(LIGHT_COLOR);
				inner_cirlce.setFillColor(DARK_COLOR);
			}
		}	
	}
	
	public CheckersGraphics(int width, int height) {
		
		this.width = width;
		this.height = height;
		
		this.setSize(width, height);		
	}
	
	public void initialize(boolean reverse) {
	
		System.out.println("Here I am!");
		
		drawBoard();
		
		if(reverse) {

			addCheckers(N_ROWS - 3, DARK_COLOR);
			addCheckers(0, LIGHT_COLOR);	
		} else {

			addCheckers(N_ROWS - 3, LIGHT_COLOR);	
			addCheckers(0, DARK_COLOR);
		}
	}

	private void drawBoard() {

		double square_width = width/N_ROWS;
		double square_height = height/N_COLS;
		
		boolean filled = false;
		
		for(int i = 0; i < N_ROWS; i++) {
			for(int j = 0; j < N_COLS; j++) {
				
				addSquare(i * square_width, j * square_height, square_width, square_height, filled);
				filled = !filled;
			}
			filled = !filled;
		}
	}
	
	private void addSquare(double x, double y, double square_width, double square_height, boolean filled) {
		
		GRect square = new GRect(x, y, square_width, square_height);
		square.setFilled(filled);
		square.setFillColor(SQUARE_COLOR);
		
		add(square);
	}

	private void addCheckers(int start_row, Color checker_color) {
				
		boolean skip_first = start_row % 2 == 0;
		
		for(int i = 0; i < N_CHECKER_ROWS; i++) {
			
			for(int j = skip_first ? 1 : 0; j < N_COLS; j += 2) {
				addChecker(start_row + i, j, checker_color);
			}
			skip_first = !skip_first;
		}
	}

	private void addChecker(int i, int j, Color checker_color) {

		double square_width = width/N_ROWS;
		double square_height = height/N_COLS;
		
		Checker checker = new Checker(square_width, square_height, checker_color == DARK_COLOR);
		
		add(checker, j * square_width, i * square_height);
	}
	
	private double min(double first, double second) {
		
		return first < second ? first : second;
	}

	private double width, height;
}