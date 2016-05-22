package org.hv.reversi.game.impl;

import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;

public class ReversiGameEventTag {

    public static final AggregateEventTag<ReversiGameEvent> INSTANCE = AggregateEventTag.of(ReversiGameEvent.class);
    
}
