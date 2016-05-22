package org.hv.reversi.game.impl;

import java.util.Optional;

import org.hv.reversi.game.api.Game;
import org.hv.reversi.game.impl.ReversiGameCommand.CreateGame;
import org.hv.reversi.game.impl.ReversiGameCommand.CreateGameReply;
import org.hv.reversi.game.impl.ReversiGameCommand.GetGame;
import org.hv.reversi.game.impl.ReversiGameCommand.GetGameReply;
import org.hv.reversi.game.impl.ReversiGameEvent.GameCreated;

import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

public class ReversiGameEntity extends PersistentEntity<ReversiGameCommand, ReversiGameEvent, ReversiGameState> {

	@Override
	public Behavior initialBehavior(Optional<ReversiGameState> snapshotState) {

		BehaviorBuilder b = newBehaviorBuilder(snapshotState.orElse(new ReversiGameState(Optional.empty())));

		b.setCommandHandler(CreateGame.class, (cmd, ctx) -> {
			if (state().game.isPresent()) {
				ctx.invalidCommand("Reversi game " + entityId() + " is already created");
				return ctx.done();
			}
			return ctx.thenPersist(new GameCreated(cmd.gameId), (evt) -> ctx.reply(new CreateGameReply(cmd.gameId)));

		});

		b.setEventHandler(GameCreated.class, evt -> new ReversiGameState(Optional.of(new Game(evt.gameId))));

		b.setReadOnlyCommandHandler(GetGame.class, (cmd, ctx) -> {
			ctx.reply(new GetGameReply(state().game));
		});

		return b.build();
	}

}
