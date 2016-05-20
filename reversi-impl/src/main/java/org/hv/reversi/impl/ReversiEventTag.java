package org.hv.reversi.impl;

import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;

public class ReversiEventTag {

    public static final AggregateEventTag<ReversiEvent> INSTANCE = AggregateEventTag.of(ReversiEvent.class);
    
}
