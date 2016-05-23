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
public class ModelSchedules extends ModelMain {

    public static void deleteSchedule(Position posID, Department depID) {
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();
        Schedule sc = (Schedule) dbsession.createCriteria(Schedule.class)
                .add(Restrictions.eq("position", posID)).add(Restrictions.eq("department", depID)).uniqueResult();
        dbsession.delete(sc);
        dbsession.getTransaction().commit();
        //dbsession.close();
    }

    public static List<Schedule> getSchedules(int id) {
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();
        Criteria c = dbsession.createCriteria(Schedule.class, "Schedules");
        c.createAlias("Schedules.position", "Positions"); // inner join by default
        c.createAlias("Schedules.department", "Departments"); // inner join by default
        c.add(Restrictions.eq("Departments.departmentId",id));
        List<Schedule> list = c.list();
        dbsession.getTransaction().commit();
        return list;
    }

    public static Schedule getSchedule(int id) {
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();
        Criteria c = dbsession.createCriteria(Schedule.class)
                .add(Restrictions.eq("scheduleId", id)).setMaxResults(1);
        List<Schedule> list = c.list();
        dbsession.getTransaction().commit();
        return list!=null?list.get(0):null;
    }

    public static Schedule getScheduleDepAndPos(int dep, int pos) {
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();
        Criteria c = dbsession.createCriteria(Schedule.class, "Schedules");
        c.createAlias("Schedules.position", "Positions"); // inner join by default
        c.createAlias("Schedules.department", "Departments"); // inner join by default
        c.add(Restrictions.eq("Departments.departmentId",dep));
        c.add(Restrictions.eq("Positions.positionId",pos));
        List<Schedule> list = c.list();
        dbsession.getTransaction().commit();
        return list!=null?list.get(0):null;
    }

    public static void updateSchedule(int id,int dep,int pos,int count) {
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();
        Schedule s = (Schedule)dbsession.get(Schedule.class, id);
        s.setNumber(count);
        s.setDepartment((Department)ModelMain.getByID(Department.class,dep));
        s.setPosition((Position) ModelMain.getByID(Position.class,pos));
        dbsession.update(s);
        dbsession.getTransaction().commit();
    }

    public static void activate(int id)
    {
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();
        Schedule s = (Schedule)dbsession.get(Schedule.class, id);
        s.setActive(1);
        dbsession.update(s);
        dbsession.getTransaction().commit();
    }

    public static void addSchedule(int dep,int pos,int count) {
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();
        Schedule s = new Schedule();
        s.setNumber(count);
        s.setDepartment((Department)ModelMain.getByID(Department.class,dep));
        s.setPosition((Position) ModelMain.getByID(Position.class,pos));
        dbsession.save(s);
        dbsession.getTransaction().commit();
    }

}
