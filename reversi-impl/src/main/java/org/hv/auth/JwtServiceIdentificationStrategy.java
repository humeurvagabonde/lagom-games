package org.hv.auth;

import java.security.Principal;
import java.util.Optional;

import com.lightbend.lagom.javadsl.api.security.ServicePrincipal;
import com.lightbend.lagom.javadsl.api.transport.HeaderTransformer;
import com.lightbend.lagom.javadsl.api.transport.RequestHeader;
import com.lightbend.lagom.javadsl.api.transport.ResponseHeader;

import play.mvc.Http;

public class JwtServiceIdentificationStrategy implements HeaderTransformer {

	@Override
	public RequestHeader transformClientRequest(RequestHeader request) {
		if (request.principal().isPresent()) {
			Principal principal = request.principal().get();
			if (principal instanceof ServicePrincipal) {
				String serviceName = ((ServicePrincipal) principal).serviceName();
				return request.withHeader(Http.HeaderNames.AUTHORIZATION, serviceName);
			} else {
				return request;
			}
		} else {
			return request;
		}
	}

	@Override
	public RequestHeader transformServerRequest(RequestHeader request) {
		Optional<String> auth = request.getHeader(Http.HeaderNames.AUTHORIZATION);
		if (auth.isPresent()) {
			return request.withPrincipal(ServicePrincipal.forServiceNamed(auth.get()));
		} else {
			return request;
		}
	}

	@Override
	public ResponseHeader transformServerResponse(ResponseHeader response, RequestHeader request) {
		return response;
	}

	@Override
	public ResponseHeader transformClientResponse(ResponseHeader response, RequestHeader request) {
		return response;
	}
}
