package ru.otus.hw;

public class ServerApplication {
    public static void main(String[] args) {
        Configuration config = new Configuration();
        new Server(config).start();
    }
}
