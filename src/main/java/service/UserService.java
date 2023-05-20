package service;

import entity.Task;
import entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;
import java.util.Queue;

public class UserService {

    public boolean register(String userName, String password, String passwordRepeat){
        if(password.equals(passwordRepeat)) {
            HibernateUtil.getSessionFactory();
            Session session = HibernateUtil.openSession();
            Transaction transaction = session.beginTransaction();
            User user = new User(userName, password);
            session.persist(user);
            transaction.commit();
            session.close();
            return true;
        }
        return false;
    }

    public boolean login(String userName, String password){
        System.out.println(userName);
        HibernateUtil.getSessionFactory();
        Session session = HibernateUtil.openSession();
        Query<User> query = session.createQuery("from User where userName = :param", User.class);
        query.setParameter("param", userName);
        List<User> list = query.list();
        session.close();
        if(list.get(0).getPassword().equals(password)){
            return true;
        } else {
            return false;
        }
    }
}
