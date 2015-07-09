package mb.qbit;

import io.advantageous.boon.core.Sys;
import io.advantageous.qbit.http.client.HttpClient;
import static io.advantageous.qbit.http.client.HttpClientBuilder.httpClientBuilder;
import io.advantageous.qbit.http.websocket.WebSocket;
import java.util.function.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Qbit Client.
 *
 * @author dhudson
 */
public class QbitClient {

    // Aysnc Logger
    private static final Logger theLogger = LogManager.getLogger(QbitClient.class);

    public QbitClient() {

        theLogger.debug("Creating client");

        HttpClient httpClient = httpClientBuilder()
                .setHost("localhost").setPort(8080).build();
        httpClient.startClient();

        /* Setup the client websocket. */
        WebSocket webSocket = httpClient.createWebSocket("/websocket/rocket");

        TextClientConsumer consumer = new TextClientConsumer();

        webSocket.setTextMessageConsumer(consumer);
        webSocket.openAndWait();

        theLogger.debug("Wait a bit");
        Sys.sleep(1000);

        theLogger.info("Sending ...");
        // Send messages
        for (int i = 0; i < 100; i++) {
            webSocket.sendText(Long.toString(System.currentTimeMillis()));
        }

    }

    private class TextClientConsumer implements Consumer<String> {
        @Override
        public void accept(String t) {
            // Lets get the time back ..
            theLogger.info("Latency round trip .. {}", (System.currentTimeMillis() - Long.parseLong(t)));
        }
    }

    // Bootstrap
    public static void main(String[] args) {
        new QbitClient();
    }
}
