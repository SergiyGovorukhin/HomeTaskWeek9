package com.geekhub.hw9;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.*;

@WebServlet(value = "/dir/view", initParams = {
    @WebInitParam(name = "root", value = "D:\\")
})
public class ViewDirectoryServlet extends HttpServlet {

    private static Path ROOT_PATH;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ROOT_PATH = Paths.get(config.getInitParameter("root"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Path path = Paths.get((String) session.getAttribute("path"));

        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        //TODO: Implement directory listing here
        sb.append("<head>");
        sb.append("<title>Remote File Manager</title>");
        sb.append("<style>");
        sb.append(".row{background: #F5F5DC; padding: 5px; padding-right: 20px; border: solid 1px black; }");
        sb.append("input[type=submit] { background: none;padding: 0;border: none;color: #2E5B82; font: inherit;cursor: pointer;text-decoration: underline;}");
        sb.append("input[type=submit]:hover{color: #D11010;}");
        sb.append("</style>");
        sb.append("</head>");
        sb.append("<body>");
        sb.append("<h1 align=\"center\">Remote File Manager</h1>");
        sb.append("PARAMETER = " + req.getParameter("X"));
        sb.append("<form name=\"main\" method=\"get\">");
        sb.append("<table bgcolor=\"#F0F8FF\" width=\"600\" align=\"center\">");
        String pathParam = req.getParameter("path");
        path = (pathParam == null) ? ROOT_PATH : Paths.get(ROOT_PATH + pathParam);
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {
            for (Path entry: directoryStream) {
                appendLink(sb, entry.getName(entry.getNameCount()-1).toString(), path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        sb.append("</table>");
        sb.append("<input type=\"hidden\" name=\"path\" value=\"" + path + "\">");
        sb.append("</form>");
        sb.append("</body>");
        sb.append("</html>");
        resp.setContentType("text/html");
        resp.getWriter().write(sb.toString());
    }

    /**
     * Appends link to file or directory
     * @param sb contains list of links to render
     * @param text is a link name
     * @param path is a link path
     */
    private void appendLink(StringBuilder sb, String text, Path path) {
        sb.append("<tr><td>");
        sb.append("<div class=\"row\">");
        if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
            sb.append("<input type=\"submit\" name=\"" + text + "\" value=\"" + text + "\" onClick=\"document.main.path.value=this.value\"");
            //sb.append("<a href=\"/dir/view\">" + text + "</a>");
        } else {
            sb.append("<a href=\"/file/view\">" + text + "</a>");
        }
        sb.append("</div>");
        sb.append("</td></tr>");
    }
}
