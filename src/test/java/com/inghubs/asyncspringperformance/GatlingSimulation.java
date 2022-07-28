package com.inghubs.asyncspringperformance;


import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.http.HttpDsl.status;

public class GatlingSimulation extends Simulation {
    HttpProtocolBuilder httpProtocol = HttpDsl.http
            .baseUrl("http://localhost:8080")
            .acceptHeader("application/json")
            .userAgentHeader("Gatling/Performance Test");



    ScenarioBuilder scnJersey = CoreDsl.scenario("Load Test Creating Customers")
            .exec(HttpDsl.http("create-customer-request")
                    .get("/hello/jersey")
                    .check(status().is(200))
            );

    ScenarioBuilder scnJerseyAsync = CoreDsl.scenario("Load Test Creating Customers")
            .exec(HttpDsl.http("create-customer-request")
                    .get("/hello/jersey/async")
                    .check(status().is(200))
            );

    ScenarioBuilder scnMvc = CoreDsl.scenario("Load Test Creating Customers")
            .exec(HttpDsl.http("create-customer-request")
                    .get("/hello/mvc")
                    .check(status().is(200))
            );

    ScenarioBuilder scnMvcAsync = CoreDsl.scenario("Load Test Creating Customers")
            .exec(HttpDsl.http("create-customer-request")
                    .get("/hello/mvc/async")
                    .check(status().is(200))
            );

    public GatlingSimulation() {
        this.setUp(scnMvcAsync.injectOpen(constantUsersPerSec(100).during(Duration.ofSeconds(30))))
                .protocols(httpProtocol);
    }
}