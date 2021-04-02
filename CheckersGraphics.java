
/**
 * File: CheckersGraphics.java
 * --------------------
 */

import acm.program.*;
import acm.graphics.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class CheckersGraphics extends GCanvas {
	
	private static final int N_ROWS = 8;
	private static final int N_COLS = 8;
	private static final int N_CHECKER_ROWS = 3;
	
	private static final Color SQUARE_COLOR = Color.black;
	private static final Color SECOND_COLOR = Color.gray;
	private static final Color FIRST_COLOR = Color.red;
	
	private static final int EMPTY = 0;
	private static final int FIRST_PLAYER = 1;
	private static final int SECOND_PLAYER = 2;
	private static final int FIRST_PLAYER_KING = 3;
	private static final int SECOND_PLAYER_KING = 4;
	
	
	
	public class Coordinate {
		
		private int row, col;
		
		public Coordinate(int row, int col) {
			
			this.row = row;
			this.col = col;
		}
		
		public int getRow() {
			
			return row;
		}
		public int getCol() {
			
			return col;
		}
		
	}
	
	
	public class Checker extends GCompound {
		
		static final double OUTER_SCALE = 0.7;
		static final double INNER_SCALE = 0.4;
		static final double MID_SCALE = 0.47;
		
		public Checker(double width, double height, boolean dark) {

			double outer_diameter = min(width, height) * OUTER_SCALE;
			double inner_diameter = min(width, height) * INNER_SCALE;
			double mid_diameter =  min(width, height) * MID_SCALE;
			
			GOval outer_circle = new GOval(outer_diameter, outer_diameter);
			GOval inner_cirlce = new GOval(inner_diameter, inner_diameter);
			GOval mid_circle = new GOval(mid_diameter, mid_diameter);
			
			add(outer_circle, (width - outer_diameter)/2, (height - outer_diameter)/2);
			add(mid_circle, (width - mid_diameter)/2, (height - mid_diameter)/2);
			add(inner_cirlce, (width - inner_diameter)/2, (height - inner_diameter)/2);
			
			outer_circle.setFilled(true);
			inner_cirlce.setFilled(true);
			mid_circle.setFilled(true);
			
			if(dark) {
				
				outer_circle.setFillColor(SECOND_COLOR);
				inner_cirlce.setFillColor(SECOND_COLOR);
				mid_circle.setFillColor(Color.WHITE);
				
				inner_cirlce.setColor(Color.white);
				mid_circle.setColor(Color.white);
			} else {

				outer_circle.setFillColor(FIRST_COLOR);
				inner_cirlce.setFillColor(FIRST_COLOR);
				mid_circle.setFillColor(Color.WHITE);

				inner_cirlce.setColor(Color.white);
				mid_circle.setColor(Color.white);
			}
		}	
	}
	
	public CheckersGraphics(int width, int height) {
		
		this.width = width;
		this.height = height;
		
		this.setSize(width, height);		
	}
	
	public void initialize(boolean reverse) {
		
		this.addMouseListener(mouse_listener);
		
		drawBoard();
		
		if(reverse) {

			addCheckers(N_ROWS - N_CHECKER_ROWS, SECOND_COLOR);
			addCheckers(0, FIRST_COLOR);	
		} else {

			addCheckers(N_ROWS - N_CHECKER_ROWS, FIRST_COLOR);	
			addCheckers(0, SECOND_COLOR);
		}
		
		 printGrid(checkers);
	}

	private void drawBoard() {

		double square_width = width/N_ROWS;
		double square_height = height/N_COLS;
		
		boolean filled = false;
		
		for(int i = 0; i < N_ROWS; i++) {
			for(int j = 0; j < N_COLS; j++) {
				
				checkers[i][j] = EMPTY;
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
		
		if(checkers[i][j] == EMPTY) {
			double square_width = width/N_ROWS;
			double square_height = height/N_COLS;
			
			Checker checker = new Checker(square_width, square_height, checker_color == SECOND_COLOR);
			
			add(checker, j * square_width, i * square_height);
			
			if(checker_color == FIRST_COLOR) {
				checkers[i][j] = FIRST_PLAYER;
				
			}else {
				checkers[i][j] = SECOND_PLAYER;
				
			}
		}else {
			
			System.out.print("Could not add a checker\n");
		}
	}
	
	private void removeChecker(int i, int j) {
		
		double square_width = width/N_ROWS;
		double square_height = height/N_COLS;
		
		double x = square_width * j + square_width / 2;
		double y = square_height * i + square_height / 2;
		
		if(checkers[i][j] != EMPTY) {
			
			remove(getElementAt(x, y));
			checkers[i][j] = EMPTY;
			
		}else {
			
			System.out.print("No checker found at [" + i + ", " + j +"]\n");
		}

	}
	
	
	private void moveChecker(int start_row, int start_col, int end_row, int end_col) {
		
		if(checkers[start_row][start_col] != EMPTY && checkers[end_row][end_col]  == EMPTY) {
		
			removeChecker(start_row, start_col);
			
			Color color;
			
			if(checkers[start_row][start_col] == FIRST_PLAYER || checkers[start_row][start_col] == FIRST_PLAYER_KING) {
				
				color = FIRST_COLOR;
				
			}else {
				
				color = SECOND_COLOR;
			}
			
			addChecker(end_row, end_col, color);
			
		}else {
			
			System.out.print("Checker could not be moved from [" + start_row + ", " + start_col + "] to [" + end_row + ", " + end_col + "]\n");
		}
	}
	
	
	private double min(double first, double second) {
		
		return first < second ? first : second;
	}
	
	private Coordinate getSquare(double x, double y) {
		
		double square_width = width/N_ROWS;
		double square_height = height/N_COLS;
		
		int col =  (int) (x / square_width);
		int row = (int) (y / square_height);
		
		Coordinate coordinate = new Coordinate(row, col);
		
		return coordinate;
	}
	
	private MouseAdapter mouse_listener = new MouseAdapter() {
		
		public void mouseClicked(MouseEvent e) {
			
			double x = e.getX();
			double y = e.getY();
			
			Coordinate coordinate = getSquare(x, y);
			
			int row = coordinate.getRow();
			int col = coordinate.getCol();
			
			System.out.print("row: " + row + ", col: " + col + "\n");
			
			if(move) {
				
				move = false;
			}
			
			//removeChecker(row, col);
			
		}

	};
		
	private void printGrid(int [][] grid) {
		
		System.out.print("{\n");
		
		for(int i = 0; i < grid.length; i++) {
			
			int [] curr_row = grid[i];
			for(int j = 0; j < curr_row.length; j++) {
				
				System.out.print(curr_row[j] + ", ");
			}
			System.out.print("\n");
		}
		System.out.print("}\n");
	}

	private double width, height;
	private double last_row, last_col;
	private boolean move = false;
	private int [][] checkers = new int [N_ROWS][N_COLS];
	
}