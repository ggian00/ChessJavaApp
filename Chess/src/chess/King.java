package chess;
/**
 * This is the king subclass of ChessPiece. Most of the king logic is stored in the ChessBoard.java file. This is still used for identifying where a king is on the board(using instanceof King) and for printing purposes
 * @author Scott Lahart, Niles Ball
 *
 */
public class King extends ChessPiece{
	
	/**
	 * isValidMove in King is never used, but is declared to satisfy inheriting the abstract function
	 */
	public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] board) {
		
		//This function shouldnt be called
		if (Math.abs(endRow-startRow) != 1 || (Math.abs(endCol-startCol) != 1)){return false;}
		
		return true;
	}

	
	/**
	 * Constructor for Kings. Sets the color, name and hasMoved to false
	 * @param col col is the color of the king
	 * @param n n is the name of the king
	 */
	public King(ChessPiece.Color col, String n) {
		this.setColor(col);
		this.setMovement(false);
		this.setName("k"+n);
	}
	/**
	 * toString is the override of toString for kings. Kings are printed with a 'w' or 'b' followed by a capital 'K', depending on their color.
	 */
	public String toString() {
		if(this.getColor() == ChessPiece.Color.White) {return "w" + "K";}
		
		return "b" + "K";
	}

}
