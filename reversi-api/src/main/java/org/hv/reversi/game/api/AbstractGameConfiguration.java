package org.hv.reversi.game.api;

import org.immutables.value.Value;
import org.pcollections.PCollection;

import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;

@Value.Immutable
@ImmutableStyle
public interface AbstractGameConfiguration {

    String owner();
    PCollection<String> players();
    int nbPlayerMin();
    int nbPlayerMax();
    
}
