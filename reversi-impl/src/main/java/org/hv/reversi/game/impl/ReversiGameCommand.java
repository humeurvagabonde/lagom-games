package org.hv.reversi.game.impl;

import java.util.Optional;

import org.hv.reversi.game.api.Game;
import org.hv.reversi.game.api.GameId;
import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.Jsonable;

public interface ReversiGameCommand extends Jsonable {

	@Value.Immutable
	@ImmutableStyle
	@JsonDeserialize(as = CreateGame.class)
	interface AbstractCreateGame extends ReversiGameCommand, PersistentEntity.ReplyType<CreateGameReply> {
		@Value.Parameter
		GameId gameId();
	}

	@SuppressWarnings("serial")
	@Value.Immutable
	@ImmutableStyle
	@JsonDeserialize(as = CreateGameReply.class)
	abstract class AbstractCreateGameReply implements Jsonable {
		@Value.Parameter
		abstract GameId gameId();
		
		@Value.Check
		protected void check() {
			Preconditions.checkState(gameId() != null, "'game Id' must be specified");
		}
	}
	
	@SuppressWarnings("serial")
	@Value.Immutable(singleton = true, builder = false)
	@ImmutableStyle
	@JsonDeserialize(as = GetGame.class)
	abstract class AbstractGetGame implements ReversiGameCommand, PersistentEntity.ReplyType<GetGameReply> {
		protected AbstractGetGame() {
	    }
	}

	@SuppressWarnings("serial")
	@Value.Immutable
	@ImmutableStyle
	@JsonDeserialize(as = GetGameReply.class)
	abstract class AbstractGetGameReply implements Jsonable {
		@Value.Parameter
		abstract Optional<Game> game();

		@Value.Check
		protected void check() {
			Preconditions.checkState(game() != null, "'game' cannot be null");
		}	
	}
}
