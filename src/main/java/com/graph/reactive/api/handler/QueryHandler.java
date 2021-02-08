/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.graph.reactive.api.handler;

import com.graph.reactive.api.model.Node;
import com.graph.reactive.api.service.Neo4jService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.time.Duration;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

@Component
public class QueryHandler {
    private Neo4jService neo4jService;

    public QueryHandler(Neo4jService neo4jService) {
        this.neo4jService = neo4jService;
    }

    public Mono<ServerResponse> query(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(neo4jService.streamNodesFromNeo4j(), Node.class);
    }

    private Flux<Node> streamNodes() {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
        Flux<Node> nodes = Flux.fromStream(Stream.generate(()->new Node(UUID.randomUUID().toString(),
                generateRandomString())));
        return Flux.zip(nodes, interval, (key, value) -> key);
    }

    public Mono<ServerResponse> hello(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue("Hello, Spring!"));
    }

    private String generateRandomString() {
        byte[] array = new byte[7];
        new Random().nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }
}
