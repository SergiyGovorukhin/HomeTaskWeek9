package com.geekhub.hw9;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet("/file/view")
public class ViewFileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parameter = req.getParameter("path");
        Path path = Paths.get(parameter);
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<title>Remote File Manager</title>");
        sb.append("</head>");
        sb.append("<body>");
        sb.append("<h1 align=\"center\">Remote File Manager</h1>");
        sb.append("<table bgcolor=\"#FFFFFF\" width=\"600\" align=\"center\">");
        sb.append("<tr><td>");

        // "back" link
        sb.append("<form name=\"back\" method=\"get\" action=\"/dir/view\">");
        sb.append("<input type=\"submit\" value=\"Back\">");
        sb.append("<input type=\"hidden\" name=\"path\" value=\"").append(path.getParent()).append("\">");
        sb.append("</form>");

        sb.append("</td></tr>");
        sb.append("<tr><td>");
        sb.append("File: ").append(path.getFileName());
        sb.append("<hr>");


        // file content output
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("<br>");
            }
        } catch (UnsupportedEncodingException e) {
            sb.append("Unknown Encoding!");
        } catch (IOException e) {
            sb.append("Error open file!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        sb.append("</td></tr>");
        sb.append("</table>");
        sb.append("</body>");
        sb.append("</html>");
        resp.setContentType("text/html; charset=UTF-8");
        resp.getWriter().write(sb.toString());
    }
}
