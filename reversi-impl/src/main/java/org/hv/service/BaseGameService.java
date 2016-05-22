package org.hv.service;

import java.util.function.Function;

import org.hv.user.api.UserId;

import com.lightbend.lagom.javadsl.api.transport.Forbidden;
import com.lightbend.lagom.javadsl.server.HeaderServiceCall;
import com.lightbend.lagom.javadsl.server.ServerServiceCall;

public class BaseGameService {

	public <Id, Request, Response> ServerServiceCall<Id, Request, Response> authenticated(
			Function<UserId, ServerServiceCall<Id, Request, Response>> serviceCall) {
		// TODO le rendre asynchrone composeAsync et appeler UserServiceImpl
		return HeaderServiceCall.compose(requestHeader -> {
			String userId = requestHeader.principal().orElseGet(() -> {
				throw new Forbidden("User must be authenticated to access this service call");
			}).getName();
			return serviceCall.apply(UserId.of(userId));
		});
	}
}
