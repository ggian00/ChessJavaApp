

package chess;
/**
 * ChessBoard is the class that handles all of the board logic. This includes checking for valid inputs, keeping track of "check", housing the board, print functions, setting the board on start and moving pieces
 * The variable board is where all the pieces are stored, the boolean inCheck tracks if the current players king is in check, and if so it restricts which movements are valid(only moves that take them out of check remain valid)
 * @author Scott Lahart, Niles Ball
 *
 */

public class ChessBoard {
	/**
	 * The board which holds all the ChessPieces in the game, if there is no piece in a location it is set to null
	 */
	ChessPiece[][] board = new ChessPiece[8][8];
	/**
	 * Tells whether either player is in check
	 */
	boolean inCheck;
	
	/**
	 * ChessBoard constructer. Calls setBoard which puts pieces in starting position, sets inCheck to false and sets the turnCounter to 0
	 */
	public ChessBoard() {
		
		setBoard();
		
		inCheck = false;
		ChessPiece.turnCounter = 0;
	}
	
	/**
	 * clearBoard sets the board to be empty
	 */
	public void clearBoard() {
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[0].length; y++) {
				board[x][y] = null;
			}
		}
	}
	/**
	 * printBoard prints the state of board[][] to the console window. If a space is null(empty) it prints the appropriate checkered pattern.
	 * Additionally this will print the row and column tags, with columns being identified by letters a-h, and rows by numbers 1-8
	 */
	public void printBoard() {
		for (int r = board.length-1; r >= 0; r--) {
			for (int c = 0; c < board[0].length; c++) {
				if (board[r][c]==null) {
					if ((r%2)==(c%2)) {
						System.out.print("## ");
					}
					else {
						System.out.print("   ");
					}
				}
				else {
					System.out.print(board[r][c] + " ");
				}
			}
			System.out.println(r+1);
		}
		System.out.println(" a  b  c  d  e  f  g  h");
		System.out.println();
	}
	
/**
 * setBoard initalizes the board to the start position. Calls initializeRow function to set non pawn pieces.
 */
	public void setBoard() {
		initializeRow(0, ChessPiece.Color.White);
		initializeRow(7, ChessPiece.Color.Black);
		// sets up the pawns
		for (int col = 0; col < 8; col++) {
			board[1][col] = new Pawn(ChessPiece.Color.White, Integer.toString(col));
		}
		for (int col = 0; col < 8; col++) {
			board[6][col] = new Pawn(ChessPiece.Color.Black, Integer.toString(col));
		}
	}
	
	/**
	 * initializeRow is a helper function of setBoard which sets the non pawn pieces on the board. It takes the color of a player, and the row that players pieces start on as param.
	 * @param row row is the row on the board that a players non pawn pieces begin on
	 * @param col col is the color of the player whose pieces are being spawned by initializeRow
	 */
	private void initializeRow(int row, ChessPiece.Color col) {
		board[row][0] = new Rook(col, Integer.toString(row));
		board[row][1] = new Knight(col, Integer.toString(row));
		board[row][2] = new Bishop(col, Integer.toString(row));
		board[row][3] = new Queen(col, Integer.toString(row));
		board[row][4] = new King(col, Integer.toString(row));
		board[row][5] = new Bishop(col, Integer.toString(row));
		board[row][6] = new Knight(col, Integer.toString(row));
		board[row][7] = new Rook(col, Integer.toString(row));
	}
	
	/**
	 * isValidMove is the main function in checking if any given input is valid. It takes the piece to be moved, denoted by (startRow,startCol) and checks if it can move to (endRow,endCol).
	 * This function also takes a chess pieces color to ensure a player is moving one of their own pieces.
	 * isValidMove works by checking first if the parameters are in the boards range, then if a piece exists in the start position and it belongs to the current player, then it calls that pieces specific isValidMove function
	 * Due to the heavier constraints on king movements, the kings isValidMove logic was moved into ChessBoard under the function "validKing"
	 * If the validMove functions return true, the function checks if the player is in "check", and if so, tests the move to see if the players king is out of check after executing the move. If so, return true
	 * @param startRow startRow is the row of the first input, where the piece to be moved is located
	 * @param startCol startCol is the column of the first input, where the piece to be moved is located
	 * @param endRow endRow is the row of the location attempting to be moved to
	 * @param endCol endCol is the column of the location attempting to be moved to
	 * @param color color is the color of the current player, used to check they are moving a piece they own
	 * @return Returns true if the player is moving a piece they own, in a legal way, that would not put their king into, or leave their king in check. Otherwise returns false.
	 */
	public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece.Color color) {
		if ((endRow>=8)||(endCol>=8) || (startRow>=8) || (startCol>=8)) {
			return false;
		}
		if ((endRow<0)||(endCol<0) || (startRow<0) || (startCol<0)) {
			return false;
		}
		
		if (startRow == endRow && startCol == endCol) {
			return false;
		}
		
		//If start location is empty, cant move anything
		if(board[startRow][startCol] == null) {return false;}
		
		ChessPiece movedPiece = board[startRow][startCol];

		//This checks that the player is moving their own piece
		if(movedPiece.getColor() != color) {return false;}
		
		
		
		//Kings require many additional checks when moving, call a function with better access to board functions here
		if(movedPiece instanceof King) {
			
			if(validKing(startRow, startCol, endRow, endCol)) {
				
				//if in check, see if the move would take king out of check, if not, its not a valid move
				if(inCheck) {
					ChessPiece temp = board[endRow][endCol];
					ChessPiece defender = board[startRow][startCol];
					board[endRow][endCol] = defender;
					board[startRow][startCol] = null;
					
					//If king is still in check, move is invalid, return pieces to start position
					if(isCheck(defender.getColor())) {
						board[startRow][startCol] = defender;
						board[endRow][endCol] = temp;
						return false;	
					}
					else {
						board[startRow][startCol] = defender;
						board[endRow][endCol] = temp;
						return true;
					}
					
				}
				
				return true;
			}
			else {return false;}
		}
		//end if king
		
		
		//if its not a king, call its personal isValid func
		if(!movedPiece.isValidMove(startRow, startCol, endRow, endCol, board)) {
			
			return false;
		}
		//Check if currently in check, if so, see if move will remove check
		
		//Test the move by swapp the pieces
		ChessPiece temp = board[endRow][endCol];
		ChessPiece defender = board[startRow][startCol];
		board[endRow][endCol] = defender;
		board[startRow][startCol] = null;
		//If already in check, see if the move removed check
		if(inCheck) {
			//If king is still in check, move is invalid, return pieces to start position
			if(isCheck(defender.getColor())) {
				board[startRow][startCol] = defender;
				board[endRow][endCol] = temp;
				return false;	
			}
			else {
				board[startRow][startCol] = defender;
				board[endRow][endCol] = temp;
				return true;
			}
			
		}
		//If not already in check, see if the move will open up your king to attack
		if (isCheck(color)) {
			board[startRow][startCol] = defender;
			board[endRow][endCol] = temp;
			return false;
		}
		//Otherwise, put pieces back, return true
		board[startRow][startCol] = defender;
		board[endRow][endCol] = temp;
		return true;
	}
	/**
	 * move is the function called to execute moving a piece. After isValidMove is called, if the move is valid, this function is called to execute input.
	 * If the move is valid, then it means the player is not in check, or exiting check, so inCheck is set to false. The turn counter is likewise incremented
	 * Afterwards, the function "isCheck" is run on the opponent to see if the move executed has put the enemy into check. inCheck is set accordingly
	 * @param startRow startRow is the row of the to be moved piece
	 * @param startCol startCol is the column of the to be moved piece
	 * @param endRow endRow is the end row location of the to be moved piece
	 * @param endCol endCol is the end column location of the to be moved piece
	 */
	public void move(int startRow, int startCol, int endRow, int endCol) {
		
		ChessPiece movedPiece = board[startRow][startCol];
		ChessPiece.Color moveColor = movedPiece.getColor();
		//if it reached here, current color no longer in check
		inCheck = false;
		
		if(movedPiece instanceof King) {
			moveKing(startRow, startCol, endRow, endCol);
			ChessPiece.turnCounter++;
			
			//See if movement puts opponent in check
			if(moveColor == ChessPiece.Color.Black) {
				if(isCheck(ChessPiece.Color.White)) {inCheck = true;}
			}
			if(moveColor == ChessPiece.Color.White) {
				if(isCheck(ChessPiece.Color.Black)) {inCheck = true;}
			}
			return;
		}
		
		//enpassant, had to put logic out here to avoid deletions on "isvalid" calls by the "Checkmate" checker
		//If a pawn returns valid diagonal move but nothing is in that spot, must be enpassant
		if(movedPiece instanceof Pawn && board[endRow][endCol] == null && (startCol != endCol)) {
			
				//delete the passed pawn
				board[startRow][endCol] = null;
			
			}
		
		
			movedPiece.setMovement(true);
			board[startRow][startCol] = null;
			board[endRow][endCol] = movedPiece;
			ChessPiece.turnCounter++;
			
			//See if movement puts opponent in check
			if(moveColor == ChessPiece.Color.Black) {
				if(isCheck(ChessPiece.Color.White)) {inCheck = true;}
			}
			if(moveColor == ChessPiece.Color.White) {
				if(isCheck(ChessPiece.Color.Black)) {inCheck = true;}
			}
			return;
		
	}
	

	
	/**
	 * isPathToKing checks if a given piece has a valid move to the enemies king. It does this by taking the piece which is passed by row and column in the parameters, and then checking every board location.
	 * If a board location contains the enemy king, it calls the pieces "isValidMove" function with its current location as the start row and column and the kings current position as the end row and column
	 * @param pieceRow pieceRow is the row of the piece to be tested
	 * @param pieceCol pieceCol is the column of the piece to be tested
	 * @return isPathToKing returns true if the piece can attack the enemy king, or false if it has no move to attack the king
	 */
	private boolean isPathToKing(int pieceRow, int pieceCol) {
		ChessPiece piece = board[pieceRow][pieceCol];
		ChessPiece.Color color = piece.getColor();
		int row, col;
		for (row = 0; row < 8; row++) {
			for (col = 0; col < 8; col++) {
				if ((board[row][col] instanceof King)&&(board[row][col].getColor()!=color)) {
					if (piece.isValidMove(pieceRow, pieceCol, row, col, board)) {
						 return true;
					}
				}
			}
		}
		return false;
		
	}
	
	
	/**
	 * isCheck determines if the passed players king is in Check. This is done by finding each enemy piece and calling the "isPathToKing" function on it.
	 * @param color color is the color of the player being tested for check. ie: isCheck(white) returns whether white is in check
	 * @return isCheck returns true if the player is in check, or false if they are not in check
	 */
		public boolean isCheck(ChessPiece.Color color) {
			
			for (int row = 0; row < 8; row++) {
				for (int col = 0; col < 8; col++) {
					if (board[row][col]!=null) {
						if (color!=board[row][col].getColor()) {
							if (isPathToKing(row, col)) {
								inCheck = true;
								return true;
							}
						}
					}
				}
			}
			inCheck = false;
			return false;
		}
		
		
	
	/**
	 * isCheckMate checks if the passed player is in checkmate. It does this by calling "canRemoveCheck" on every piece owned by the player. Those pieces are then tested for all possible moves they can make.
	 * If any move would break the check, isCheckMate returns false. Otherwise the player has no valid moves and is in checkmate, returning true
	 * @param color color is the color of the player being tested for checkmate. IE: isCheckMate(white) tests if white has lost the game
	 * @return isCheckMate returns true if the passed color player has lost, or false if they still have valid moves left
	 */
		public boolean isCheckMate(ChessPiece.Color color) {
	 		
	 		ChessPiece defender;
	 		for (int row = 0; row < 8; row++) {
	 			for (int col = 0; col < 8; col++) {
	 				if (board[row][col]!=null) {
	 					// if the piece has the same color as the king
						if (color==board[row][col].getColor()) {
	
	 						defender = board[row][col];
							if (canRemoveCheck(defender, row, col)) {
								
	 							return false;
							}
	 					}
	 				}
	 			}
	 		}
	 		return true;
	 	}
	/**
	 * canRemoveCheck cycles through all of a given pieces possible moves, and tests each one to see if it would remove check. It does this by calling "isValidMove" on all board locations for the given piece.
	 * If a move is valid, it then tests the move by putting the current piece in that spot into "temp" and making the move, then testing for "check" once again. If check is removed, the player has a valid move left.
	 * If no move by this piece can remove check, it returns false.
	 * @param defender defender is the chess piece being tested to see if it can removed the "check" on their king. defender can be any piece, including the players king
	 * @param row row is the row location of the defender
	 * @param col col is the column location of the defender
	 * @return canRemoveCheck returns true if the piece has a valid move that removes the "check" on their king. Returns false if no move exists
	 */
		private boolean canRemoveCheck(ChessPiece defender, int row, int col) {
		ChessPiece temp;
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y<8; y++) {
				
				if (defender instanceof King) {
					if (validKing(row, col, x, y)) {
						
						temp = board[x][y];
						board[x][y] = defender;
						board[row][col] = null;
		
						//if the move takes king out of check, not checkmate, return items to spots
						if (!isCheck(defender.getColor())) {
							board[row][col] = defender;
							board[x][y] = temp;
									return true;
						}
						board[row][col] = defender;
						board[x][y] = temp;
						
					}
				}
				else if(defender.isValidMove(row, col, x, y, board)) {
					
					temp = board[x][y];
					board[x][y] = defender;
					board[row][col] = null;
	
					//if the move takes king out of check, not checkmate, return items to spots
					if (!isCheck(defender.getColor())) {
						board[row][col] = defender;
						board[x][y] = temp;
								return true;
					}
					board[row][col] = defender;
					board[x][y] = temp;
					
				}
			}
		}
		//if no valid moves for defender take king out of check, removeCheck is false
		return false;
	}
		
	/**
	 * pawnPromotion reads the input to determine what piece the pawn should be promoted to. This is called by the "chess" main class
	 * Note: If no promotion was specified, the "chess" class passes a 'Q' to promote to queen
	 * @param row row is the row of the pawn being promoted
	 * @param col col is the column of the pawn being promoted
	 * @param piece piece is the char identifier of what piece the pawn is to be promoted to
	 */
	public void pawnPromotion(int row, int col, char piece) {
		ChessPiece.Color color = board[row][col].getColor();
		switch (piece) {
			case 'B': 
				board[row][col] = new Bishop(color, "Promo");
				break;
			case 'N':
				board[row][col] = new Knight(color, "Promo");
				break;
			case 'Q':
				board[row][col] = new Queen(color, "Promo");
				break;
			case 'R':
				board[row][col] = new Rook(color, "Promo");
				break;
			default:
				board[row][col] = new Queen(color, "Promo");
		}
	}

	/**
	 * validKing is the kings personal "isValidMove" function. As the kings movement is much more restricted, it was placed here in the chessBoard class. 
	 * ValidKing checks what type of move the king is doing. If the distance being moved is greater than 1, it checks if the king is trying to castle, and if so checks if the king and rook pair have moved
	 * as well as if any spaces the king will move through are attackable by the enemy. If the king is not trying to castle, it checks that the king is moving at most one space in any direction
	 * @param startRow startRow is the row the king starts in
	 * @param startCol startCol is the column the king starts in
	 * @param endRow endRow is the row the king is trying to move to
	 * @param endCol endCol is the column the king is trying to move to
	 * @return validKing returns true if the move is acceptable, false if it is invalid
	 */
	private boolean validKing(int startRow, int startCol, int endRow, int endCol) {
			
			
			ChessPiece piece = board[startRow][startCol];
			ChessPiece.Color color = piece.getColor();
			int castleDirection = (startCol - endCol);

			
			//Trying to castle
			if(startRow == endRow && Math.abs(startCol - endCol) == 2) {
				
				if(color == ChessPiece.Color.White) {
					//White Castling right
					if(castleDirection < 0) {
						if(board[0][7] == null) {return false;}
						ChessPiece temp = board[7][7];
						
						//if neither has moved and nothings in the way
						if(piece.moveHistory() == false && temp.moveHistory() == false && board[0][6] == null && board[0][5] == null) {
							if(!isSpotAttackable(piece.getColor(), 0,6) && !isSpotAttackable(piece.getColor(), 0, 5)) {
								return true;
							}
							
						}
						else {
							return false;
						}
						
					}
					//White Castling left
					else {
						if(board[0][0] == null) {return false;}
						ChessPiece temp = board[7][0];
						
						//if neither has moved and nothings in the way
						if(piece.moveHistory() == false && temp.moveHistory() == false && board[0][1] == null && board[0][2] == null && board[0][3] == null) {
							if(!isSpotAttackable(piece.getColor(), 0,1) && !isSpotAttackable(piece.getColor(), 0, 2) && !isSpotAttackable(piece.getColor(), 0, 3)) {
								return true;
							}
							
						}
						else {
							return false;
						}
						
					}
				}
				else {
					//Black Castling right
					if(castleDirection < 0) {
						
						if(board[7][7] == null) {return false;}
						ChessPiece temp = board[7][7];
						
						//if neither has moved and nothings in the way
						if(piece.moveHistory() == false && temp.moveHistory() == false && board[7][6] == null && board[7][5] == null) {
							if(!isSpotAttackable(piece.getColor(), 7,6) && !isSpotAttackable(piece.getColor(), 7, 5)) {
								return true;
							}
							
						}
						else {
							return false;
						}
						
					}
					//BLack Castling left
					else {
						if(board[7][0] == null) {return false;}
						ChessPiece temp = board[7][0];
						
						//if neither has moved and nothings in the way
						if(piece.moveHistory() == false && temp.moveHistory() == false && board[7][1] == null && board[7][2] == null && board[7][3] == null) {
							if(!isSpotAttackable(piece.getColor(), 7,1) && !isSpotAttackable(piece.getColor(), 7, 2) && !isSpotAttackable(piece.getColor(), 7, 3)) {
								return true;
							}
							
						}
						else {
							return false;
						}
						
					}
					
					
				}
				
			}
			//end trying to castle
		
			//Check if a friendly piece is in the end location
			if(board[endRow][endCol] != null) {
				ChessPiece temp = board[endRow][endCol];
				if(temp.getColor() == piece.getColor()) {return false;}
			}
			
			
			//King can only move 1 square in any direction. Ensure its moving into an unattacked spot
			
			
			if(Math.abs(startRow - endRow) <= 1 && Math.abs(startCol - endCol) <= 1 && !(isSpotAttackable(piece.getColor(), endRow, endCol))) {
				
				return true;
				
			}
			
			
			
			return false;
		}
		
	/**
	 * isSpotAttackable checks if a given space can be attacked by an enemy. This is used in checking if the king can castle through certain spaces
	 * @param color color is the color of the friendly team
	 * @param testRow testRow is the row of the location being tested for safety
	 * @param testCol testCol is the column of the location being tested for safety
	 * @return isSpotAttackable returns true if an enemy can attack the location, false if the space is safe.
	 */
	private boolean isSpotAttackable(ChessPiece.Color color, int testRow, int testCol) {
			//Have to consider that the spot might have an enemy piece, how to test if its attackable with when empty...
			boolean wasntEmpty = false;
			ChessPiece holder = null;
			//If spot isnt empty, remove its piece until later
			if(board[testRow][testCol] != null) {
				holder = board[testRow][testCol];
				board[testRow][testCol] = null;
				wasntEmpty = true;
			}
			
				int row, col;
				for (row = 0; row < 8; row++) {
					for (col = 0; col < 8; col++) {
						if (board[row][col]!= null) {
							ChessPiece temp = board[row][col];
							//If the current locations piece is an enemy
							if(temp.getColor() != color) {
								//test if the piece here can attack the passed location
								
								if (temp.isValidMove(row, col, testRow, testCol, board)) {
								
								if(wasntEmpty) {
									board[testRow][testCol] = holder;
								}
								
								 return true;
								}
							}
						}
					}
				}
			
			if(wasntEmpty) {
				board[testRow][testCol] = holder;
			}
				
			return false;
		}
	/**
	 * moveKing is the kings personal move function. It takes into account potentially castling, and calls castleKing if needed.
	 * @param startRow startRow is the row of the king
	 * @param startCol startCol is the column of the king
	 * @param endRow endRow is the row the king is moving to
	 * @param endCol endCol is the column the king is moving to
	 */
	private void moveKing(int startRow, int startCol, int endRow, int endCol) {

			//Check if its a castle, if so use castleKing, else move it
			if(Math.abs(startCol - endCol) == 2) {
				castleKing(startRow,startCol,endRow,endCol);
			}
			else {
				ChessPiece movedPiece = board[startRow][startCol];
					movedPiece.setMovement(true);
					board[startRow][startCol] = null;
					board[endRow][endCol] = movedPiece;
					return;
			}
			
			return;
		}
	/**
	 * castleKing is called when the king is castling. This will move the king horizontally 2 spaces, and place the pair rook to the inside of the king
	 * @param startRow startRow is the row of the king
	 * @param startCol startCol is the column of the king
	 * @param endRow endRow is the end row of the king
	 * @param endCol endCol is the end column of the king
	 */
	private void castleKing(int startRow, int startCol, int endRow, int endCol) {
			
			ChessPiece king = board[startRow][startCol];
			ChessPiece.Color color = king.getColor();
			int direction = (startCol - endCol);
			
			
			//Black castle left
			if(direction > 0 && color == ChessPiece.Color.Black) {
				ChessPiece rook = board[7][0];
				board[7][2] = king;
				board[7][3] = rook;
				board[7][0] = null;
				board[7][4] = null;
				
				
			}
			//Black castle right
			if(direction < 0 && color == ChessPiece.Color.Black) {
				
				ChessPiece rook = board[7][7];
				board[7][6] = king;
				board[7][5] = rook;
				board[7][7] = null;
				board[7][4] = null;
				
			}
			//White castle left
			if(direction > 0 && color == ChessPiece.Color.White) {
				ChessPiece rook = board[0][0];
				board[0][2] = king;
				board[0][3] = rook;
				board[0][0] = null;
				board[0][4] = null;
			}
			//White castle right
			if(direction < 0 && color == ChessPiece.Color.White) {
				ChessPiece rook = board[0][7];
				board[0][6] = king;
				board[0][5] = rook;
				board[0][7] = null;
				board[0][4] = null;
			}
			return;
		}
		
	}