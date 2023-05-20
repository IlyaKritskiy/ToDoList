package controller;

import entity.Task;
import entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import service.UserService;
import util.HibernateUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class UserRegisterServlet extends HttpServlet {

    UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            HibernateUtil.getSessionFactory();
            Session session = HibernateUtil.openSession();

            Query<User> from_task = session.createQuery("from User", User.class);
            List<User> list = from_task.list();

            resp.setContentType("text/html");
            PrintWriter writer = resp.getWriter();

            for (User user : list) {
                writer.println(String.format("<center><h1>%d)\t%s</h1>",
//                                "<form action=\"/product?id=%d\" method=\"POST\">" +
//                                "<input type=\"hidden\" name=\"_method\" value=\"DELETE\">" +
//                                "<input type=\"submit\" value=\"Delete\">" + "&nbsp;&nbsp;&nbsp;&nbsp;" +
//                                "</form>" +
//                                "<form action=\"/product?id=%d\" method=\"POST\">" +
//                                "<input type=\"hidden\" name=\"_method\" value=\"PUT\">" +
//                                "<input type=\"submit\" value=\"Put\">" +
//                                "</form></center>",
                        user.getId(), user.getUserName()));
            }
            session.close();
        }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean result = userService.register(req.getParameter("username"), req.getParameter("password"), req.getParameter("password-repeat"));
        if(result){
            HttpSession session = req.getSession();
            session.setAttribute("username", req.getParameter("username"));
            session.setAttribute("password", req.getParameter("password"));
            resp.setStatus(resp.SC_MOVED_PERMANENTLY);
            resp.sendRedirect("http://localhost:8080/task");
        } else {
            String message = "Passwords mismatch";
            req.setAttribute("message", message);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
