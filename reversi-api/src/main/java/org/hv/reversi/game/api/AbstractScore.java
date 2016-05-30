package org.hv.reversi.game.api;

import org.immutables.value.Value;

import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;

@Value.Immutable
@ImmutableStyle
interface AbstractScore {

	@Value.Parameter
    int score();
 
}
