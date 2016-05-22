package org.hv.reversi.game.impl;

import java.util.Optional;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.hv.reversi.game.api.Game;
import org.hv.reversi.game.api.GameId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.Jsonable;

public interface ReversiGameCommand extends Jsonable {

	@SuppressWarnings("serial")
	@Immutable
	@JsonDeserialize
	public final class CreateGame implements ReversiGameCommand, PersistentEntity.ReplyType<CreateGameReply> {

		public GameId gameId;

		@JsonCreator
		public CreateGame(GameId gameId) {
			this.gameId = gameId;
		}

		@Override
		public boolean equals(@Nullable Object another) {
			if (this == another) {
				return true;
			}
			return another instanceof CreateGame && equalTo((CreateGame) another);
		}

		private boolean equalTo(CreateGame another) {
			return gameId.equals(another.gameId);
		}

		@Override
		public int hashCode() {
			int h = 31;
			h = h * 17 + gameId.hashCode();
			return h;
		}

		@Override
		public String toString() {
			return MoreObjects.toStringHelper("CreateGame").add("gameId", gameId).toString();
		}
	}

	@SuppressWarnings("serial")
	@Immutable
	@JsonDeserialize
	public final class CreateGameReply implements Jsonable {
		public final GameId gameId;

		@JsonCreator
        public CreateGameReply(GameId gameId) {
            this.gameId = Preconditions.checkNotNull(gameId, "reversi game id");
        }

		@Override
		public boolean equals(@Nullable Object another) {
			if (this == another) {
				return true;
			}
			return another instanceof CreateGameReply && equalTo((CreateGameReply) another);
		}

		private boolean equalTo(CreateGameReply another) {
			return gameId.equals(another.gameId);
		}

		@Override
		public int hashCode() {
			int h = 31;
			h = h * 17 + gameId.hashCode();
			return h;
		}

		@Override
		public String toString() {
			return MoreObjects.toStringHelper("CreateGameReply").add("gameId", gameId).toString();
		}
	}

	@SuppressWarnings("serial")
	@Immutable
	@JsonDeserialize
	public final class GetGame implements ReversiGameCommand, PersistentEntity.ReplyType<GetGameReply> {

		@Override
		public boolean equals(@Nullable Object another) {
			return this instanceof GetGame;
		}

		@Override
		public int hashCode() {
			return 2053226012;
		}

		@Override
		public String toString() {
			return "GetGame{}";
		}
	}

	@SuppressWarnings("serial")
	@Immutable
	@JsonDeserialize
	public final class GetGameReply implements Jsonable {
		public final Optional<Game> game;

		@JsonCreator
		public GetGameReply(Optional<Game> game) {
			this.game = Preconditions.checkNotNull(game, "reversi game");
		}

		@Override
		public boolean equals(@Nullable Object another) {
			if (this == another) {
				return true;
			}
			return another instanceof GetGameReply && equalTo((GetGameReply) another);
		}

		private boolean equalTo(GetGameReply another) {
			return game.equals(another.game);
		}

		@Override
		public int hashCode() {
			int h = 31;
			h = h * 17 + game.hashCode();
			return h;
		}

		@Override
		public String toString() {
			return MoreObjects.toStringHelper("GetGameReply").add("game", game).toString();
		}
	}
}
