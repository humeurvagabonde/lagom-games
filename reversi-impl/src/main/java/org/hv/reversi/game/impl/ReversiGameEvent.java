package org.hv.reversi.game.impl;

import java.time.Instant;

import org.hv.reversi.game.api.GameId;
import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;
import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.serialization.Jsonable;

public interface ReversiGameEvent extends Jsonable, AggregateEvent<ReversiGameEvent> {

    @Override
    default public AggregateEventTag<ReversiGameEvent> aggregateTag() {
        return ReversiGameEventTag.INSTANCE;
    }

    @Value.Immutable
    @ImmutableStyle
    @JsonDeserialize(as = GameCreated.class)
    interface AbstractGameCreated extends ReversiGameEvent {
        @Value.Parameter
        GameId gameId();
        
        @Value.Parameter
        default Instant timestamp() {
            return Instant.now();
        }
    }
}
