/*
 * Copyright (c) 2020 Honeywell International, Inc. All rights reserved.
 * This file contains trade secrets of Honeywell International, Inc.  No part
 * may be reproduced or transmitted in any form by any means or for any
 * purpose without the express written permission of Honeywell.
 */
package com.honeywell.reactive.api.service;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;
import org.neo4j.driver.reactive.RxResult;
import org.neo4j.driver.reactive.RxSession;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Neo4jService {

    private Driver driver;

    public Neo4jService() {
        this.driver = GraphDatabase.driver("bolt://localhost:11005",
                AuthTokens.basic("neo4j", "password"));
    }

    public Flux<String> streamNodesFromNeo4j() {
        String query = "MATCH (n) RETURN n";
        System.out.println("Querying for classes");
        return reactor.core.publisher.Flux.usingWhen( Mono.just( driver.rxSession(SessionConfig.forDatabase("hcb")) ),
                session -> session.readTransaction( tx -> {
                            RxResult result = tx.run( query );
                            return reactor.core.publisher.Flux.from( result.records() )
                                    .map( record -> record.get( 0 ).get("som.label:en", "No Label") );
                        }
                ), RxSession::close );
    }

    public Flux<String> streamNodeProperty() {
        try(Driver driver = GraphDatabase.driver("bolt://localhost:11005",
                AuthTokens.basic("neo4j", "password"))) {
            try(Session session = driver.session(SessionConfig.forDatabase("showcase"))) {
                Result result = session.run("MATCH (n) RETURN n LIMIT 25");
                List<Record> records = result.stream().collect(Collectors.toList());
                System.out.println("Count: "+records.size());
                return Flux.just(records)
                        .map( record -> record.get(0).toString() );
            }
        }
    }

    public static void main(String[] args) {

    }
}
