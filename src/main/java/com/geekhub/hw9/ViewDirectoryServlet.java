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
    @WebInitParam(name = "root", value = "D:\\temp")
})
public class ViewDirectoryServlet extends HttpServlet {

    private static Path ROOT_PATH;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ROOT_PATH = Paths.get(config.getInitParameter("root"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<title>Remote File Manager</title>");

        // styles
        sb.append("<style>");
        sb.append(".create{visibility: hidden;}");
        sb.append(".row{background: #E8E8E8; padding: 5px; padding-right: 20px; border: solid 1px black; }");
        sb.append(".menu{background: #EED5B7; padding: 5px; padding-right: 20px; border: solid 1px black; }");
        sb.append(".path{background: #BCD2EE; padding: 5px; padding-right: 20px; border: solid 1px black; }");
        sb.append("input[type=submit] { background: none; padding: 0; border: none; color: #0000FF;");
        sb.append("font: inherit; cursor: pointer; text-decoration: underline;}");
        sb.append("input[type=submit]:hover{color: #D02090;}");
        sb.append("input[type=submit].dir {color: #B03060;}");
        sb.append("input[type=submit].file {color: #0000FF;}");
        sb.append("</style>");

        sb.append("</head>");
        sb.append("<body>");
        sb.append("<h1 align=\"center\">Remote File Manager</h1>");
        sb.append("<table bgcolor=\"#FFFFFF\" width=\"800\" align=\"center\">");

        // append file deleted message
        String parameter = req.getParameter("path");
        Path path = (parameter == null) ? ROOT_PATH : Paths.get(parameter);
        HttpSession session = req.getSession();
        String deleted = (String) session.getAttribute("deleted");
        if (deleted != null && !deleted.equals("")) {
            sb.append("<tr><td><div class=\"message\">");
            sb.append(deleted);
            sb.append("</div></td></tr>");
            session.setAttribute("deleted", "");
            path = Paths.get((String) session.getAttribute("path"));
        }

        // append file created message
        String created = (String) session.getAttribute("created");
        if (created != null && !created.equals("")) {
            sb.append("<tr><td><div class=\"message\">");
            sb.append(created);
            sb.append("</div></td></tr>");
            session.setAttribute("created", "");
            path = Paths.get((String) session.getAttribute("path"));
        }

        sb.append("<tr><td colspan=\"2\"><div class=\"menu\">");

        // append "back" link
        if (path.startsWith(ROOT_PATH)) {
            sb.append("<form name=\"back\" method=\"get\" action=\"/dir/view\">");
            sb.append("<input type=\"submit\" value=\"Back\">");
            sb.append("<input type=\"hidden\" name=\"path\" value=\"").append(path.getParent()).append("\">");
            sb.append("</form>");
        }

        // append "New File" menu
        sb.append("<form name=\"menu\" method=\"get\" action=\"/file/create\">");
        sb.append("<input type=\"text\" name=\"filename\">").append("   ");
        sb.append("<input type=\"submit\" value=\"Create New File\">");
        sb.append("<input type=\"hidden\" name=\"path\" value=\"").append(path).append("\">");
        sb.append("</form>");

        sb.append("</div></td></tr>");

        // append current path
        sb.append("<tr><td colspan=\"2\"><div class=\"path\">");
        sb.append("> ").append(path);
        sb.append("</div></td></tr>");

        // append files and directories list
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {
            for (Path entry: directoryStream) {
                appendLink(sb, entry.getName(entry.getNameCount()-1).toString(), entry.toAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        sb.append("</table>");
        sb.append("</body>");
        sb.append("</html>");
        resp.setContentType("text/html; charset=UTF-8");
        resp.getWriter().write(sb.toString());
    }

    /**
     * Appends link to file or directory
     * @param sb contains list of links to render
     * @param text is a link name
     * @param path is a absolute link path
     */
    private void appendLink(StringBuilder sb, String text, Path path) {
        sb.append("<tr><td><div class=\"row\">");
        String action = "";
        String clazz;
        if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
            action = "/dir/view";
            clazz = "dir";
        } else {
            if (Files.isReadable(path)) {
                action = "/file/view";
            }
            clazz = "file";
        }
        sb.append("<form name=\"main\" method=\"get\" action=\"").append(action).append("\">");
        sb.append("<input type=\"submit\" name=\"text\" value=\"").append(text).append("\" class=\"").append(clazz).append("\">");
        sb.append("<input type=\"hidden\" name=\"path\" value=\"" + path + "\">");
        sb.append("</form>");
        sb.append("</div></td>");
        sb.append("<td><div class=\"row\">");
        sb.append("<form name=\"del\" method=\"get\" action=\"/file/remove\">");
        sb.append("<input type=\"submit\" value=\"delete\" class=\"del\">");
        sb.append("<input type=\"hidden\" name=\"path\" value=\"").append(path).append("\">");
        sb.append("</form>");
        sb.append("</div></td>");
        sb.append("</tr>");
    }
}
