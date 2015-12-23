package com.geekhub.hw9;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parameter = req.getParameter("path");
        req.getSession().setAttribute("path", parameter);
        resp.sendRedirect("/dir/view");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> dirLinks = new HashMap<>();
        Map<String, String> fileLinks = new HashMap<>();

        String pathParam = (String) req.getSession().getAttribute("path");
        Path path = (pathParam == null) ? ROOT_PATH : Paths.get(pathParam);
        // append files and directories list
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {
            for (Path entry: directoryStream) {
                String linkText = entry.getName(entry.getNameCount()-1).toString();
                Path linkPath = entry.toAbsolutePath();
                if (Files.isDirectory(linkPath, LinkOption.NOFOLLOW_LINKS)) {
                    dirLinks.put(linkText, linkPath.toString());
                } else if (Files.isReadable(entry.toAbsolutePath())) {
                    fileLinks.put(linkText, linkPath.toString());
                } else {
                    fileLinks.put(linkText, "");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        req.setAttribute("dirLinks", dirLinks);
        req.setAttribute("fileLinks", fileLinks);
        req.getSession().setAttribute("path", path.toString());
        req.setAttribute("backPath", path.getParent().toString());
        req.setAttribute("root", ROOT_PATH);
        req.getRequestDispatcher("/WEB-INF/jsp/viewDirectory.jsp").forward(req, resp);
    }
}
