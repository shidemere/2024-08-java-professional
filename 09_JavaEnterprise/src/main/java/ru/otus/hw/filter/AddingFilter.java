package ru.otus.hw.filter;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw.servlet.AddingServlet;

import java.io.IOException;

@WebFilter(value = "/add")
public class AddingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AddingFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        String firstValue = servletRequest.getParameter("first");
        String secondValue = servletRequest.getParameter("second");
        logger.info("Register request in {} with values [{}, {}]", AddingServlet.class.getSimpleName(), firstValue, secondValue);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}