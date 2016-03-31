package funny.models;

import funny.HibernateSessionFactory;
import funny.entity.Department;
import funny.entity.Position;
import funny.entity.Schedule;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by Tony on 09.03.2016.
 */
public class ModelPositions extends ModelMain {

    public static List<Position> getPositions() {
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();
        Criteria c = dbsession.createCriteria(Position.class);
        List<Position> list = c.list();
        dbsession.getTransaction().commit();
        return list;
    }

    public static void add(String name,int role) {
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();
        Position d = new Position();
        d.setName(name);
        d.setRole(role);
        dbsession.save(d);
        dbsession.getTransaction().commit();
    }


    public static Position getPosition(int id) {
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();
        Criteria c = dbsession.createCriteria(Position.class)
                .add(Restrictions.eq("positionId", id)).setMaxResults(1);
        List<Position> list = c.list();
        dbsession.getTransaction().commit();
        return list!=null?list.get(0):null;
    }

    public static void updatePosition(int id,String name,int role) {
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();
        Position pos = dbsession.get(Position.class, id);
        pos.setName(name);
        pos.setRole(role);
        dbsession.update(pos);
        dbsession.getTransaction().commit();
    }

    public static boolean deletePosition(Position posID, Department depID) {
        ModelSchedules.deleteSchedule(posID,depID);
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();
        dbsession.delete(posID);
        dbsession.getTransaction().commit();
       // dbsession.close();
        return true;
    }
}
