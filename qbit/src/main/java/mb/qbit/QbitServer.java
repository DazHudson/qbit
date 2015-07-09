package mb.qbit;

import io.advantageous.qbit.http.server.HttpServer;
import static io.advantageous.qbit.http.server.HttpServerBuilder.httpServerBuilder;
import io.advantageous.qbit.http.websocket.WebSocket;
import java.util.function.Consumer;

/**
 * Start a HTTP Micro Service
 *
 * @author dhudson
 */
public class QbitServer {

    public QbitServer() {
        /* Create an HTTP server. */
        HttpServer httpServer = httpServerBuilder()
                .setPort(8080).build();

        /* Setup WebSocket Server support. */
        QbitWebSocketConsumer consumer = new QbitWebSocketConsumer();
        httpServer.setWebSocketOnOpenConsumer(consumer);

        /* Start the server. */
        httpServer.start();
    }

    private class QbitWebSocketConsumer implements Consumer<WebSocket> {

        @Override
        public void accept(WebSocket webSocket) {
            // Set up the message consumer
            QbitServerMessageConsumer messageConsumer = new QbitServerMessageConsumer(webSocket);
            webSocket.setTextMessageConsumer(messageConsumer);
        }
    }

    private class QbitServerMessageConsumer implements Consumer<String> {

        private final WebSocket theWebSocket;

        QbitServerMessageConsumer(WebSocket webSocket) {
            theWebSocket = webSocket;
        }

        @Override
        public void accept(String t) {
            // Send the message back
            theWebSocket.sendText(t);
        }
    }

    // Bootstrap
    public static void main(String[] args) {
        new QbitServer();
    }
}
