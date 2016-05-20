package org.hv.reversi.impl;

import java.util.UUID;

import javax.inject.Inject;

import org.hv.reversi.api.Game;
import org.hv.reversi.api.ReversiService;
import org.hv.reversi.impl.ReversiCommand.CreateGame;
import org.hv.reversi.impl.ReversiCommand.GetGame;

import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.NotFound;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;

import akka.NotUsed;

public class ReversiServiceImpl implements ReversiService {

    private final PersistentEntityRegistry persistentEntities;
    
    @Inject
    public ReversiServiceImpl(PersistentEntityRegistry persistentEntities) {
      this.persistentEntities = persistentEntities;
      this.persistentEntities.register(ReversiEntity.class);
    }

    @Override
    public ServiceCall<NotUsed, NotUsed, NotUsed> createGame() {
        return (id, request) -> {
            String gameId = UUID.randomUUID().toString();
            return reversiEntityRef(gameId).ask(new CreateGame(gameId))
                    .thenApply(ack -> NotUsed.getInstance());
        };
    }
    
    @Override
    public ServiceCall<String, NotUsed, Game> getGame() {
      return (id, request) -> {
        return reversiEntityRef(id).ask(new GetGame()).thenApply(reply -> {
          if (reply.game.isPresent())
            return reply.game.get();
          else
            throw new NotFound("reversi game " + id + " not found");
        });
      };
    }

    private PersistentEntityRef<ReversiCommand> reversiEntityRef(String reversiId) {
        PersistentEntityRef<ReversiCommand> ref = persistentEntities.refFor(ReversiEntity.class, reversiId);
        return ref;
    }

}
