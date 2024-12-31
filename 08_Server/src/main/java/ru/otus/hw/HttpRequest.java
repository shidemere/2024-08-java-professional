package ru.otus.hw;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw.exception.HttpFormatException;
import ru.otus.hw.exception.RequestSizeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Кастомный объект запроса.
 * Входящий байтовый поток формируется в объект.
 */
public class HttpRequest {
    private static Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private String host;
    private String endpoint;
    private HttpMethod method;
    private StringBuffer body;
    private Map<String, String> parameters;
    private Map<String, String> headers;

    private int requestContentLength = 0;
    private int maxContentLength;

    public HttpRequest(InputStream request, int maxContentLength) {
        try {
            this.maxContentLength = maxContentLength;
            parse(request);
        } catch (IOException e) {
            logger.error("IOException in Building HttpRequest: {}", e.getMessage());
        }
    }


    private void parse(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        // Узнаем метод запроса и URL.
        String firstLine = readLine(reader);
        while (true) {
            if (firstLine != null) {
                setMethod(firstLine);
                break;
            }
            firstLine = readLine(reader);
        }

        // Собираем заголовки запроса
        this.headers = new HashMap<>();
        String header = readLine(reader);
        while (!header.isEmpty()) {
            if (header.split(": ")[0].equals("Host")) {
                setHost(header);
            }
            addHeaderParameter(header);
            header = readLine(reader);
        }

        // Если запрос имеет тело - читаем его.
        if (method != HttpMethod.GET && method != HttpMethod.HEAD) {
            this.body = new StringBuffer();
            String bodyLine = readLine(reader);
            while (bodyLine != null) {
                appendMessageBody(bodyLine);
                bodyLine = readLine(reader);
            }
        }

    }

    /**
     * Чтение запроса построчно с проверкой превышения максимального размера.
     * @param reader входной поток данных.
     * @return прочитанная строка
     * @throws IOException выбрасывается в случае превышения максимально допустимого размера контента.
     */
    private String readLine(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        if (line != null) {
            requestContentLength += line.getBytes().length;
            if (requestContentLength > maxContentLength) {
                throw new RequestSizeException("Request size > " + maxContentLength);
            }
            return line;
        }
        return null;
    }

    private void setMethod(String requestLine) {
        String[] parts = requestLine.split(" ");
        this.method = HttpMethod.valueOf(parts[0]);
        /*
            Проверка на то, имеется ли в эндпоинте параметры.
            Например URI "example.com/page?param1=value1&param2=value2"
            будет разделен на ["example.com/page", "param1=value1&param2=value2"]
         */
        if (parts[1].contains("?")) {
            setParams(parts[1]);
        } else {
            this.endpoint = parts[1];
        }
    }

    // Парсинг параметров, переданных через адрессную строку.
    private void setParams(String paramsLine) {
        this.parameters = new HashMap<>();
        String[] uriParts = paramsLine.split("[?]");
        setEndpoint(uriParts[0]);
        String rawParams = uriParts[1];
        String[] differentParams = rawParams.split("&");
        for (String param : differentParams) {
            int index = param.indexOf("=");
            this.parameters.put(param.substring(0, index), param.substring(index + 1));
        }
    }

    private void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    private void setHost(String requestLine) {
        this.host= requestLine.substring(requestLine.indexOf(" "));
    }

    private void addHeaderParameter(String header) {
        int index = header.indexOf(":");
        if (index == -1) {
            throw new HttpFormatException("Invalid Header Parameter: " + header);
        }
        headers.put(header.substring(0, index), header.substring(index + 1).trim());
    }

    private void appendMessageBody(String bodyLine) {
        body.append(bodyLine).append("\r\n");
    }

    public String getHost() {
        return host;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getEndpoint() {
        return endpoint;
    }
}
