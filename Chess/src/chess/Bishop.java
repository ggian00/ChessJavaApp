package chess;
/**
 * Bishop is the subclass of ChessPiece that houses the logic for bishops moving. It uses the boolean variables hoizontalPos and verticlePos to determine in what direction the piece is trying to move.
 * Afterward it tests all valid moves in that direction until it hits an obstacle (enemy piece in the way, edge of board) or finds the desired location.
 * @author Scott Lahart, Niles Ball
 *
 */
public class Bishop extends ChessPiece{
	
	//These are set during validMove checks, Pos stands for "positive direction". Declared here for reusability
	/**
	 * Tells if the Bishop is moving to the right (increasing column), true if yes, false if moving to the left
	 */
	private boolean horizontalPos;
	/**
	 * Tells if the Bishop is moving up (increasing row), true if yes, false if moving down
	 */
	private boolean verticlePos;
	
	
	/**
	 * isValidMove for bishop checks in what diagonal direction the piece is trying to move. It then tests every board location in that direction, stopping if it reaches the edge of the board, an enemy piece
	 * gets in its way, or it finds its target
	 */
	public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] board) {

		ChessPiece piece = board[startRow][startCol];
		ChessPiece.Color pieceCol = piece.getColor();
		
		//Each bishop move changes both row and col by at least 1
		if((startRow == endRow) || (startCol == endCol)) {return false;}
		
		if(startCol < endCol) {horizontalPos = true;}
		else {horizontalPos = false;}
		
		if(startRow < endRow) {verticlePos = true;}
		else {verticlePos = false;}
		
		//Each of the following checks will increment in the correct direction, returning false if something is in the way, or return true if end position is reached
		//Check to the top right
		if(horizontalPos && verticlePos) {
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
		if(!horizontalPos && verticlePos) {
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
		if(!horizontalPos && !verticlePos) {
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
		if(horizontalPos && !verticlePos) {
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
	 * Bishop constructor, sets name, color and hasMoved to false
	 * @param col col is the color of the pice
	 * @param n n is the name of the piece
	 */
	public Bishop(ChessPiece.Color col, String n) {
		this.setColor(col);
		this.setMovement(false);
		this.setName("b"+n);
	}
	/**
	 * This is the toString override. Bishops print a lowercase 'w' or 'b' depending on their color, followed by a capital 'B'
	 */
	public String toString() {
		if(this.getColor() == ChessPiece.Color.White) {return "w" + "B";}
		
		return "b" + "B";
	}

}
