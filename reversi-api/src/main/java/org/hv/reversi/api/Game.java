package org.hv.reversi.api;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;

@Immutable
@JsonDeserialize
public final class Game {

    private String id;

    public Game(String gameId) {
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
        return MoreObjects.toStringHelper("Game").add("id", id).toString();
    }

}
