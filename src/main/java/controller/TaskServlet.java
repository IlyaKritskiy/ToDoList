package controller;

import entity.Task;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import service.TasksService;
import util.HibernateUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

public class TaskServlet extends HttpServlet {

    TasksService tasksService = new TasksService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HibernateUtil.getSessionFactory();
        Session session = HibernateUtil.openSession();

        Query<Task> from_task = session.createQuery("from Task", Task.class);
        List<Task> list = from_task.list();

        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();

        for (Task task : list) {
            writer.println(String.format("<center><h1>%d)\t%s\t%s\t%s</h1> + " +
                    task.getId(), task.getName(), task.getDescription(), task.getDate()));
        }
        session.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("_method").equals("DELETE")){
            doDelete(req, resp);
            return;
        }
        if(req.getParameter("_method").equals("PUT")){
            doPut(req, resp);
            return;
        }
        tasksService.addTask(req.getParameter("taskname"), req.getParameter("description"), LocalDate.parse(req.getParameter("date")));
//        if(result){
//            HttpSession session = req.getSession();
//            session.setAttribute("username", req.getParameter("username"));
//            session.setAttribute("password", req.getParameter("password"));
//            resp.setStatus(resp.SC_MOVED_PERMANENTLY);
//            resp.sendRedirect("http://localhost:8080/task");
//        } else {
            String message = "Task not added!";
            req.setAttribute("message", message);
//        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        tasksService.deleteTask(Integer.parseInt(req.getParameter("id")));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(resp.SC_MOVED_PERMANENTLY);
        resp.sendRedirect("http://localhost:8080/task.html");
    }
}
