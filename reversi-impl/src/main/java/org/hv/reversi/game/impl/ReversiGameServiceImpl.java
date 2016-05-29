package org.hv.reversi.game.impl;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import org.hv.reversi.game.api.Game;
import org.hv.reversi.game.api.GameId;
import org.hv.reversi.game.api.PlayDiscRequest;
import org.hv.reversi.game.api.ReversiGameService;
import org.hv.service.BaseGameService;

import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.NotFound;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;

import akka.NotUsed;
import akka.stream.javadsl.Source;
import sample.chirper.chirp.api.Chirp;

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
    
    @Override
	public ServiceCall<String, Source<PlayDiscRequest, ?>, Source<Game, ?>> playDisc() {
    	return (id, request) -> getGame().invoke(id, NotUsed.getInstance()).thenCompose(game -> {
    		// solution 1
//    		CompletionStage<Source<Game, ?>> result = chirpService.getLiveChirps().invoke(chirpsReq);
//            return result;
    		
    		// solution 2 : utilisez un pubsub et puis pubsb.consuer qui retourne un source :)
    		//CompletableFuture.completedFuture(value)
    		return null;
    	});
	}

	private PersistentEntityRef<ReversiGameCommand> reversiEntityRef(String reversiGameId) {
        PersistentEntityRef<ReversiGameCommand> ref = persistentEntities.refFor(ReversiGameEntity.class, reversiGameId);
        return ref;
    }

}
