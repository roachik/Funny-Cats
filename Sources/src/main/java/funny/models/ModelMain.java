package funny.models;

import funny.HibernateSessionFactory;
import funny.entity.EmployersOfStaffs;
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
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();
        Object persistentInstance = dbsession.load(type, id);
        if (persistentInstance != null) {
            dbsession.delete(persistentInstance);
            dbsession.getTransaction().commit();
            //dbsession.close();
            return true;
        }
        dbsession.getTransaction().commit();
        //dbsession.close();
        return false;
    }

    public static Object getByID(Class<?> type, Serializable id) {
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();
        Object persistentInstance = dbsession.load(type, id);
        //dbsession.close();
        return persistentInstance;
    }

    public static Users checkUser(String name,String password) throws SQLException {
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();
        Criteria c = dbsession.createCriteria(Users.class)
                .add(Restrictions.eq("name", name)).add(Restrictions.eq("password", password));
        c.setMaxResults(1);
        List<Users> list = c.list();
        //dbsession.close();
        return list.size()==0?null:list.get(0);
    }

    public static boolean isInRole(int employer,int role) {
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();
        Criteria c = dbsession.createCriteria(EmployersOfStaffs.class, "EmployersOfStaffs");
        c.createAlias("EmployersOfStaffs.position", "Positions"); // inner join by default
        c.createAlias("EmployersOfStaffs.department", "Departments"); // inner join by default
        c.createAlias("EmployersOfStaffs.employer", "Employers"); // inner join by default
        c.add(Restrictions.eq("Employers.employerId",employer));
        c.add(Restrictions.eq("Position.role",role));
        c.setMaxResults(1);
        List<EmployersOfStaffs> list = c.list();
        //dbsession.close();
        return list.size()>0;
    }

    public static int getRoleForDep(int dep,int pos,int employer){
        System.out.println("asd2 "+pos);
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();
        Criteria c = dbsession.createCriteria(EmployersOfStaffs.class, "EmployersOfStaffs");
        c.createAlias("EmployersOfStaffs.position", "Positions"); // inner join by default
        c.createAlias("EmployersOfStaffs.department", "Departments"); // inner join by default
        c.createAlias("EmployersOfStaffs.employer", "Employers"); // inner join by default
        c.add(Restrictions.eq("Departments.departmentId",dep));
        c.add(Restrictions.eq("Positions.positionId",pos));
        c.add(Restrictions.eq("Employers.employerId",employer));
        c.setMaxResults(1);
        List<EmployersOfStaffs> list = c.list();
        dbsession.getTransaction().commit();
        //dbsession.close();
        System.out.println("asd "+list.size());
        if(list.size() > 0) return list.get(0).getPosition().getRole();
        else return -1;
    }
}
