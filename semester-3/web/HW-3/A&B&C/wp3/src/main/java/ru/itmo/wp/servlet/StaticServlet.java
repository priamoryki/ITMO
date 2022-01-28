package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class StaticServlet extends HttpServlet {
//    private static final String staticDirectory = "D:/Важное/Учеба/Семестр 3/СП-Web/ДЗ-3/A&B&C/wp3/src/main/webapp/static/";
    private static final String staticDirectory = System.getenv("staticDirectory");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        String[] uris = uri.split("\\+");
        File[] files = new File[uris.length];

        for (int i = 0; i < uris.length; i++) {
            File file = new File(staticDirectory, uris[i]);
            if (!file.isFile()) {
                file = new File(getServletContext().getRealPath("/static/" + uris[i]));
            }
            if (file.isFile()) {
                files[i] = file;
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        }

        for (int i = 0; i < files.length; i++) {
            if (i == 0) {
                response.setContentType(getContentTypeFromName(files[i].getPath()));
            }
            OutputStream outputStream = response.getOutputStream();
            Files.copy(files[i].toPath(), outputStream);
            outputStream.flush();
        }
    }

    private String getContentTypeFromName(String name) {
        name = name.toLowerCase();

        if (name.endsWith(".png")) {
            return "image/png";
        }

        if (name.endsWith(".jpg")) {
            return "image/jpeg";
        }

        if (name.endsWith(".html")) {
            return "text/html";
        }

        if (name.endsWith(".css")) {
            return "text/css";
        }

        if (name.endsWith(".js")) {
            return "application/javascript";
        }

        throw new IllegalArgumentException("Can't find content type for '" + name + "'.");
    }
}
