package org.hv.reversi.impl;

import java.time.Instant;
import java.util.Optional;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.serialization.Jsonable;

public interface ReversiEvent extends Jsonable, AggregateEvent<ReversiEvent> {

    @Override
    default public AggregateEventTag<ReversiEvent> aggregateTag() {
        return ReversiEventTag.INSTANCE;
    }

    @SuppressWarnings("serial")
    @Immutable
    @JsonDeserialize
    public class GameCreated implements ReversiEvent {
        public final String gameId;
        public final Instant timestamp;

        public GameCreated(String gameId) {
            this(gameId, Optional.empty());
        }

        @JsonCreator
        public GameCreated(String gameId, Optional<Instant> timestamp) {
            this.gameId = Preconditions.checkNotNull(gameId, "gameId");
            this.timestamp = timestamp.orElseGet(() -> Instant.now());
        }

        @Override
        public boolean equals(@Nullable Object another) {
            if (this == another) {
                return true;
            }
            return another instanceof GameCreated && equalTo((GameCreated) another);
        }

        private boolean equalTo(GameCreated another) {
            return gameId.equals(another.gameId) && timestamp.equals(another.timestamp);
        }

        @Override
        public int hashCode() {
            int h = 31;
            h = h * 17 + gameId.hashCode();
            h = h * 17 + timestamp.hashCode();
            return h;
        }
    }
}
