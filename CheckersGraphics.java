
/**
 * File: CheckersGraphics.java
 * --------------------
 */

import acm.program.*;
import acm.graphics.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Iterator;

@SuppressWarnings("serial")
public class CheckersGraphics extends GCanvas {
	
	private static final int N_ROWS = 8;
	private static final int N_COLS = 8;
	private static final int N_CHECKER_ROWS = 3;
	
	private static final Color HIGHLIGHT_COLOR = Color.cyan;
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
		
		public String toString() {
			
			return "[" + row + ", " + col +"]";
		}
				
		public boolean equals(Object other) {
			
			Coordinate other_coordinate = (Coordinate) other;
			
			System.out.println("Comparing " + this + " with " + other);
			
			return row == other_coordinate.row && col == other_coordinate.col;
		}
	}
	
	
	public class Checker extends GCompound {
		
		static final double OUTER_SCALE = 0.7;
		static final double INNER_SCALE = 0.4;
		static final double MID_SCALE = 0.47;
		
		public Checker(double width, double height, final int player) {

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
			
			switch(player) {
						
			case FIRST_PLAYER:

				outer_circle.setFillColor(FIRST_COLOR);
				inner_cirlce.setFillColor(FIRST_COLOR);
				mid_circle.setFillColor(Color.WHITE);

				inner_cirlce.setColor(Color.white);
				mid_circle.setColor(Color.white);
				break;
			case SECOND_PLAYER:
				
				outer_circle.setFillColor(SECOND_COLOR);
				inner_cirlce.setFillColor(SECOND_COLOR);
				mid_circle.setFillColor(Color.WHITE);
				
				inner_cirlce.setColor(Color.white);
				mid_circle.setColor(Color.white);
				break;
				
			case FIRST_PLAYER_KING:
				
				System.out.println("Not yet implemented.");
				throw new Error();
				
			case SECOND_PLAYER_KING:

				System.out.println("Not yet implemented.");
				throw new Error();
				
			default:
				
				System.out.println("Not a valid player type.");
				throw new Error();
			}
		}	
	}
	
	public CheckersGraphics(int width, int height) {
		
		this.width = width;
		this.height = height;
		
		this.setSize(width, height);		
	}
	
	public void initialize(boolean reverse) {

		this.reverse = reverse;
		this.addMouseListener(mouse_listener);
		
		square_width = width/N_ROWS;
		square_height = height/N_COLS;
		
		drawBoard();
		
		if(reverse) {

			addCheckers(N_ROWS - N_CHECKER_ROWS, SECOND_PLAYER);
			addCheckers(0, FIRST_PLAYER);	
		} else {

			addCheckers(N_ROWS - N_CHECKER_ROWS, SECOND_PLAYER);	
			addCheckers(0, FIRST_PLAYER);
		}
		
		 printGrid(checkers);
	}

	private void drawBoard() {
		
		boolean filled = false;
		
		for(int row = 0; row < N_ROWS; row++) {
			for(int col = 0; col < N_COLS; col++) {
				
				checkers[row][col] = EMPTY;
				addSquare(row * square_width, col * square_height, square_width, square_height, filled);
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

	private void addCheckers(int start_row, final int player) {
		
		boolean skip_first = start_row % 2 == 0;
		
		for(int row = 0; row < N_CHECKER_ROWS; row++) {
			
			for(int col = skip_first ? 1 : 0; col < N_COLS; col += 2) {
				addChecker(start_row + row, col, player);
				
			}
			skip_first = !skip_first;
		}
	}

	private void addChecker(int row, int col, final int player) {
		
		if(checkers[row][col] == EMPTY) {
			
			Checker checker = new Checker(square_width, square_height, player);

			add(checker, col * square_width, row * square_height);
			checker.sendToFront();
			
			
			checkers[row][col] = player;
			
		}else {
			
			System.out.print("Could not add a checker\n");
		}
	}
	
	private void removeChecker(int row, int col) {
		
		double x = square_width * col + square_width / 2;
		double y = square_height * row + square_height / 2;
		
		if(checkers[row][col] != EMPTY) {
			
			remove(getElementAt(x, y));
			checkers[row][col] = EMPTY;
			
		}else {
			
			System.out.print("No checker found at " + new Coordinate(row, col) + "\n");
		}

	}
	
	
	private void moveChecker(int start_row, int start_col, int end_row, int end_col) {
		
		if(checkers[start_row][start_col] != EMPTY && checkers[end_row][end_col]  == EMPTY) {
		
			int player = checkers[start_row][start_col];
			
			removeChecker(start_row, start_col);
			addChecker(end_row, end_col, player);
			
		}else {
			
			System.out.print("Checker could not be moved from " + new Coordinate(start_row, start_col) + " to " + new Coordinate(end_row, end_col) + "\n");
		}
	}
	
	private boolean possibleMove(int row, int col) {
	
		return inBounds(row, col) && checkers[row][col] == EMPTY;
	}
	
	private	HashSet<Coordinate> possibleMoves(int start_row, int start_col) {
		
		HashSet<Coordinate> possible_moves = new HashSet<Coordinate>();
		
		int player = checkers[start_row][start_col];
		
		int diff_row = reverse ? 1 : -1;
		
		switch(player) {
		
		case EMPTY:
			return possible_moves;
			
		case FIRST_PLAYER:

			if(possibleMove(start_row + diff_row, start_col - 1)) possible_moves.add(new Coordinate(start_row + diff_row, start_col - 1));
			if(possibleMove(start_row + diff_row, start_col + 1)) possible_moves.add(new Coordinate(start_row + diff_row, start_col + 1));
			
			return possible_moves;
		case SECOND_PLAYER:

			if(possibleMove(start_row - diff_row, start_col - 1)) possible_moves.add(new Coordinate(start_row - diff_row, start_col - 1));
			if(possibleMove(start_row - diff_row, start_col + 1)) possible_moves.add(new Coordinate(start_row - diff_row, start_col + 1));
			
			return possible_moves;
		case FIRST_PLAYER_KING:
		case SECOND_PLAYER_KING:
			
			// ... Something ...
			break;
		default:
			
			System.out.println("Not a valid player type for possible moves.");
			throw new Error();
		}
		
		return null;
	}
	
	private void highlightSquare(int row, int col, boolean light) {
		
		highlightSquare(row, col, light, HIGHLIGHT_COLOR);
	}
	
	private void clearHighlights() {
		
		for(int i = 0; i < N_ROWS; i++) {
			for(int j = 0; j < N_COLS; j++) {
				if(highlight[i][j]) highlightSquare(i, j, false);
			}
		}
	}
	
	private void highlightSquare(int row, int col, boolean light, Color color) {
		
		if(light == highlight[row][col]) {
			
			if(light == true) {
				
				System.out.println("Square already highlighted.");
			} else {
				
				System.out.println("Square not highlited.");
			}
			return;
		}
		
		if(light) {
			
			GRect highlighter_rect = new GRect(square_width, square_height);
			
			highlighter_rect.setColor(color);
			highlighter_rect.setFilled(true);
			highlighter_rect.setFillColor(color);

			if(checkers[row][col] != EMPTY) {
				
				int player = checkers[row][col];
				
				removeChecker(row, col);
				add(highlighter_rect, col * square_width, row * square_height);
				addChecker(row, col, player);
				
			} else {

				add(highlighter_rect, col * square_width, row * square_height);
			}
		} else {
			
			GRect highlighter_rect = (GRect) getElementAt(col * square_width + 1, row * square_height + 1);
			
			remove(highlighter_rect);
		}
		
		highlight[row][col] = light;
	}
	
	private double min(double first, double second) {
		
		return first < second ? first : second;
	}
	
	private boolean inBounds(int row, int col) {
		
		return 0 <= row && row < N_ROWS && 0 <= col && col < N_COLS;
	}
	
	private Coordinate getSquare(double x, double y) {
		
		int col =  (int) (x / square_width);
		int row = (int) (y / square_height);
		
		if(!inBounds(row, col)) {
			
			System.out.println("Coordinate not on board");
			return new Coordinate(-1, -1);
		}
		
		Coordinate coordinate = new Coordinate(row, col);
		
		return coordinate;
	}
	
	private MouseAdapter mouse_listener = new MouseAdapter() {
		
		public void mouseClicked(MouseEvent e) {
			
			double x = e.getX();
			double y = e.getY();
			
			Coordinate coordinate = getSquare(x, y);
			
			if(coordinate.getRow() == -1) return;
			
			int row = coordinate.getRow();
			int col = coordinate.getCol();
			
			System.out.print("row: " + row + ", col: " + col + "\n");
			
			if(move) {
				
				clearHighlights();
				
				if(last_moves.contains(new Coordinate(row, col))) moveChecker(last_row, last_col, row, col);
				else System.out.println("Not a valid move.");

				move = false;
			}else {
								
				last_moves = possibleMoves(row, col);
				
				Iterator<Coordinate> moves_it = last_moves.iterator();
				
				while(moves_it.hasNext()) {
					
					System.out.println("HERE");
					Coordinate curr_coordinate = moves_it.next();
					highlightSquare(curr_coordinate.getRow(), curr_coordinate.getCol(), true, Color.YELLOW);
				}
				
				last_row = row;
				last_col = col;	
				
				highlightSquare(last_row, last_col, true);
				move = true;
			}
			
			//removeChecker(row, col);
			
		}

	};
		
	private void printGrid(int [][] grid) {
		
		System.out.print("{\n");
		
		for(int row = 0; row < grid.length; row++) {
			
			int [] curr_row = grid[row];
			for(int col = 0; col < curr_row.length; col++) {
				
				System.out.print(curr_row[col] + ", ");
			}
			System.out.print("\n");
		}
		System.out.print("}\n");
	}

	private double width, height;
	private boolean reverse;
	
	private int last_row, last_col;	
	private HashSet<Coordinate> last_moves;
	private boolean move = false;

	private double square_width;
	private double square_height;
	
	private int [][] checkers = new int [N_ROWS][N_COLS];
	private boolean [][] highlight = new boolean [N_ROWS][N_COLS];
	
}