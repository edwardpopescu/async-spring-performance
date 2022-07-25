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



    ScenarioBuilder scn = CoreDsl.scenario("Load Test Creating Customers")
//            .feed(FeederBuilder.)
            .exec(HttpDsl.http("create-customer-request")
                    .get("/hello/jersey/async")
                    .check(status().is(200))
            );

    public GatlingSimulation() {
        this.setUp(scn.injectOpen(constantUsersPerSec(1000).during(Duration.ofSeconds(30))))
                .protocols(httpProtocol);
    }
}