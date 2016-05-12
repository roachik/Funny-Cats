package funny.models;

import funny.HibernateSessionFactory;
import funny.controllers.Employers;
import funny.entity.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tony on 10.03.2016.
 */
public class ModelEmployers extends ModelMain{

    public static List<EmployersOfStaffs> getEmployersOfStaffs(int dep,int pos) {
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();
        Criteria c = dbsession.createCriteria(EmployersOfStaffs.class, "EmployersOfStaffs");
        c.createAlias("EmployersOfStaffs.position", "Positions"); // inner join by default
        c.createAlias("EmployersOfStaffs.department", "Departments"); // inner join by default
        c.add(Restrictions.eq("Departments.departmentId",dep));
        c.add(Restrictions.eq("Positions.positionId",pos));
        List<EmployersOfStaffs> list = c.list();
        dbsession.getTransaction().commit();
        return list!=null?list:new ArrayList<EmployersOfStaffs>();
    }

    public static void updateStaff(int id,double part,int active) {
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();
        EmployersOfStaffs s = dbsession.get(EmployersOfStaffs.class, id);

        Criteria c = dbsession.createCriteria(EmployersOfStaffs.class, "EmployersOfStaffs");
        c.createAlias("EmployersOfStaffs.position", "Positions"); // inner join by default
        c.createAlias("EmployersOfStaffs.department", "Departments"); // inner join by default
        c.createAlias("EmployersOfStaffs.employer", "Employers"); // inner join by default
        c.add(Restrictions.eq("Departments.departmentId",s.getDepartment().getDepartmentId()));
        c.add(Restrictions.eq("Positions.positionId",s.getPosition().getPositionId()));
        c.add(Restrictions.ne("Employers.employerId",s.getEmployer().getEmployerId()));
        List<EmployersOfStaffs> list = c.list();
        double sum = 0;
        for (EmployersOfStaffs employersOfStaffs : list) {
            sum+=employersOfStaffs.getPart();
        }
        sum+=part;
        Criteria cs = dbsession.createCriteria(Schedule.class, "Schedules");
        cs.createAlias("Schedules.position", "Positions"); // inner join by default
        cs.createAlias("Schedules.department", "Departments"); // inner join by default
        cs.add(Restrictions.eq("Departments.departmentId",s.getDepartment().getDepartmentId()));
        cs.add(Restrictions.eq("Positions.positionId",s.getPosition().getPositionId()));
        cs.setMaxResults(1);
        List<Schedule> lists = cs.list();
        int max = lists.get(0).getNumber();
        if(sum <= max && part <=1) {

            s.setPart(part);
            s.setIsActive(active);
            dbsession.update(s);

        }
        dbsession.getTransaction().commit();
    }

    public static void activate(int id) {
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();
        EmployersOfStaffs s = dbsession.get(EmployersOfStaffs.class, id);
        s.setIsActive(1);
        dbsession.update(s);
        dbsession.getTransaction().commit();
    }

    public static void addStaff(int dep,int pos,int emp,double part) {
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();

        Criteria c = dbsession.createCriteria(EmployersOfStaffs.class, "EmployersOfStaffs");
        c.createAlias("EmployersOfStaffs.position", "Positions"); // inner join by default
        c.createAlias("EmployersOfStaffs.department", "Departments"); // inner join by default
        c.createAlias("EmployersOfStaffs.employer", "Employers"); // inner join by default
        c.add(Restrictions.eq("Departments.departmentId",dep));
        c.add(Restrictions.eq("Positions.positionId",pos));
        List<EmployersOfStaffs> list = c.list();
        double sum = 0;
        for (EmployersOfStaffs employersOfStaffs : list) {
            sum+=employersOfStaffs.getPart();
        }
        sum+=part;
        Criteria cs = dbsession.createCriteria(Schedule.class, "Schedules");
        cs.createAlias("Schedules.position", "Positions"); // inner join by default
        cs.createAlias("Schedules.department", "Departments"); // inner join by default
        cs.add(Restrictions.eq("Departments.departmentId",dep));
        cs.add(Restrictions.eq("Positions.positionId",pos));
        cs.setMaxResults(1);
        List<Schedule> lists = cs.list();
        int max = lists.get(0).getNumber();
        if(sum <= max && part <=1) {
            EmployersOfStaffs s = new EmployersOfStaffs();
            s.setPart(part);
            s.setDepartment((Department) ModelMain.getByID(Department.class, dep));
            s.setPosition((Position) ModelMain.getByID(Position.class, pos));
            s.setEmployer((Employer) ModelMain.getByID(Employer.class, emp));
            dbsession.save(s);

        }
        dbsession.getTransaction().commit();
    }

    public static List<Employer> getEmployers() {
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();
        Criteria c = dbsession.createCriteria(Employer.class);
        List<Employer> list = c.list();
        dbsession.getTransaction().commit();
        return list;
    }

    public static EmployersOfStaffs getEmployerOfStaff(int id) {
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();
        Criteria c = dbsession.createCriteria(EmployersOfStaffs.class)
                .add(Restrictions.eq("staffId", id)).setMaxResults(1);
        List<EmployersOfStaffs> list = c.list();
        dbsession.getTransaction().commit();
        return list!=null?list.get(0):null;
    }

    public static void addEmployer(String name) {
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();
        Employer d = new Employer();
        d.setName(name);
        dbsession.save(d);
        dbsession.getTransaction().commit();
    }

    public static Employer getEmployer(int id) {
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();
        Criteria c = dbsession.createCriteria(Employer.class)
                .add(Restrictions.eq("employerId", id)).setMaxResults(1);
        List<Employer> list = c.list();
        dbsession.getTransaction().commit();
        return list!=null?list.get(0):null;
    }

    public static void updateEmployer(int id,String name) {
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();
        Employer pos = dbsession.get(Employer.class, id);
        pos.setName(name);
        dbsession.update(pos);
        dbsession.getTransaction().commit();
    }

}
