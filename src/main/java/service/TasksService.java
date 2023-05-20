package service;

import entity.Task;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.time.LocalDate;

public class TasksService {

    public void addTask(String name, String description, LocalDate date){
        HibernateUtil.getSessionFactory();
        Session session = HibernateUtil.openSession();
        Transaction transaction = session.beginTransaction();
        Task task = new Task(name, description, date);
        session.persist(task);
        transaction.commit();
        session.close();
    }

    public void updateTask(Task task){
        HibernateUtil.getSessionFactory();
        Session session = HibernateUtil.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(task);
        transaction.commit();
        session.close();
    }

    public void deleteTask(int id){
        HibernateUtil.getSessionFactory();
        Session session = HibernateUtil.openSession();
        Transaction transaction = session.beginTransaction();
        Task task = session.get(Task.class, id);
        session.remove(task);
        transaction.commit();
        session.close();
    }
}
