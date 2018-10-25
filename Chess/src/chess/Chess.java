package chess;

import java.util.*;

/**
 * 
 * The chess class is where Main is contained. It creates an instance of ChessBoard which controls the game logic and then begins taking inputs until the game is over
 * 
 * @author Scott Lahart, Niles Ball
 *
 */
public class Chess {
	/**
	 * Used for getting player input
	 */
	static Scanner scanner;
	/**
	 * The board in which the chess game takes place
	 */
	static ChessBoard board;
	/**
	 * Flag used to determine if a player has asked for a draw
	 */
	static int drawFlag;
	
	/**
	 * 
	 * Main method. Creates a scanner, an instance of ChessBoard and then calls the runGame function
	 * @param args args is not used
	 */
	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		board = new ChessBoard();
		drawFlag = 0;
		runGame();
	}
		/**
		 * runGame controls user inputs. This function handles calling board prints, asking for user input, calling "checkmate" check functions and tracking whose turn it is.
		 */
	public static void runGame() {
		ChessPiece.Color white = ChessPiece.Color.White;
		ChessPiece.Color black = ChessPiece.Color.Black;
		boolean isCheck = false;
		while (true) {
			
			
			board.printBoard();
			if (board.isCheck(white)) {
				System.out.println("Check");
				System.out.println();
			}
			readInstruction(white);

			if (board.isCheckMate(black)) {
				System.out.println("Checkmate");
				System.out.println();
				System.out.println("White wins");
				return;
			}
			
			board.printBoard();
			if (board.isCheck(black)) {
				System.out.println("Check");
				System.out.println();
			}
			readInstruction(black);
			
			if (board.isCheckMate(white)) {
				System.out.println("Checkmate");
				System.out.println();
				System.out.println("Black wins");
				return;
			}
			
		}
	}
	/**
	 * readInstruction prints a message declaring whose move it is, denoted by the player color passed as its only param. It then calls playerMove and will keep calling this function until it returns true.
	 * playerMove only returns true when given a valid input by the user	
	 * @param color color is the color of the player whose turn it currently is
	 */
	public static void readInstruction(ChessPiece.Color color) {
		do {
			if (color==ChessPiece.Color.White) {
				System.out.print("White's move: ");	
			}
			else {
				System.out.print("Black's move: ");
			}
		} while(!playerMove(color));
	}
		 	
	/**
	 * playerMove handles taking in user input from the console and checking if it is valid. It handles resigns, draws, converting letters into usable integer values and calling the appropriate ChessBoard
	 * functions to determine if the input is valid. This function also calls the pawn promotion function if a pawn reached the boards edge on its turn.
	 * @param color color is once again the identifier of whose turn it is
	 * @return playerMove returns true is the input was a valid turn, or false if there was an error. Before returning false, "Illegal move, try again" is printed and the user is promoted to input again
	 */
	public static boolean playerMove(ChessPiece.Color color) {
		String instruction = scanner.nextLine();
		System.out.println();
		instruction = instruction.trim();
		
		// dealing with resigning
		if (instruction.equals("resign")) {
			if (ChessPiece.Color.Black == color) {
				System.out.println("White wins");
			}
			else {
				System.out.println("Black wins");
			}
			System.exit(0);
		}
		
		// d isnt used in promotion, so if d is at position 6, player must haved asked for a draw, set flag. If next turn draw isnt written, drawFlag will go back to 0
		if (instruction.equals("draw") && drawFlag == 1) {
			System.exit(0);
		}
		//If attempting to draw when enemy didnt offer, return
		if(instruction.equals("draw") && drawFlag == 0) {
			System.out.println("Illegal move, try again\n");
			return false;
		}
		drawFlag = 0;
		//Check if player asks for a draw
		if (instruction.length()>=7 && instruction.charAt(6) == 'd'){
			drawFlag = 1;
		}
		//if input is too small
		if((instruction.length()<5)){
			System.out.println("Illegal move, try again\n");
			return false;
		}
		
		
		int startRow, endRow, startCol, endCol;
		startCol = instruction.charAt(0) - 'a';
		startRow = instruction.charAt(1) - '1';
		endCol = instruction.charAt(3) - 'a';
		endRow = instruction.charAt(4) - '1';
			
		
		if (board.isValidMove(startRow, startCol, endRow, endCol, color)) {
			board.move(startRow, startCol, endRow, endCol);
			
			// deals with pawn promotion
			if ((board.board[endRow][endCol] instanceof Pawn)&&(endRow==7 || endRow == 0)) {
				if (instruction.length()>=7) {
					board.pawnPromotion(endRow, endCol, instruction.charAt(6));
				}
				else {
					board.pawnPromotion(endRow, endCol, 'Q');
				}
			}
			return true;
			
		}
		else {
			System.out.println("Illegal move, try again\n");
			return false;
		}
	}	
}