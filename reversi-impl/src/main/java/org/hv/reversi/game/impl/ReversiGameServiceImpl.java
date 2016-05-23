package org.hv.reversi.game.impl;

import java.util.UUID;

import javax.inject.Inject;

import org.hv.reversi.game.api.Game;
import org.hv.reversi.game.api.GameId;
import org.hv.reversi.game.api.ReversiGameService;
import org.hv.service.BaseGameService;

import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.NotFound;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;

import akka.NotUsed;

public class ReversiGameServiceImpl extends BaseGameService implements ReversiGameService {

    private final PersistentEntityRegistry persistentEntities;
    
    @Inject
    public ReversiGameServiceImpl(PersistentEntityRegistry persistentEntities) {
      this.persistentEntities = persistentEntities;
      this.persistentEntities.register(ReversiGameEntity.class);
    }

    @Override
    public ServiceCall<NotUsed, NotUsed, GameId> createGame() {
        return authenticated(userId -> (id, request) -> {
        	GameId gameId = GameId.of(UUID.randomUUID().toString());
            return reversiEntityRef(gameId.toString()).ask(CreateGame.of(gameId)).thenApply(reply -> reply.gameId());
        });
    }
    
    @Override
    public ServiceCall<String, NotUsed, Game> getGame() {
      return (id, request) -> {
        return reversiEntityRef(id).ask(GetGame.of()).thenApply(reply -> {
          if (reply.game().isPresent())
            return reply.game().get();
          else
            throw new NotFound("reversi game " + id + " not found");
        });
      };
    }

    private PersistentEntityRef<ReversiGameCommand> reversiEntityRef(String reversiGameId) {
        PersistentEntityRef<ReversiGameCommand> ref = persistentEntities.refFor(ReversiGameEntity.class, reversiGameId);
        return ref;
    }

}
