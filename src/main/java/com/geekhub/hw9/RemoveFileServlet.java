package com.geekhub.hw9;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet("/file/remove")
public class RemoveFileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parameter = req.getParameter("path");
        Path path = Paths.get(parameter);
        String result = "";
        if (Files.deleteIfExists(path)) {
            result = "deleted successfully!";
        }
        req.getSession().setAttribute("deleted", result);
        req.getSession().setAttribute("path", path.getParent().toString());
        resp.sendRedirect("/dir/view");
    }
}
