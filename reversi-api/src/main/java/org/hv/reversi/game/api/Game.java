package org.hv.reversi.game.api;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;

@Immutable
@JsonDeserialize
public final class Game {

    private GameId id;
    private GameConfiguration configuration;
    private GameProgression progression;
    private GameBoard board;

    public Game(GameId gameId) {
        this.id = gameId;
    }

    @Override
    public boolean equals(@Nullable Object another) {
        if (this == another)
            return true;
        return another instanceof Game && equalTo((Game) another);
    }

    private boolean equalTo(Game another) {
        return id.equals(another.id);
    }

    @Override
    public int hashCode() {
        int h = 31;
        h = h * 17 + id.hashCode();
        return h;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("Game").add("id", id.toString()).toString();
    }

}
