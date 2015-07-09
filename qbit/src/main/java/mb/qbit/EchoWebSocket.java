package mb.qbit;

import io.advantageous.boon.core.Sys;
import io.advantageous.qbit.http.client.HttpClient;
import static io.advantageous.qbit.http.client.HttpClientBuilder.httpClientBuilder;
import io.advantageous.qbit.http.server.HttpServer;
import static io.advantageous.qbit.http.server.HttpServerBuilder.httpServerBuilder;
import io.advantageous.qbit.http.websocket.WebSocket;

/**
 * Take from Rick Hightowers example.
 *
 * @author rhightower
 */
public class EchoWebSocket {

    public static void main(String... args) {


        /* Create an HTTP server. */
        HttpServer httpServer = httpServerBuilder()
                .setPort(8080).build();

        /* Setup WebSocket Server support. */
        httpServer.setWebSocketOnOpenConsumer(webSocket -> webSocket.setTextMessageConsumer(message -> {
            webSocket.sendText("ECHO " + message);
        }));

        /* Start the server. */
        httpServer.start();

        /**
         * CLIENT.
         */

        /* Setup an httpClient. */
        HttpClient httpClient = httpClientBuilder()
                .setHost("localhost").setPort(8080).build();
        httpClient.startClient();

        /* Setup the client websocket. */
        WebSocket webSocket = httpClient
                .createWebSocket("/websocket/rocket");

        webSocket.setTextMessageConsumer(System.out::println);
        webSocket.openAndWait();

        /* Send some messages. */
        webSocket.sendText("Hi mom");
        webSocket.sendText("Hello World!");

        Sys.sleep(1000);
        webSocket.close();
        httpClient.stop();
        httpServer.stop();
    }
}
