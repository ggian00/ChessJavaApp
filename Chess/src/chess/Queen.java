package chess;
/**
 * The Queen subclass of ChessPiece
 * @author Scott Lahart, Niles Ball
 *
 */
public class Queen extends ChessPiece{
	
	/**
	 * Tells if the Queen is moving to the right (increasing column), 1 if yes 0 if no movement, -1 if moving left
	 */
	private int horizontalPos;
	/**
	 * Tells if the Queen is moving up (increasing row), 1 if yes 0 if not moving, -1 if moving down
	 */
	private int verticlePos;
	
	/**
	 * The Queen isValidMove function is a combination of the rook and bishop isValidMove checks. The queen can move in any diagonal or cardinal direction as long as no pieces are in her path. 
	 * The direction is determined by setting verticlePos and horizontalPos. They are set to 1 if in the positive direction, 0 if they are staying in their row or column, and -1 if moving in the negative direction.
	 * Afterwards, the chosen direction has its locations tested until finding the end of the board, a piece blocking the queens path, or the desired location. Return true only if desired location is found first.
	 */
	public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] board) {
		
		ChessPiece piece = board[startRow][startCol];
		ChessPiece.Color pieceCol = piece.getColor();
		
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
			
			//Check to the top right
			if(horizontalPos == 1 && verticlePos == 1) {
				startCol++;
				startRow++;
				while(startCol < 8 && startRow < 8) {
					
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
					startRow++;
				}
				return false;
			}
			//Check to the top left
			if(horizontalPos == -1 && verticlePos == 1) {
				startCol--;
				startRow++;
				//While in range of the board
				while(startCol >= 0 && startRow < 8) {
					
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
					startRow++;
						
				}
				//out of range
				return false;
			}
			//Check to the bottom left
			if(horizontalPos == -1 && verticlePos == -1) {
				startCol--;
				startRow--;
				while(startCol >= 0 && startRow >= 0) {

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
					startRow--;
				}
			return false;
			
			
		}
			//Check to the bottom right
			if(horizontalPos == 1 && verticlePos == -1) {
				startCol++;
				startRow--;
				while(startCol < 8 && startRow >= 0) {

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
					startRow--;
				}
				return false;
			}
			
			//Shouldnt ever be reached? but cant hurt, need for compile
			return false;
		}
	

	
	/**
	 * Queen constructor. Sets the name, color and hasMoved to false
	 * @param col col is the color of the queen
	 * @param n n is the name of the queen
	 */
	public Queen(ChessPiece.Color col, String n) {
		this.setColor(col);
		this.setMovement(false);
		this.setName("q"+n);
	}
	/**
	 * toString is the toString override for queens. Queens print a 'w' or 'b' depending on color, followed by a capital 'Q'
	 * @return a String describing the color and that this is a queen
	 */
	public String toString() {
		if(this.getColor() == ChessPiece.Color.White) {return "w" + "Q";}
		
		return "b" + "Q";
	}

}
