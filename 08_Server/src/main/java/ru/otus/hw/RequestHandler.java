package ru.otus.hw;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * Обработка входящего запроса байтов в многопоточном режиме.
 * При помощи соединения формируется входящий и исходящий поток данных.
 * Уже из него можно парсить запрос и создавать ответ.
 */
public class RequestHandler {

    private final Socket connection;
    private final OutputStream out;
    private final InputStream in;

    public RequestHandler(ExecutorService pool, Socket connection, Dispatcher dispatcher, int maxRequestSize) throws IOException {
        this.connection = connection;
        this.out = connection.getOutputStream();
        this.in = connection.getInputStream();

        // Запуск обработки запросов в паралельном режиме.
        pool.execute(()-> {
            try {
                // Парсинг входящего потока в специальные объекты.
                HttpRequest httpRequest = new HttpRequest(in, maxRequestSize);
                // Отдаём ответ клиенту
                dispatcher.execute(httpRequest, out);
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            } finally {
                disconnect();
            }
        });
    }

    private void disconnect() {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                System.err.println("Error closing input stream");
            }
        }

        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                System.err.println("Error closing output stream");
            }
        }

        try {
            connection.close();
        } catch (IOException e) {
            System.err.println("Error closing connection");
        }
    }
}