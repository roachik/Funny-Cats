package funny.models;

import funny.HibernateSessionFactory;
import funny.entity.Users;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Tony on 09.03.2016.
 */
public class ModelMain {

    /**
     * Общий метод для удаления данных из БД
     * @param type
     * @param id
     * @return
     */
    public static boolean deleteById(Class<?> type, Serializable id) {
        Session dbsession = HibernateSessionFactory.getSessionFactory().openSession();
        dbsession.beginTransaction();
        Object persistentInstance = dbsession.load(type, id);
        if (persistentInstance != null) {
            dbsession.delete(persistentInstance);
            dbsession.getTransaction().commit();
            dbsession.close();
            return true;
        }
        dbsession.close();
        return false;
    }

    public static Object getByID(Class<?> type, Serializable id) {
        Session dbsession = HibernateSessionFactory.getSessionFactory().openSession();
        dbsession.beginTransaction();
        Object persistentInstance = dbsession.load(type, id);
        dbsession.close();
        return persistentInstance;
    }

    public static boolean checkUser(String name,String password) throws SQLException {
        Session dbsession = HibernateSessionFactory.getSessionFactory().openSession();
        Criteria c = dbsession.createCriteria(Users.class)
                .add(Restrictions.eq("name", name)).add(Restrictions.eq("password", password));
        c.setMaxResults(1);
        List<Users> list = c.list();
        dbsession.close();
        return list.size() > 0;
    }
}
