/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.hv.reversi.impl;

import org.hv.reversi.api.ReversiService;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

public class ReversiModule extends AbstractModule implements ServiceGuiceSupport {
    
    @Override
    protected void configure() {
        bindServices(serviceBinding(ReversiService.class, ReversiServiceImpl.class));
    }
    
}
