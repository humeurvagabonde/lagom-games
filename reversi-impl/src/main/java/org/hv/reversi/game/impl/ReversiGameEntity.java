package org.hv.reversi.game.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.hv.reversi.game.api.Color;
import org.hv.reversi.game.api.Game;
import org.hv.reversi.game.api.GameBoard;
import org.hv.reversi.game.api.Pos;
import org.pcollections.HashTreePBag;
import org.pcollections.PCollection;
import org.pcollections.TreePVector;

import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

public class ReversiGameEntity extends PersistentEntity<ReversiGameCommand, ReversiGameEvent, ReversiGameState> {

    /** move offset for row */
    private static final int[] OFFSET_MOVE_ROW = {-1, -1, -1,  0,  0,  1,  1,  1};
    
    /** move offset for column */
    private static final int[] OFFSET_MOVE_COL = {-1,  0,  1, -1,  1, -1,  0,  1};
    
	@Override
	public Behavior initialBehavior(Optional<ReversiGameState> snapshotState) {

		BehaviorBuilder b = newBehaviorBuilder(snapshotState.orElse(ReversiGameState.builder().build()));

		b.setCommandHandler(CreateGame.class, (cmd, ctx) -> {
			if (state().game().isPresent()) {
				ctx.invalidCommand("Reversi game " + entityId() + " is already created");
				return ctx.done();
			}
			return ctx.thenPersist(GameCreated.of(cmd.gameId()), (evt) -> ctx.reply(CreateGameReply.of(cmd.gameId())));

		});

		b.setEventHandler(GameCreated.class, evt -> ReversiGameState.of(Optional.of(Game.builder().id(evt.gameId()).build())));

		b.setCommandHandler(PlayDisc.class, (cmd, ctx) -> {
		    List<ReversiGameEvent> events = new ArrayList<>();
			// 1. check command if Pos is in available moves
		    // 2. apply game logic
		    Pair<GameBoard, PCollection<Pos>> changes = playDisc(cmd.discPosition());
		    // 3. check end game
		    if (isGameEnded(changes.getLeft())) {
		        
		    }
		    
			return ctx.thenPersistAll(events, () -> ctx.done());
		});
		
		b.setReadOnlyCommandHandler(GetGame.class, (cmd, ctx) -> {
			ctx.reply(GetGameReply.of(state().game()));
		});

		return b.build();
	}
	
	private Pair<GameBoard, PCollection<Pos>> playDisc(Pos pos) {
	    GameBoard board = state().game().get().board();
	    return Pair.of(board, TreePVector.singleton(pos));
	}
	
	private boolean isGameEnded(GameBoard boardgame) {
	    // When neither player can move, the game ends
	    return availableMoves(boardgame, Color.BLACK).plusAll(availableMoves(boardgame, Color.WHITE)).isEmpty();
	}
	
	private PCollection<Pos> availableMoves(GameBoard boardgame, Color color) {
	    PCollection<Pos> availableMoves = HashTreePBag.empty();
	    Color[][] board = boardgame.board();
	    for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                Pos testedPos = Pos.of(i, j);
                if (isValidMove(board, color, testedPos)) {
                    availableMoves = availableMoves.plus(testedPos);
                }
            }
	    }
	    return availableMoves;
	}

	private boolean isValidMove(Color[][] board, Color color, Pos pos) {
	    // check whether this square is empty
        if (board[pos.x()][pos.y()] != null) {
            return false;
        }
        
        Color opponent = color.opposite();
        boolean isValid = false;
        
        // check 8 directions
        for (int i = 0; i < 8; ++i) {
            int curRow = pos.y() + OFFSET_MOVE_ROW[i];
            int curCol = pos.x() + OFFSET_MOVE_COL[i];
            Pos curPos = Pos.of(curCol, curRow);

            boolean hasOppPieceBetween = false;
            while (isPosInsideBoard(curPos)) {
                if (board[curPos.x()][curPos.y()].equals(opponent)) {
                    hasOppPieceBetween = true;
                } else if ((board[curPos.x()][curPos.y()].equals(color)) && hasOppPieceBetween) {
                    isValid = true;
                    break;
                } else {
                    break;
                }
                
                // move to next position using the "same direction"
                curRow += OFFSET_MOVE_ROW[i];
                curCol += OFFSET_MOVE_COL[i];
                curPos = Pos.builder().x(curCol).y(curRow).build();
            }
            if (isValid) {
                break;
            }
        }
        
        return isValid;
	}
	
	private boolean isPosInsideBoard(Pos pos) {
	    return pos.y() >=0 && pos.y() < 8 
	            && pos.x() >= 0 && pos.x() < 8;
	}
}
