package org.hv.reversi.game.api;

import org.immutables.value.Value;

import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;

@Value.Immutable
@ImmutableStyle
public interface AbstractGameBoard {
	
	@Value.Immutable
	@ImmutableStyle
	interface Pos {
	    @Value.Parameter
		int x();
	    @Value.Parameter
		int y();
	}
	
	@Value.Default
	default Color[][] board() {
		return new Color[7][7];
	}
}
