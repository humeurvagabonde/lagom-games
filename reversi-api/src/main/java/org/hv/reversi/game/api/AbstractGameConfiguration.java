package org.hv.reversi.game.api;

import org.hv.user.api.UserId;
import org.immutables.value.Value;
import org.pcollections.HashTreePMap;
import org.pcollections.PMap;

import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;

@Value.Immutable
@ImmutableStyle
interface AbstractGameConfiguration {

    UserId owner();
    int nbPlayerMin();
    int nbPlayerMax();
    
    @Value.Default
    default PMap<UserId, Color> players() {
    	return HashTreePMap.empty();
    }
}
