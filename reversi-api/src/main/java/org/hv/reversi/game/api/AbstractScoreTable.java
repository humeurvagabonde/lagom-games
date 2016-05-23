package org.hv.reversi.game.api;

import org.hv.user.api.UserId;
import org.immutables.value.Value;
import org.pcollections.PMap;

import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;

@Value.Immutable
@ImmutableStyle
public interface AbstractScoreTable {

    PMap<UserId, Score> scoreTable();
    
}
