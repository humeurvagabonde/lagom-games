package org.hv.reversi.game.impl;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import org.hv.reversi.game.api.Game;
import org.hv.reversi.game.api.GameEvent;
import org.hv.reversi.game.api.GameId;
import org.hv.reversi.game.api.PlayDiscRequest;
import org.hv.reversi.game.api.Pos;
import org.hv.reversi.game.api.ReversiGameService;
import org.hv.service.BaseGameService;
import org.hv.user.api.UserId;

import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.NotFound;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;

import akka.NotUsed;
import akka.japi.pf.PFBuilder;
import akka.stream.javadsl.Source;
import scala.PartialFunction;

public class ReversiGameServiceImpl extends BaseGameService implements ReversiGameService {

    private final PersistentEntityRegistry persistentEntityRegistry;
    
    @Inject
    public ReversiGameServiceImpl(PersistentEntityRegistry persistentEntityRegistry) {
      this.persistentEntityRegistry = persistentEntityRegistry;
      this.persistentEntityRegistry.register(ReversiGameEntity.class);
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
    
    @Override
 	public ServiceCall<String, Source<PlayDiscRequest, ?>, Source<GameEvent, ?>> playDisc() {
 	  	return (id, request) -> {
 	  		UserId userId = UserId.of("1");
 	  		GameId gameId = GameId.of(id);
 	  		reversiEntityRef(gameId.id()).ask(PlayDisc.of(userId, gameId, Pos.builder().x(0).y(0).build()));

            Source<GameEvent, ?> stream = persistentEntityRegistry
                    .eventStream(ReversiGameEventTag.INSTANCE, Optional.empty())
                    .map(pair -> pair.first()).collect(collectFunction());
            return CompletableFuture.completedFuture(stream);
   		};
 	}
    
    private PartialFunction<ReversiGameEvent, GameEvent> collectFunction() {
            return new PFBuilder<ReversiGameEvent, GameEvent>()
                .match(ReversiGameEvent.class, evt ->
                    GameEvent.builder().build())
                .build();
    }

    private PersistentEntityRef<ReversiGameCommand> reversiEntityRef(String reversiGameId) {
        PersistentEntityRef<ReversiGameCommand> ref = persistentEntityRegistry.refFor(ReversiGameEntity.class, reversiGameId);
        return ref;
    }

}
