package java.org.hv.reversi.impl;

import javax.inject.Inject;

import org.hv.reversi.api.ReversiService;

import com.lightbend.lagom.javadsl.api.ServiceCall;
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
            return reversiEntityRef(request.userId).ask(new CreateUser(request))
                    .thenApply(ack -> NotUsed.getInstance());
              };
        }
    }

    private PersistentEntityRef<ReversiCommand> reversiEntityRef(String reversiId) {
        PersistentEntityRef<ReversiCommand> ref = persistentEntities.refFor(ReversiEntity.class, reversiId);
        return ref;
    }

}
