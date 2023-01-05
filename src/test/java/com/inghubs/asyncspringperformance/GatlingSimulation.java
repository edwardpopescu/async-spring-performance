package com.inghubs.asyncspringperformance;


import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;


import static io.gatling.javaapi.core.CoreDsl.rampUsersPerSec;
import static io.gatling.javaapi.http.HttpDsl.status;

public class GatlingSimulation extends Simulation {

    private static final int USERS_PER_SEC = 150;
    private static final int DURATION = 600;

    HttpProtocolBuilder httpSetupJersey = HttpDsl.http
            .baseUrl("http://localhost:8081")
            .acceptHeader("application/json")
            .userAgentHeader("Gatling");

    HttpProtocolBuilder httpSetupMvc = HttpDsl.http
            .baseUrl("http://localhost:8082")
            .acceptHeader("application/json")
            .userAgentHeader("Gatling");

    HttpProtocolBuilder httpSetupWebflux = HttpDsl.http
            .baseUrl("http://localhost:8083")
            .acceptHeader("application/json")
            .userAgentHeader("Gatling");

    ScenarioBuilder scnJersey = CoreDsl.scenario("Load Test Creating Customers")
            .exec(HttpDsl.http("jersey")
                    .get("/hello/jersey")
                    .check(status().is(200))
            );

    ScenarioBuilder scnJerseyAsync = CoreDsl.scenario("Load Test Creating Customers")
            .exec(HttpDsl.http("jersey-async")
                    .get("/hello/jersey/async")
                    .check(status().is(200))
            );

    ScenarioBuilder scnMvc = CoreDsl.scenario("Load Test Creating Customers")
            .exec(HttpDsl.http("mvc")
                    .get("/hello/mvc/")
                    .check(status().is(200))
            );

    ScenarioBuilder scnMvcAsync = CoreDsl.scenario("Load Test Creating Customers")
            .exec(HttpDsl.http("mvc-async")
                    .get("/hello/mvc/async")
                    .check(status().is(200))
            );

    ScenarioBuilder scnWebFlux = CoreDsl.scenario("Load Test Creating Customers")
            .exec(HttpDsl.http("webflux")
                    .get("/hello/webflux")
                    .check(status().is(200))
            );

    public GatlingSimulation() {
        this.setUp(scnJersey.injectOpen(rampUsersPerSec(USERS_PER_SEC/10.0).to(USERS_PER_SEC).during(DURATION)))
                .protocols(httpSetupJersey);
    }
//    public GatlingSimulation() {
//        this.setUp(scnJerseyAsync.injectOpen(rampUsersPerSec(USERS_PER_SEC/10.0).to(USERS_PER_SEC).during(DURATION)))
//                .protocols(httpSetupJersey);
//    }
//    public GatlingSimulation() {
//        this.setUp(scnMvc.injectOpen(rampUsersPerSec(USERS_PER_SEC/10.0).to(USERS_PER_SEC).during(DURATION)))
//                .protocols(httpSetupMvc);
//    }
//    public GatlingSimulation() {
//        this.setUp(scnMvcAsync.injectOpen(rampUsersPerSec(USERS_PER_SEC/10.0).to(USERS_PER_SEC).during(DURATION)))
//                .protocols(httpSetupMvc);
//    }
//    public GatlingSimulation() {
//        this.setUp(scnWebFlux.injectOpen(rampUsersPerSec(USERS_PER_SEC/10.0).to(USERS_PER_SEC).during(DURATION)))
//                .protocols(httpSetupWebflux);
//    }
}