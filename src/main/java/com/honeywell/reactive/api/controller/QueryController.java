/*
 * Copyright (c) 2020 Honeywell International, Inc. All rights reserved.
 * This file contains trade secrets of Honeywell International, Inc.  No part
 * may be reproduced or transmitted in any form by any means or for any
 * purpose without the express written permission of Honeywell.
 */
package com.honeywell.reactive.api.controller;

import com.honeywell.reactive.api.service.Neo4jService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/nodes")
public class QueryController {
    private Neo4jService neo4jService;

    public QueryController(Neo4jService neo4jService) {
        this.neo4jService = neo4jService;
    }

    @GetMapping
    public Flux<String> query() {
        return neo4jService.streamNodesFromNeo4j();
    }
}
