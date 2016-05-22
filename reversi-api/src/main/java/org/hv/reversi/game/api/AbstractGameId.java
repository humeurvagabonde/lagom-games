package org.hv.reversi.game.api;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;

@Value.Immutable
@ImmutableStyle
@JsonDeserialize
public interface AbstractGameId {

	@Value.Parameter
    String id();
	
}
