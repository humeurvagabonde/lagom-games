package org.hv.reversi.api;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.restCall;

import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

import akka.NotUsed;

public interface ReversiService extends Service {

    /**
     * Service call for creating a game of Reversi.
     */
    ServiceCall<NotUsed, NotUsed, NotUsed> createGame();
    
    @Override
    default Descriptor descriptor() {
        return named("reversiService").with(
            restCall(Method.POST, "/api/reversi", createGame())
        ).withAutoAcl(true);
    }
}
