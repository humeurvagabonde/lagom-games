package java.org.hv.reversi.impl;

import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.serialization.Jsonable;


public interface ReversiEvent extends Jsonable, AggregateEvent<ReversiEvent> {
    
}
