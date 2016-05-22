/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.hv.reversi.game.impl;

import org.hv.reversi.game.api.ReversiGameService;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

public class ReversiGameModule extends AbstractModule implements ServiceGuiceSupport {
    
    @Override
    protected void configure() {
        bindServices(serviceBinding(ReversiGameService.class, ReversiGameServiceImpl.class));
    }
    
}
