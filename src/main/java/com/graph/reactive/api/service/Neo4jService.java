/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.graph.reactive.api.service;

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

import com.graph.reactive.api.model.Node;

@Service
public class Neo4jService {

    private Driver driver;

    public Neo4jService() {
        this.driver = GraphDatabase.driver("bolt://localhost:11005",
                AuthTokens.basic("neo4j", "password"));
    }

    public Flux<Node> streamNodesFromNeo4j() {
        String query = "MATCH (n) RETURN n";
        System.out.println("Querying for classes");
        return reactor.core.publisher.Flux.usingWhen( Mono.just( driver.rxSession(SessionConfig.forDatabase("hcb")) ),
                session -> session.readTransaction( tx -> {
                            RxResult result = tx.run( query );
                            return reactor.core.publisher.Flux.from( result.records() )
                                    .map( record ->
                                    new Node(Long.toString(record.get( 0 ).asNode().id()),
                                    record.get( 0 ).get("som.label:en", "No Label")) );
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
}
