package org.hv.reversi.game.api;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;

@Value.Immutable
@ImmutableStyle
@JsonSerialize(as = GameEvent.class)
public interface AbstractGameEvent {

}
