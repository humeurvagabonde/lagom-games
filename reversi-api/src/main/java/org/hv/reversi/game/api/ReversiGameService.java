package org.hv.reversi.game.api;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.restCall;

import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

import akka.NotUsed;

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
    
    @Override
    default Descriptor descriptor() {
        return named("reversiGameService").with(
            restCall(Method.POST, "/api/reversi/games/", createGame()),
            restCall(Method.GET,  "/api/reversi/games/:id", getGame())
        ).withAutoAcl(true);
    }
}