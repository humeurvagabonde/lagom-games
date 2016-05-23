package org.hv.reversi.game.impl;

import java.util.Optional;

import org.hv.reversi.game.api.Game;
import org.hv.reversi.game.impl.ReversiGameEvent.GameCreated;

import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

public class ReversiGameEntity extends PersistentEntity<ReversiGameCommand, ReversiGameEvent, ReversiGameState> {

	@Override
	public Behavior initialBehavior(Optional<ReversiGameState> snapshotState) {

		BehaviorBuilder b = newBehaviorBuilder(snapshotState.orElse(ReversiGameState.builder().build()));

		b.setCommandHandler(CreateGame.class, (cmd, ctx) -> {
			if (state().game().isPresent()) {
				ctx.invalidCommand("Reversi game " + entityId() + " is already created");
				return ctx.done();
			}
			return ctx.thenPersist(new GameCreated(cmd.gameId()), (evt) -> ctx.reply(CreateGameReply.of(cmd.gameId())));

		});

		b.setEventHandler(GameCreated.class, evt -> ReversiGameState.of(Optional.of(new Game(evt.gameId))));

		b.setReadOnlyCommandHandler(GetGame.class, (cmd, ctx) -> {
			ctx.reply(GetGameReply.of(state().game()));
		});

		return b.build();
	}

}
