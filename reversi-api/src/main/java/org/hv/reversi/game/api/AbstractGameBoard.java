package org.hv.reversi.game.api;

import org.hv.user.api.UserId;
import org.immutables.value.Value;

import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;

@Value.Immutable
@ImmutableStyle
public abstract class AbstractGameBoard {

	enum Color {
		BLACK,
		WHITE
	}
	
	@Value.Default
	Color[][] board() {
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
	 */
	
}
