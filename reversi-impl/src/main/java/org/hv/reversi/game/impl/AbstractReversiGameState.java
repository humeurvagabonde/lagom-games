package org.hv.reversi.game.impl;

import java.util.Optional;

import org.hv.reversi.game.api.Game;
import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;
import com.lightbend.lagom.serialization.Jsonable;

@SuppressWarnings("serial")
@Value.Immutable
@ImmutableStyle
@JsonDeserialize(as = ReversiGameState.class)
public abstract class AbstractReversiGameState implements Jsonable {

	@Value.Parameter
	abstract Optional<Game> game();
    
    @Value.Check
	protected void check() {
		Preconditions.checkState(game() != null, "'game' cannot be null");
	}	
}