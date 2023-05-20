package controller;

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
import java.util.List;

public class UserLoginServlet extends HttpServlet {

    UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        HibernateUtil.getSessionFactory();
//        Session session = HibernateUtil.openSession();
//        Query<User> from_user = session.createQuery("from User", User.class);
//        List<User> list = from_user.list();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean result = userService.login(req.getParameter("username"), req.getParameter("password"));
        if (result) {
            HttpSession session = req.getSession();
            session.setAttribute("username", req.getParameter("username"));
            session.setAttribute("password", req.getParameter("password"));
            resp.setStatus(resp.SC_MOVED_PERMANENTLY);
            resp.sendRedirect("http://localhost:8080/task");
        } else {
            String message = "Invalid username/password";
            req.setAttribute("message", message);
            resp.setStatus(resp.SC_MOVED_PERMANENTLY);
            resp.sendRedirect("http://localhost:8080/register.html");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
