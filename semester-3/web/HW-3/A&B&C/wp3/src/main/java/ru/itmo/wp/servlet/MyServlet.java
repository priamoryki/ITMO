package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * @author Pavel Lymar
 */
public class MyServlet extends HttpServlet {
    private final ArrayList<UserData> messages = new ArrayList<>();

    public static class UserData {
        public Object user;
        public String text;

        UserData() {
        }

        UserData(Object user, String text) {
            this.user = user;
            this.text = text;
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        HttpSession session = request.getSession();

        Object result = null;
        UserData userData = new UserData();

        switch (uri) {
            case "/message/auth" -> {
                userData.user = request.getParameter("user");
                if (userData.user != null && !((String) userData.user).isBlank()) {
                    session.setAttribute("user", userData.user);
                    result = userData.user;
                } else {
                    result = session.getAttribute("user") != null ? session.getAttribute("user") : "";
                }
            }

            case "/message/findAll" -> {
                if (session.getAttribute("user") != null) {
                    result = messages;
                }
            }

            case "/message/add" -> {
                userData = new UserData(session.getAttribute("user"), request.getParameter("text"));
                if (userData.user != null && userData.text != null && !userData.text.isBlank()) {
                    messages.add(userData);
                }
            }

            default -> response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }

        response.setContentType("application/json");
        OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8);
        writer.write(new Gson().toJson(result));
        writer.flush();
    }
}
