package com.geekhub.hw9;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");

        //TODO: Implement directory listing here

        sb.append("</html>");
        resp.getWriter().write(sb.toString());
    }

    private void appendLink(StringBuilder sb, String text, Path path) {
        //TODO: Implement link rendering based on path type (directory or file)
    }
}
