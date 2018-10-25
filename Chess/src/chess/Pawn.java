package chess;
/**
 * Pawn subclass of ChessPiece
 * @author Scott Lahart, Niles Ball
 *
 */
public class Pawn extends ChessPiece{
	
	/**
	 * The turn on which the pawn moved two spaces. Used for enpassant
	 */
	private int movedTwoOnTurn;
	/**
	 * The direction that the pawn is moving. Is the result of (startRow - endRow). White pieces start on low row, black pieces on high rows. Thus, white pieces direction is less than 0. black pieces direction is greater than 0
	 */
	private int direction;
	/**
	 * Pawn isValidMove does a few checks. If attempting to move two spaces forward(forward being towards the current players enemy start location), the function checks if the piece has previously moved.
	 * If it has not previously moved, it checks the locations in front of the pawn. If both squares are empty, and the desired location is two spots in front of the pawn, returns true
	 * Otherwise the function checks if the pawn is moving laterally at all for an attack. The pawn checks if an enemy exists in the diagonal direction, and if so returns true
	 * If there is no enemy, the function checks if there is a pawn directly next to the pawn being moved. It then checks if that pawn moved two spaces on the previous turn. If the pawn
	 * did indeed move two on the previous turn, the function treats it as if it is in the attackable diagonal direction, executing enpassant.
	 * Otherwise, check if the pawns desired location is the spot directly in front of it. If it is and the spot is free, return true
	 * Otherwise return false
	 */
	public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] board) {

		//if hasmoved = false, can move forward 2, otherwise moves forward 1 or attacks diagonally 1 if enemy is present and is in direction opposite of start. 
		
		//enpassant - if a pawn moves 2 spaces and ends up next to an enemy pawn, the enemy pawn can capture the spot behind the orginal pawn, taking the enemy pawn with it
		
		
		ChessPiece piece = board[startRow][startCol];
		ChessPiece.Color pieceCol = piece.getColor();
		ChessPiece enemy = board[endRow][endCol];
		
		direction = (startRow - endRow);
	
		//Black can only move down 6->0, white can only move up 1->7, can only move 2 space on first turn, 1 space on other turns, diagonal one if enemy exists
		
		//cant move more than 2
		if(Math.abs(startRow - endRow) > 2) {return false;}
		
		if(startRow == endRow) {return false;}
		//Black pawn cant move that direction
		if(pieceCol == ChessPiece.Color.Black && direction < 0) {return false;}
		//White pawn cant move that direction
		if(pieceCol == ChessPiece.Color.White && direction > 0) {return false;}
		
		//If the movement is greater than 1 and its not the pawns first turn, invalid
		if(Math.abs(startRow - endRow) == 2 && piece.moveHistory() == true) {return false;}
		
		//Cant jump into a friendly piece
		if(enemy != null) {
			if(enemy.getColor() == pieceCol) {return false;}
		}
		
		//If its moving sideways, it must be attacking, check for enemies
	
		if(startCol != endCol) {
			
			if(Math.abs(startCol - endCol) == 1 && Math.abs(startRow - endRow) == 1) {
				
				//If an enemy exists in the spot
				if(enemy != null) {
					
					if(enemy.getColor() != piece.getColor()) {
						return true;
					}
				}
				//Below is enpassant
				
				//so, in the same row, but end col, if theres a piece with a moved two on current turn + 1
				
				//No item in enpassant slot
				if(board[startRow][endCol] == null) {return false;}
				
				ChessPiece possibleEnpassant = board[startRow][endCol];
				
				//If its a pawn in enpassant slot, do check
				if(possibleEnpassant instanceof Pawn) {
					//If the end slot is empty, the pawn to the side moved two on the previous turn, is valid. Remove the pawn and return true so the "move" function can handle moving the pawn
					if(board[endRow][endCol] == null && ((Pawn) possibleEnpassant).getTwoMoveTurn() == ChessPiece.turnCounter - 1) {

						return true;
						
					}
				
				}
			}
			
			//If above is not true, cannot move sideways, must be false
			return false;
		}
		
		
		
		//else, column doesnt change
		else {
			//if trying to move 2, check if its in its starter position
			if(Math.abs(startRow - endRow) == 2) {
				if(piece.moveHistory() == false) {
					//if direction is positive, its blacks move
					if(direction > 0) {
						if(board[startRow-1][startCol] == null && board[startRow - 2][startCol] == null) {
							setMoveTwoTurn(ChessPiece.turnCounter);
							return true;
						}
					}
					//if direction is negative, its whites move
					if(direction < 0) {
						if(board[startRow+1][startCol] == null && board[startRow + 2][startCol] == null) {
							setMoveTwoTurn(ChessPiece.turnCounter);
							return true;
						}
					}
					
					
					
				}
				return false;
			}
			//else its trying to move 1
			else {
				if(direction > 0) {
					if(board[startRow-1][startCol] == null) {
						return true;
					}
				}
				//if direction is negative, its whites move
				if(direction < 0) {
					if(board[startRow+1][startCol] == null) {
						return true;
					}
				}
			}
			
			
		}

		return false;
	}
/**
 * sets the pawns variable that tracks on what turn it moved two spaces. Used in checking if an enemy pawn can execute enpassant on this pawn
 * @param i i is the turn on which the pawn moved two spaces
 */
	public void setMoveTwoTurn(int i) {
		movedTwoOnTurn = i;
	}
	/**
	 * getTwoMoveTurn returns the turn on which the pawn moved two spaces. Used in checking for valid enpassant
	 * @return returns the integer value of the turn that the pawn moved two spaces on
	 */
	public int getTwoMoveTurn(){
		return movedTwoOnTurn;
	}
	/**
	 * Pawn constructor. Sets the color, name and hasMoved to false
	 * @param col col is the color of the Pawn
	 * @param n n is the name of the Pawn
	 */
	public Pawn(ChessPiece.Color col, String n) {
		this.setColor(col);
		this.setMovement(false);
		this.setName("p"+n);
		this.movedTwoOnTurn = -1;
	}
	
	
	/**
	 * toString override for Pawn. Prints 'w' or 'b' depending on color, followed by a lowercase 'p'
	 */
	public String toString() {
		if(this.getColor() == ChessPiece.Color.White) {return "w" + "p";}
		
		return "b" + "p";
	}

}
