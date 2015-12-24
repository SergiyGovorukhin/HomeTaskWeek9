package com.geekhub.hw9;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.*;

@WebServlet("/file/create")
public class CreateFileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathParam = (String) req.getSession().getAttribute("path");
        String filename = req.getParameter("filename");
        Path path = null;
        try {
            path = Paths.get(pathParam, filename);
        } catch (InvalidPathException e) {
            e.printStackTrace();
        }
        String result = "";
        try {
            if (Files.exists(Files.createFile(path))) {
                result = String.format("File \"%s\" created successfully!", filename);
            }
        } catch (FileAlreadyExistsException x) {
            result = String.format("File \"%s\" already exists!", filename);
        } catch (IOException x) {
            result = String.format("Error creating file \"%s\" !", filename);
        }
        req.getSession().setAttribute("created", result);
        resp.sendRedirect("/dir/view");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
