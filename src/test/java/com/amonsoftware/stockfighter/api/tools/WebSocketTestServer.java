package com.amonsoftware.stockfighter.api.tools;

import org.glassfish.tyrus.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.DeploymentException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class WebSocketTestServer {

    private static final String HOST_NAME = "localhost";
    private static final int PORT = 8025;
    private static final String BASE_PATH = "/";
    private static final Logger log = LoggerFactory.getLogger(WebSocketTestServer.class);
    private Map<String, Object> serverProperties = new HashMap<>();
    private Server server;

    public void runTickerTapeTestServer() {
        runServer(TickerTapeTestEndpoint.class);
    }

    public void runExecutionsTestServer() {
        runServer(ExecutionsTestEndpoint.class);
    }

    public void stopServer() {
        server.stop();
    }

    private void runServer(Class<?> configuration) {
        server = new Server(HOST_NAME, PORT, BASE_PATH, serverProperties, configuration);

        try {
            server.start();
        } catch (DeploymentException e) {
            log.error("Cannot start websocket server", e);
        }
    }

    @ServerEndpoint("/MS44144592/venues/RWOMEX/tickertape")
    public static class TickerTapeTestEndpoint {
        @OnOpen
        public void onOpen(Session session, EndpointConfig config) throws Exception {
            Path path = Paths.get(WebSocketTestServer.class.getResource("/responses/tickertape.txt").toURI());
            Files.readAllLines(path)
                    .stream()
                    .forEach(s -> {
                        try {
                            session.getBasicRemote().sendText(s);
                        } catch (IOException e) {
                            log.error("Cannot deliver message", e);
                        }
                    });
        }
    }

    @ServerEndpoint("/executions")
    public static class ExecutionsTestEndpoint {
        @OnOpen
        public void onOpen(Session session, EndpointConfig config) throws IOException {
            session.getBasicRemote().sendText("");
        }
    }

}
