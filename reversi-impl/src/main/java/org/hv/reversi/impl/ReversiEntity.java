package org.hv.reversi.impl;

import java.util.Optional;

import org.hv.reversi.api.Game;
import org.hv.reversi.impl.ReversiCommand.CreateGame;
import org.hv.reversi.impl.ReversiEvent.GameCreated;

import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import akka.Done;

public class ReversiEntity extends PersistentEntity<ReversiCommand, ReversiEvent, ReversiState> {

    @Override
    public Behavior initialBehavior(Optional<ReversiState> snapshotState) {

        BehaviorBuilder b = newBehaviorBuilder(snapshotState.orElse(
                new ReversiState(Optional.empty())));

        b.setCommandHandler(CreateGame.class, (cmd, ctx) -> {
            if (state().game.isPresent()) {
                ctx.invalidCommand("Reversi game " + entityId() + " is already created");
                return ctx.done();
            }
            return ctx.thenPersist(new GameCreated(cmd.gameId), (evt) -> ctx.reply(Done.getInstance()));

        });
        
        b.setEventHandler(GameCreated.class,
                evt -> new ReversiState(Optional.of(new Game(evt.gameId))));

        return b.build();
    }

}
