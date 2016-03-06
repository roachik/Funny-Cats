package funny;

import funny.entity.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tony on 22.02.2016.
 */
public class DB {
    private static DB instance = new DB();

    private DB() {

    }

    public static DB getInstance() {
        return instance;
    }

    public boolean checkUser(String name,String password) throws SQLException {
        Session dbsession = HibernateSessionFactory.getSessionFactory().openSession();
        Criteria c = dbsession.createCriteria(Users.class)
                .add(Restrictions.eq("name", name)).add(Restrictions.eq("password", password));
        c.setMaxResults(1);
        List<Users> list = c.list();
        dbsession.close();
        return list.size() > 0;
    }

    public List<Departments> getDepartments(int parentId) {
        Session dbsession = HibernateSessionFactory.getSessionFactory().openSession();
        Criteria c = dbsession.createCriteria(Departments.class)
                .add(Restrictions.eq("parentId", parentId));
        List<Departments> list = c.list();
        for (Departments departments : list) {
            boolean isHas = checkDepartmentChilds(departments.getDepartmentId());
            departments.setHasChilds(isHas);
            //System.out.println("Check dep "+departments.getDepartmentId()+" res "+isHas);
        }
        dbsession.close();
        return list!=null?list:new ArrayList<Departments>();
    }

    public List<Schedules> getPositions(int id) {
        Session dbsession = HibernateSessionFactory.getSessionFactory().openSession();
        Criteria c = dbsession.createCriteria(Schedules.class, "Schedules");
        c.createAlias("Schedules.positionId", "Positions"); // inner join by default
        c.createAlias("Schedules.departmentId", "Departments"); // inner join by default
        c.add(Restrictions.eq("Departments.departmentId",id));
        List<Schedules> list = c.list();
        dbsession.close();
        return list;
    }

    public Departments getDepartment(int id) {
        Session dbsession = HibernateSessionFactory.getSessionFactory().openSession();
        Criteria c = dbsession.createCriteria(Departments.class)
                .add(Restrictions.eq("departmentId", id)).setMaxResults(1);
        List<Departments> list = c.list();
        dbsession.close();
        return list!=null?list.get(0):null;
    }

    public void updateDepartment(int id,String name) {
        Session dbsession = HibernateSessionFactory.getSessionFactory().openSession();
        dbsession.beginTransaction();
        Departments dep = (Departments)dbsession.get(Departments.class, id);
        dep.setName(name);
        dbsession.update(dep);
        dbsession.getTransaction().commit();
    }

    /**
     * Проверяет, есть ли у департамента дети (поддепартаменты)
     * @param id
     * @return
     */
    public boolean checkDepartmentChilds(int id)
    {
        Session dbsession = HibernateSessionFactory.getSessionFactory().openSession();
        Criteria c = dbsession.createCriteria(Departments.class)
                .add(Restrictions.eq("parentId", id));
        List<Departments> list = c.list();
        dbsession.close();
        return (list.size() > 0);
    }

}
