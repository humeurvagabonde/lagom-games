package org.hv.reversi.game.api;

import org.immutables.value.Value;

import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;

@Value.Immutable
@ImmutableStyle
public interface AbstractGameProgression {

    String currentPlayer();
    String currentState();
    
}
