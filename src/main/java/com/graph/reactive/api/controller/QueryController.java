/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.graph.reactive.api.controller;

import com.graph.reactive.api.model.Node;
import com.graph.reactive.api.service.Neo4jService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/nodes")
public class QueryController {
    private Neo4jService neo4jService;

    public QueryController(Neo4jService neo4jService) {
        this.neo4jService = neo4jService;
    }

    @GetMapping
    public Flux<Node> query() {
        return neo4jService.streamNodesFromNeo4j();
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @CrossOrigin(origins = "http://localhost:3000")
    public Flux<Node> streamNodes() {
        return neo4jService.streamNodesFromNeo4j().filter(node-> Long.parseLong(node.getId()) > 100 && Long.parseLong(node.getId()) < 140);
    }
}
