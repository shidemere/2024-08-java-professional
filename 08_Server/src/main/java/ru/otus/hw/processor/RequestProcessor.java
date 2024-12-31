package ru.otus.hw.processor;


import ru.otus.hw.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;

public interface RequestProcessor {
    void execute(HttpRequest httpRequest, OutputStream out) throws IOException;
}
