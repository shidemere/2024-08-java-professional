package ru.otus.hw;


import ru.otus.hw.processor.InternalServerErrorRequestProcessor;
import ru.otus.hw.processor.RequestProcessor;
import ru.otus.hw.processor.RootRequestProcessor;
import ru.otus.hw.processor.ShutdownRequestProcessor;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Обработка входящего запроса, уже преобразованного из потока байт в объект, для возвращения ответа.
 */
public class Dispatcher {

    private final RequestProcessor rootRequestProcessor;
    private final RequestProcessor internalServerRequestProcessor;
    private final RequestProcessor shutdownRequestProcessor;

    public Dispatcher(Server server) {
        this.rootRequestProcessor = new RootRequestProcessor();
        this.internalServerRequestProcessor = new InternalServerErrorRequestProcessor();
        this.shutdownRequestProcessor = new ShutdownRequestProcessor(server);
    }

    public void execute(HttpRequest httpRequest, OutputStream out) throws IOException {
        try {
            if (httpRequest.getMethod() == (HttpMethod.GET) && httpRequest.getEndpoint().equalsIgnoreCase("/shutdown")) {
                shutdownRequestProcessor.execute(httpRequest, out);
                return;
            }
            rootRequestProcessor.execute(httpRequest, out);
        } catch (Exception e) {
            internalServerRequestProcessor.execute(httpRequest, out);
        }
    }
}
