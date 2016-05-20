package org.hv.reversi.impl;

import java.util.Optional;

import javax.annotation.concurrent.Immutable;

import org.hv.reversi.api.Game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.serialization.Jsonable;

@SuppressWarnings("serial")
@Immutable
@JsonDeserialize
public class ReversiState implements Jsonable {

    public final Optional<Game> game;
    
    @JsonCreator
    public ReversiState(Optional<Game> game) {
      this.game = Preconditions.checkNotNull(game, "game");
    }
}