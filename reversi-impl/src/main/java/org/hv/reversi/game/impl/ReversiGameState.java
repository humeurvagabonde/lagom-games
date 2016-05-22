package org.hv.reversi.game.impl;

import java.util.Optional;

import javax.annotation.concurrent.Immutable;

import org.hv.reversi.game.api.Game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.serialization.Jsonable;

@SuppressWarnings("serial")
@Immutable
@JsonDeserialize
public class ReversiGameState implements Jsonable {

    public final Optional<Game> game;
    
    @JsonCreator
    public ReversiGameState(Optional<Game> game) {
      this.game = Preconditions.checkNotNull(game, "game");
    }
}