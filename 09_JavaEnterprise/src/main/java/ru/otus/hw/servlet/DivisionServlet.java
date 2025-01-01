package ru.otus.hw.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "DivisionServlet", urlPatterns = "/div")
public class DivisionServlet extends HttpServlet {

    // http://localhost:8080/09_JavaEnterprise/calc?first=100&second=10
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        double first = Double.parseDouble(req.getParameter("first"));
        double second = Double.parseDouble(req.getParameter("second"));
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
                        <h1>%2f</h1>
                  </body>
                </html>
                """, first / second);
        writer.println(out);
        writer.close();
    }
}
