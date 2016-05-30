package org.hv.reversi.game.api;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.restCall;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;

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
    ServiceCall<NotUsed, NotUsed, GameId> createGame();
    
    /**
     * Service call for getting a game.
     * The ID of this service call is the game Id, and the response message is the Game object.
     */
    ServiceCall<String, NotUsed, Game> getGame();
    
    ServiceCall<String, Source<PlayDiscRequest, NotUsed>, Source<Game, NotUsed>> playDisc();
    
    @Override
    default Descriptor descriptor() {
        return named("reversiGameService").with(
            restCall(Method.POST, "/api/reversi/games/", createGame()),
            restCall(Method.GET,  "/api/reversi/games/:id", getGame()),
            pathCall("/api/reversi/games/:id/play-disc", playDisc())
        )
		.withAutoAcl(true)
		.withServiceIdentificationStrategy(new JwtServiceIdentificationStrategy());
    }
}
