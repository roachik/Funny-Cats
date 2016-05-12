package funny.models;

import funny.HibernateSessionFactory;
import funny.entity.Department;
import funny.entity.Employer;
import funny.entity.EmployersOfStaffs;
import funny.entity.Schedule;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tony on 09.03.2016.
 */
public class ModelTasks extends ModelMain{

    public static List<Schedule> getSchedules(int e) {
        List<Department> d = getDepUser(e);
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();

        Criteria c = dbsession.createCriteria(Schedule.class, "Schedules");
        c.createAlias("Schedules.position", "Positions"); // inner join by default
        c.createAlias("Schedules.department", "Departments"); // inner join by default
        c.add(Restrictions.eq("Schedules.active",0));
        c.add(Restrictions.in("Schedules.department",d));
        List<Schedule> list = c.list();
        dbsession.getTransaction().commit();
        return list;
    }

    public static List<EmployersOfStaffs> getStaffs(int e) {
        List<Department> d = getDepUser(e);
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();

        Criteria c = dbsession.createCriteria(EmployersOfStaffs.class, "EmployersOfStaffs");
        c.createAlias("EmployersOfStaffs.position", "Positions"); // inner join by default
        c.createAlias("EmployersOfStaffs.department", "Departments"); // inner join by default
        c.createAlias("EmployersOfStaffs.employer", "Employers"); // inner join by default
        c.add(Restrictions.in("EmployersOfStaffs.department",d));
        c.add(Restrictions.eq("EmployersOfStaffs.isActive",0));
        List<EmployersOfStaffs> list = c.list();
        dbsession.getTransaction().commit();
        return list;
    }

    private static List<Department> getDepUser(int e){
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();
        List<Department> list = dbsession.createSQLQuery("select d.* from employersofstaffs eos\n" +
                "join departments d on eos.departmentId = d.departmentId\n" +
                "join positions p on eos.positionid = p.positionid\n" +
                "where eos.employerid = "+e+" and p.role = 2").addEntity(Department.class).list();
        dbsession.getTransaction().commit();
        return list!=null?list:new ArrayList<Department>();
    }

    public static List<Employer> getEmployersWithoutStaffs() {
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();
        List<Employer> list = dbsession.createSQLQuery("select e.* from employers e\n" +
                "LEFT JOIN employersofstaffs eos ON e.employerid = eos.employerid\n" +
                "where eos.employerid is null").addEntity(Employer.class).list();
        dbsession.getTransaction().commit();
        return list!=null?list:new ArrayList<Employer>();
    }



}
