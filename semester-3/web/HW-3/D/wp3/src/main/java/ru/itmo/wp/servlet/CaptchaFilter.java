package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Base64;
import java.util.Random;

/**
 * @author Pavel Lymar
 */
public class CaptchaFilter extends HttpFilter {
    private final static Random rand = new Random();

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (request.getMethod().equals("GET")) {
            Integer expected = (Integer) session.getAttribute("expected");
            String number = request.getParameter("number");

            if (session.getAttribute("isCaptchaPassed") != null) {
                chain.doFilter(request, response);
            } else {
                if (expected != null && number != null && number.equals(expected.toString())) {
                    session.setAttribute("isCaptchaPassed", true);
                    chain.doFilter(request, response);
                } else {
                    session.setAttribute("expected", doCaptchaResponse(request, response));
                }
            }
        }
    }

    public int doCaptchaResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int expected = rand.nextInt(900) + 100;
        response.setContentType("text/html");
        String html =
                "<img src=\"data:image/png;base64, " +
                        Base64.getEncoder().encodeToString(ImageUtils.toPng(Integer.toString(expected))) +
                        "\">\n" +
                        "<form action=\"" + request.getRequestURI() + "\" method=\"GET\">\n" +
                        "<input type=\"text\" name=\"number\">\n" +
                        "</form>";
        response.getWriter().write(html);
        response.getWriter().flush();
        return expected;
    }
}
