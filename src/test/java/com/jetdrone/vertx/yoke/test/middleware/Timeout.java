package com.jetdrone.vertx.yoke.test.middleware;

import com.jetdrone.vertx.yoke.middleware.YokeHttpServerRequest;
import com.jetdrone.vertx.yoke.test.Response;
import com.jetdrone.vertx.yoke.test.YokeTester;
import org.junit.Test;
import org.vertx.java.core.Handler;
import org.vertx.testtools.TestVerticle;

import static org.vertx.testtools.VertxAssert.*;

public class Timeout extends TestVerticle {

    @Test
    public void testTimeout() {
        YokeTester yoke = new YokeTester(vertx);
        yoke.use(new com.jetdrone.vertx.yoke.middleware.Timeout(10));
        yoke.use(new Handler<YokeHttpServerRequest>() {
            @Override
            public void handle(YokeHttpServerRequest request) {
                // noop to so the response would never end
            }
        });

        yoke.request("GET", "/", new Handler<Response>() {
            @Override
            public void handle(Response resp) {
                assertEquals(408, resp.getStatusCode());
                testComplete();
            }
        });
    }
}