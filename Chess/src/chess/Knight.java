package chess;
/**
 * This is the Knight subclass of ChessPiece
 * @author Scott Lahart, Niles Ball
 *
 */
public class Knight extends ChessPiece{
	
	/**
	 * Tells if the Knight is moving to the right (increasing column), 1 if yes, -1 if to the left
	 */
	private int horizontalPos;
	/**
	 * Tells if the Knight is moving up (increasing row), 1 if yes, -1 if moving down
	 */
	private int verticlePos;
	
	/**
	 * The knights isValidMove does not stop if enemy pieces are in its path, as the knight is allowed to hop over other pieces. It instead determines in what diagonal direction the Knight is trying to move
	 * A knight has two valid moves in any diagonal direction. After the direction is determined, these two spaces are calculated and tested. If one of them is both the desired location, and valid, return true.
	 */
	public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] board) {
		
		//Moves cardinal 2 + 1 to the side, L shaped. !!Can jump over pieces during movement!!
		
		ChessPiece piece = board[startRow][startCol];
		ChessPiece.Color pieceCol = piece.getColor();
		
		//Knight moves to one of two locations in a diagonal direction, this will give us what direction to check
		if(startRow < endRow) {verticlePos = 1;}
		else if(startRow == endRow) {verticlePos = 0;}
		else {verticlePos = -1;}
		
		if(startCol < endCol) {horizontalPos = 1;}
		else if(startCol == endCol) {horizontalPos = 0;}
		else {horizontalPos = -1;}
		
		//No knight moves end on the same col or row
		if(horizontalPos == 0 || verticlePos == 0) {return false;}
		
		
		//Check to the top right
		if(horizontalPos == 1 && verticlePos == 1) {
			
			if((startRow+2) == endRow && (startCol+1) == endCol) {
				if(board[startRow+2][startCol+1] == null || (board[startRow+2][startCol+1].getColor() != pieceCol)) {
					return true;
					}
				}
			if((startRow+1) == endRow && (startCol+2) == endCol) {
				if(board[startRow+1][startCol+2] == null || (board[startRow+1][startCol+2].getColor() != pieceCol)) {
				return true;
				}
			}
			return false;
			
		}
		//Check to the top left
		if(horizontalPos == -1 && verticlePos == 1) {
			
			if((startRow+2) == endRow && (startCol-1) == endCol) {
				if(board[startRow+2][startCol-1] == null || (board[startRow+2][startCol-1].getColor() != pieceCol)) {
					return true;
					}
				}
			if((startRow+1) == endRow && (startCol-2) == endCol) {
				if(board[startRow+1][startCol-2] == null || (board[startRow+1][startCol-2].getColor() != pieceCol)) {
				return true;
				}
			}
			return false;
				
		}
		//Check to the bottom left
		if(horizontalPos == -1 && verticlePos == -1) {
			
			if((startRow-2) == endRow && (startCol-1) == endCol) {
				if(board[startRow-2][startCol-1] == null || (board[startRow-2][startCol-1].getColor() != pieceCol)) {
					return true;
					}
				}
			if((startRow-1) == endRow && (startCol-2) == endCol) {
				if(board[startRow-1][startCol-2] == null || (board[startRow-1][startCol-2].getColor() != pieceCol)) {
				return true;
				}
			}
			return false;
		
		}
		//Check to the bottom right
		if(horizontalPos == 1 && verticlePos == -1) {
			
			if((startRow-2) == endRow && (startCol+1) == endCol) {
				if(board[startRow-2][startCol+1] == null || (board[startRow-2][startCol+1].getColor() != pieceCol)) {
					return true;
					}
				}
			if((startRow-1) == endRow && (startCol+2) == endCol) {
				if(board[startRow-1][startCol+2] == null || (board[startRow-1][startCol+2].getColor() != pieceCol)) {
				return true;
				}
			}
			return false;
			
		}
		//Shouldnt ever be reached? but cant hurt, need for compile
		return false;
	}

	
	/**
	 * Knight constructor. Sets name, color and hasMoved to false
	 * @param col col is the color of the Knight
	 * @param n n is the name of the Knight
	 */
	public Knight(ChessPiece.Color col, String n) {
		this.setColor(col);
		this.setMovement(false);
		this.setName("k"+n);
	}
	/**
	 * the toString override for Knights. Prints a 'w' or 'b' depending on color, followed by a capital 'N'
	 * Note : N is used as K is reserved for Kings
	 */
	public String toString() {
		if(this.getColor() == ChessPiece.Color.White) {return "w" + "N";}
		
		return "b" + "N";
	}

}
