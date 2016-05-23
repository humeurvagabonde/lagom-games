package org.hv.reversi.game.impl;

import java.util.Optional;

import org.hv.reversi.game.api.Game;
import org.hv.reversi.game.api.GameBoard;
import org.hv.reversi.game.api.GameId;
import org.hv.reversi.game.api.Pos;
import org.hv.user.api.UserId;
import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.Jsonable;

import akka.Done;

public interface ReversiGameCommand extends Jsonable {

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
			Preconditions.checkArgument(gameId() != null, "'game Id' must be specified");
		}
	}
	
	@Value.Immutable
	@ImmutableStyle
	@JsonDeserialize(as = JoinGame.class)
	interface AbstractJoinGame extends ReversiGameCommand, PersistentEntity.ReplyType<Done> {
		@Value.Parameter
		UserId userId();
		
		@Value.Parameter
		GameId gameId();
		
		@Value.Check
		default void check() {
			Preconditions.checkArgument(userId() != null, "'user Id' cannot be null");
			Preconditions.checkArgument(gameId() != null, "'game Id' cannot be null");
		}
	}
	
	@Value.Immutable
	@ImmutableStyle
	@JsonDeserialize(as = StartGame.class)
	interface AbstractStartGame extends ReversiGameCommand, PersistentEntity.ReplyType<Done> {
		@Value.Parameter
		UserId userId();
		
		@Value.Parameter
		GameId gameId();
		
		@Value.Check
		default void check() {
			Preconditions.checkArgument(userId() != null, "'user Id' cannot be null");
			Preconditions.checkArgument(gameId() != null, "'game Id' cannot be null");
		}
	}
	
	@Value.Immutable
	@ImmutableStyle
	@JsonDeserialize(as = PlayDisc.class)
	interface AbstractPlayDisc extends ReversiGameCommand, PersistentEntity.ReplyType<Done> {
		@Value.Parameter
		UserId userId();
		
		@Value.Parameter
		GameId gameId();
	
		@Value.Parameter
		Pos discPosition();
	}
	
}
