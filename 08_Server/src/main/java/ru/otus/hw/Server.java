package ru.otus.hw;


import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Поднятие сервера с указанными конфигурациями.
 */
public class Server {

    private final int port;
    private final ExecutorService connectionsPool;
    private final int maxRequestSize;
    private final Dispatcher dispatcher;

    private boolean isActive;

    // Настройка
    public Server(Configuration config) {
        this.port = Integer.parseInt(config.getProperty("port"));
        this.connectionsPool = Executors.newFixedThreadPool(Integer.parseInt(config.getProperty("maxThreadsCount")));
        this.maxRequestSize = Integer.parseInt(config.getProperty("maxRequestSize"));
        this.dispatcher = new Dispatcher(this);
        this.isActive = true;
    }

    // Запуск
    public void start() {
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            while (isActive) {
                // Ожидаем подключения
                Socket connection = serverSocket.accept();
                new RequestHandler(connectionsPool, connection, dispatcher, maxRequestSize);
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port);
        } finally {
            connectionsPool.shutdown();
        }
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }
}