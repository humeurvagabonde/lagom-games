package org.hv.reversi.impl;

import java.util.Optional;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.hv.reversi.api.Game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.Jsonable;

import akka.Done;

public interface ReversiCommand extends Jsonable {

    @SuppressWarnings("serial")
    @Immutable
    @JsonDeserialize
    public final class CreateGame implements ReversiCommand, PersistentEntity.ReplyType<Done> {

        public String gameId;
        
        @JsonCreator
        public CreateGame(String gameId) {
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
    public final class GetGame implements ReversiCommand, PersistentEntity.ReplyType<GetGameReply> {

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
