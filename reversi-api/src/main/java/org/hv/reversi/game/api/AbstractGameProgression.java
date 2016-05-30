package org.hv.reversi.game.api;

import org.hv.user.api.UserId;
import org.immutables.value.Value;

import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;

@Value.Immutable
@ImmutableStyle
interface AbstractGameProgression {

    UserId currentPlayer();
    UserId currentState();
    ScoreTable scoreTable();
}
