package org.hv.reversi.game.api;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;
import static com.lightbend.lagom.javadsl.api.Service.restCall;

import org.hv.auth.JwtServiceIdentificationStrategy;

import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

import akka.NotUsed;
import akka.stream.javadsl.Source;

public interface ReversiGameService extends Service {

    /**
     * Service call for creating a game of Reversi.
     */
    ServiceCall<NotUsed, GameId> createGame();
    
    /**
     * Service call for getting a game.
     * The ID of this service call is the game Id, and the response message is the Game object.
     */
    ServiceCall<NotUsed, Game> getGame(String id);
    
    ServiceCall<Source<PlayDiscRequest, ?>, Source<GameEvent, ?>> playDisc(String id);
    
    @Override
    default Descriptor descriptor() {
        return named("reversiGameService").with(
            restCall(Method.POST, "/api/reversi/games/", this::createGame),
            restCall(Method.GET,  "/api/reversi/games/:id", this::getGame),
            pathCall("/api/reversi/games/:id/play-disc", this::playDisc)
        )
		.withAutoAcl(true)
		.withServiceIdentificationStrategy(new JwtServiceIdentificationStrategy());
    }
}
