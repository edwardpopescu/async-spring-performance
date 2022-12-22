package com.inghubs.asyncspringperformance.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.gatling.http.HeaderNames.*;

public class WiremockServer {

    public static void main(String[] args) {
        WireMockServer wireMockServer = new WireMockServer(options()
                .disableRequestJournal()
                .asynchronousResponseEnabled(true)
                .asynchronousResponseThreads(20)
                .containerThreads(20)
                .jettyAcceptors(4)
                .port(8080));
        wireMockServer.start();
        configureFor("localhost", 8080);
        stubFor(get(urlEqualTo("/hello/wiremock"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withFixedDelay(50)
                        .withHeader(ContentType().toString(), "application/json")
                        .withBody("{\"name\":\"Spring user\"}")));
    }

}
