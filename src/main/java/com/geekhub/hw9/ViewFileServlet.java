package com.geekhub.hw9;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@WebServlet("/file/view")
public class ViewFileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parameter = req.getParameter("path");
        Path path = Paths.get(parameter);
        ArrayList<String> lines = new ArrayList<>();
        String result = "";

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path.toString()), "windows-1251"))) {
/*        try (BufferedReader reader = Files.newBufferedReader(path)) {*/
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (UnsupportedEncodingException e) {
            result = "Unknown Encoding!";
        } catch (IOException e) {
            result = "Error open file!";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = "Unknown exception!";
        }
        req.getSession().setAttribute("opened", result);
        if (result.equals("")) {
            req.setAttribute("lines", lines);
            req.setAttribute("backPath", path.getParent().toString());
            req.setAttribute("filename", path.getFileName());
            req.getRequestDispatcher("/WEB-INF/jsp/viewFile.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("/dir/view");
        }
    }
}
