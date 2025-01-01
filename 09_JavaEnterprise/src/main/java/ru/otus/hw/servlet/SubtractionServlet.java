package ru.otus.hw.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "SubtractionServlet", urlPatterns = "/subtract")
public class SubtractionServlet extends HttpServlet {

    // http://localhost:8080/09_JavaEnterprise/calc?first=100&second=100
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int first = Integer.parseInt(req.getParameter("first"));
        int second = Integer.parseInt(req.getParameter("second"));
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        String out = String.format("""
                <!DOCTYPE html>
                <html lang="en">
                  <head>
                    <meta charset="UTF-8">
                    <title>Result</title>
                  </head>
                  <body>
                        <h1>%d</h1>
                  </body>
                </html>
                """, first - second);
        writer.println(out);
        writer.close();
    }
}
