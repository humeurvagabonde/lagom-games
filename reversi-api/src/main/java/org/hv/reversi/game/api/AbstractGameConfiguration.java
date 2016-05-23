package org.hv.reversi.game.api;

import org.hv.user.api.UserId;
import org.immutables.value.Value;
import org.pcollections.PCollection;
import org.pcollections.TreePVector;

import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;

@Value.Immutable
@ImmutableStyle
public interface AbstractGameConfiguration {

    UserId owner();
    int nbPlayerMin();
    int nbPlayerMax();
    
    @Value.Default
    default PCollection<UserId> players() {
    	return TreePVector.empty();
    }
    
}
