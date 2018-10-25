package chess;
/**
 * ChessPiece is an abstract class containing common methods / variables of each chess piece. It stores colors, if the piece has moved, print information and an abstract method for checking if moves are valid
 * ChessPiece has one static variable of turnCounter. This is used to track when pawns move two spaces on their turn. For enpassant, the move must be executed the turn directly after the 2 space move was executed.
 * @author Scott Lahart, Niles Ball
 *
 */
public abstract class ChessPiece {
	
	/**
	 * A turn tracker that increments by 1 every move. Used to identify when a pawn moved 2 spaces for enpassant
	 */
	public static int turnCounter;
	/**
	 * Defines the color of the piece, either White or Black
	 */
	public enum Color {
		White, Black
	}
	/**
	 * The color of the piece
	 */
	private Color color;
	
	/**
	 * Shows whether the piece has moved
	 */
	private boolean hasMoved;
	/**
	 * The name specific to this chess piece
	 */
	private String name;
	
	/**
	 * moveHistory returns if the piece has moved. Used for castling, pawns moving two spaces, etc.
	 * @return returns true if the piece has moved, false if it has not moved
	 */
	public boolean moveHistory() {
		return hasMoved;
	}
	/**
	 * setMovement changes a pieces move history. Is called on a piece whenever it is succesfully moved
	 * @param x x is the state hasMoved is being set to. This is generally set to true after moves
	 */
	public void setMovement(boolean x) {
		hasMoved = x;
	}

	
	/**
	 * getName returns the pieces identifying name
	 * @return returns the piece name as a string
	 */
	public String getName() {
		return name;
	}
	/**
	 * setName sets the pieces name
	 * @param n n is the name to be set
	 */
	public void setName(String n) {
		name = n;
	}
	
	/**
	 * getColor returns the pieces color. Used for testing if a player can move the piece and if enemies can capture the piece.
	 * @return returns a color (white, black)
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * setColor sets the pieces color
	 * @param col col is the color to be set, either black or white
	 */
	public void setColor(Color col) {
		color = col;
	}
	/**
	 * toString is the print function. Is overridden by child classes
	 */
	public String toString() {
		return " ";
	}
	/**
	 * equals is the equals override, for comparing if two pieces are the same thing
	 */
	public boolean equals(Object o) {
		if ((o instanceof ChessPiece)&&(o!=null)) {
			return true;
		}
		return false;
	}
	/**
	 * isValidMove is an abstract function to be overwritten by each child that inherits ChessPiece. This is the function that houses all the logic for how a specific piece can move. It passes the starting location,
	 * the ending location, and the board.
	 * @param startRow startRow is the row of the piece being tested
	 * @param startCol startCol is the column of the piece being tested
	 * @param endRow endRow is the row of the space attempting to be moved to
	 * @param endCol endCol is the column of the space attempting to be moved to
	 * @param board board is the 8x8 chessboard housing all the pieces. This is passed to check for things such as friendly pieces being in the end location, or enemy pieces being in the path of the move.
	 * @return isValidMove returns true if the move is acceptable, or false if it breaks the rules
	 */
	public abstract boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] board);
	
}
