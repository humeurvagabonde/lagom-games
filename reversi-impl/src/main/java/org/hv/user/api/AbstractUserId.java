package org.hv.user.api;

import org.immutables.value.Value;

import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;

@Value.Immutable
@ImmutableStyle
public interface AbstractUserId {

	@Value.Parameter
	String id();
}
