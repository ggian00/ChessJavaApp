package chess;
/**
 * The Rook subclass of ClassPiece
 * @author Scott Lahart, Niles Ball
 *
 */
public class Rook extends ChessPiece{
	
	/**
	 * Tells if the Rook is moving to the right (increasing column), 1 if yes 0 if not moving, -1 if moving left
	 */
	private int horizontalPos;
	/**
	 * Tells if the Rook is moving up (increasing row), 1 if up 0 if not moving, -1 if down
	 */
	private int verticlePos;
	
	/**
	 * Rook isValidMove checks if the desired location is reachable by moving in the cardinal directions centered on the rook. Using verticlePos and horizontalPos, the function determines the direction attempting
	 * to be moved, then checks all locations in that direction. The function keeps going until reaching the edge of the board, a piece that blocks the rooks path, or the desired location.
	 * The function only returns true if the desired location is reached before any obstacles.
	 */
	public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] board) {
		
		ChessPiece piece = board[startRow][startCol];
		ChessPiece.Color pieceCol = piece.getColor();
		
		
		//These checks figure out the direction of movement so we dont have to check every possible viable move.
		if(startRow < endRow) {verticlePos = 1;}
		else if(startRow == endRow) {verticlePos = 0;}
		else {verticlePos = -1;}
		
		if(startCol < endCol) {horizontalPos = 1;}
		else if(startCol == endCol) {horizontalPos = 0;}
		else {horizontalPos = -1;}
		
		
		//Moving up
		if(verticlePos == 1 && horizontalPos == 0) {
			
			
			startRow++;
			while(startRow < 8) {
				
				//if there is something in the way
				if(board[startRow][startCol] != null) {
					//and its the same color, moves invalid
					if(board[startRow][startCol].getColor() == pieceCol){
						return false;
					}
					//if its different color AND its the requested end location, move is valid, capture. !if its a king, checkmate checks should prevent ever reaching here
					if(board[startRow][startCol].getColor() != pieceCol && startRow == endRow && startCol == endCol) {
						return true;
					}
					//If its not the right location, then move is blocked by enemy piece, return false;
					else {return false;}
				}
				//If slot is empty, check if its the requested slot, if not, let the loop move on
				if(startRow == endRow && startCol == endCol) {
					return true;
				}
				
				startRow++;
			}
			return false;
			
			
		}
		//Moving to the right
		if(verticlePos == 0 && horizontalPos == 1) {
			
			startCol++;
			while(startCol < 8) {
				
				//if there is something in the way
				if(board[startRow][startCol] != null) {
					//and its the same color, moves invalid
					if(board[startRow][startCol].getColor() == pieceCol){
						return false;
					}
					//if its different color AND its the requested end location, move is valid, capture. !if its a king, checkmate checks should prevent ever reaching here
					if(board[startRow][startCol].getColor() != pieceCol && startRow == endRow && startCol == endCol) {
						return true;
					}
					//If its not the right location, then move is blocked by enemy piece, return false;
					else {return false;}
				}
				//If slot is empty, check if its the requested slot, if not, let the loop move on
				if(startRow == endRow && startCol == endCol) {
					return true;
				}
				
				startCol++;
			}
			return false;
			
			
		}
		//Moving down
		if(verticlePos == -1 && horizontalPos == 0) {
			
			startRow--;
			while(startRow >= 0) {
				
				//if there is something in the way
				if(board[startRow][startCol] != null) {
					//and its the same color, moves invalid
					if(board[startRow][startCol].getColor() == pieceCol){
						return false;
					}
					//if its different color AND its the requested end location, move is valid, capture. !if its a king, checkmate checks should prevent ever reaching here
					if(board[startRow][startCol].getColor() != pieceCol && startRow == endRow && startCol == endCol) {
						return true;
					}
					//If its not the right location, then move is blocked by enemy piece, return false;
					else {return false;}
				}
				//If slot is empty, check if its the requested slot, if not, let the loop move on
				if(startRow == endRow && startCol == endCol) {
					return true;
				}
				
				startRow--;
			}
			return false;
			
		}
		//Moving left
		if(verticlePos == 0 && horizontalPos == -1) {
			
			startCol--;
			while(startCol >= 0) {
				
				//if there is something in the way
				if(board[startRow][startCol] != null) {
					//and its the same color, moves invalid
					if(board[startRow][startCol].getColor() == pieceCol){
						return false;
					}
					//if its different color AND its the requested end location, move is valid, capture. !if its a king, checkmate checks should prevent ever reaching here
					if(board[startRow][startCol].getColor() != pieceCol && startRow == endRow && startCol == endCol) {
						return true;
					}
					//If its not the right location, then move is blocked by enemy piece, return false;
					else {return false;}
				}
				//If slot is empty, check if its the requested slot, if not, let the loop move on
				if(startRow == endRow && startCol == endCol) {
					return true;
				}
				
				startCol--;
			}
			return false;
			
			
		}
		//Any other combination is not in the rooks moveset, return false
		return false;
	}

	
	
	/**
	 * Rook Constructor. Sets the name, color and hasMoved to false
	 * @param col col is the color of the Rook
	 * @param n n is the name of the Rook
	 */
	public Rook(ChessPiece.Color col, String n) {
		this.setColor(col);
		this.setMovement(false);
		this.setName("r"+n);
	}
	/**
	 * toString override for Rook. Prints a 'w' or 'b' depending on color, followed by a capital 'R'
	 */
	public String toString() {
		if(this.getColor() == ChessPiece.Color.White) {return "w" + "R";}
		
		return "b" + "R";
	}

}
