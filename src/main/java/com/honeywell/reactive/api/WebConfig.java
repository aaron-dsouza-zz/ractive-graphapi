/*
 * Copyright (c) 2020 Honeywell International, Inc. All rights reserved.
 * This file contains trade secrets of Honeywell International, Inc.  No part
 * may be reproduced or transmitted in any form by any means or for any
 * purpose without the express written permission of Honeywell.
 */
package com.honeywell.reactive.api;

import com.honeywell.reactive.api.handler.QueryHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(QueryHandler queryHandler) {
        return route()
                .GET("/query", RequestPredicates.accept(MediaType.APPLICATION_JSON), queryHandler::hello)
                .build();
    }
}
