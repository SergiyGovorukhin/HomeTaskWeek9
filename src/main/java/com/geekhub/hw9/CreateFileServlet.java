package com.geekhub.hw9;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet("/file/create")
public class CreateFileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Path path = Paths.get(req.getParameter("path"), req.getParameter("filename"));
        String result = "";
        try {
            if (Files.exists(Files.createFile(path))) {
                result = String.format("File \"%s\" created successfully!", path.getFileName());
            }
        } catch (FileAlreadyExistsException x) {
            result = String.format("File \"%s\" already exists!", path.getFileName());
        } catch (IOException x) {
            result = String.format("Error creating file \"%s\" !", path.getFileName());
        }
        req.getSession().setAttribute("created", result);
        req.getSession().setAttribute("path", path.getParent().toString());
        resp.sendRedirect("/dir/view");

    }
}
