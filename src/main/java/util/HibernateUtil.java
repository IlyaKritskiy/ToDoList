package util;

import entity.Task;
import entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(){
        if(sessionFactory == null){
            sessionFactory = new Configuration()
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(Task.class)
                    .configure()
                    .buildSessionFactory();
        }
        return sessionFactory;
    }

    public static Session openSession(){
        return sessionFactory.openSession();
    }
}
