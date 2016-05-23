package org.hv.reversi.game.api;

import org.immutables.value.Value;

import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;

@Value.Immutable
@ImmutableStyle
public interface AbstractGameBoard {

	enum Color {
		BLACK,
		WHITE
	}
	
	@Value.Immutable
	@ImmutableStyle
	interface Pos {
		int x();
		int y();
	}
	
	@Value.Default
	default Color[][] board() {
		return new Color[7][7];
	}
	
	/*
	 * 	/**
	 * Finds valid moves for specific piece
	 * @param board the board
	 * @param piece the piece need to find move
	 * @param isSuggest true to indicate suggested pieces on the board
	 * @return an array list of moves
	public static ArrayList<MoveCoord> findValidMove(char[][] board, char piece, boolean isSuggest) {
		char suggestPiece = (piece == sBLACK_PIECE) ? sSUGGEST_BLACK_PIECE : sSUGGEST_WHITE_PIECE;
		
		ArrayList<MoveCoord> moveList = new ArrayList<MoveCoord>();
		for (int i = 0; i < 8; ++i)
			for (int j = 0; j < 8; ++j) {
				// clean the suggest piece before
				if (board[i][j] == sSUGGEST_BLACK_PIECE || board[i][j] == sSUGGEST_WHITE_PIECE)
					board[i][j] = sEMPTY_PIECE;
				
				if (isValidMove(board,piece, i, j))
				{
					moveList.add(new MoveCoord(i, j));
					
					// if we want suggestion, mark on board
					if (isSuggest)
						board[i][j] = suggestPiece;
				}
			}
		
		return moveList;
	}
	/**
	 * Check whether a move is valid
	 * @param board the board
	 * @param piece the piece need to check
	 * @param row row of the move
	 * @param col column of the move
	 * @return true if the move is valid, false otherwise
	public static boolean isValidMove(char[][] board, char piece, int row, int col) {
		// check whether this square is empty
		if (board[row][col] != sEMPTY_PIECE)
			return false;
		
		char oppPiece = (piece == sBLACK_PIECE) ? sWHITE_PIECE : sBLACK_PIECE;
		
		boolean isValid = false;
		// check 8 directions
		for (int i = 0; i < 8; ++i) {
			int curRow = row + sOFFSET_MOVE_ROW[i];
			int curCol = col + sOFFSET_MOVE_COL[i];
			boolean hasOppPieceBetween = false;
			while (curRow >=0 && curRow < 8 && curCol >= 0 && curCol < 8) {
				
				if (board[curRow][curCol] == oppPiece)
					hasOppPieceBetween = true;
				else if ((board[curRow][curCol] == piece) && hasOppPieceBetween)
				{
					isValid = true;
					break;
				}
				else
					break;
				
				curRow += sOFFSET_MOVE_ROW[i];
				curCol += sOFFSET_MOVE_COL[i];
			}
			if (isValid)
				break;
		}
		
		return isValid;
	}
	
	 */
	
}
