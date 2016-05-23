package funny.models;

import funny.HibernateSessionFactory;
import funny.entity.Department;
import funny.entity.Position;
import funny.entity.Schedule;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tony on 09.03.2016.
 */
public class ModelDepartments extends ModelMain{

    public static List<Department> getDepartments(int parentId) {
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();
        Criteria c = dbsession.createCriteria(Department.class)
                .add(Restrictions.eq("parentId", parentId));
        List<Department> list = c.list();
        for (Department department : list) {
            boolean isHas = checkDepartmentChilds(department.getDepartmentId());
            department.setHasChilds(isHas);
            //System.out.println("Check dep "+departments.getDepartmentId()+" res "+isHas);
        }
        dbsession.getTransaction().commit();
        //dbsession.close();
        return list!=null?list:new ArrayList<Department>();
    }

    public static void add(int id, String name) {
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();
        Department d = new Department();
        d.setParentId(id);
        d.setName(name);
        dbsession.save(d);
        dbsession.getTransaction().commit();
    }

    public static List<Department> getDepartmentsAll() {
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();
        Criteria c = dbsession.createCriteria(Department.class);
        List<Department> list = c.list();
        dbsession.getTransaction().commit();
        //dbsession.close();
        return list!=null?list:new ArrayList<Department>();
    }

    public static Department getDepartment(int id) {
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();
        Criteria c = dbsession.createCriteria(Department.class)
                .add(Restrictions.eq("departmentId", id)).setMaxResults(1);
        List<Department> list = c.list();
        dbsession.getTransaction().commit();
        return list!=null?list.get(0):null;
    }

    public static void updateDepartment(int id,String name) {
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        dbsession.beginTransaction();
        Department dep = (Department)dbsession.get(Department.class, id);
        dep.setName(name);
        dbsession.update(dep);
        dbsession.getTransaction().commit();
    }

    /**
     * Проверяет, есть ли у департамента дети (поддепартаменты)
     * @param id
     * @return
     */
    public static boolean checkDepartmentChilds(int id)
    {
        Session dbsession = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        Criteria c = dbsession.createCriteria(Department.class)
                .add(Restrictions.eq("parentId", id));
        List<Department> list = c.list();
        //dbsession.close();
        return (list.size() > 0);
    }

    public static ArrayList<Department> getChilds(int d, ArrayList<Department> all, ArrayList<Department> result) {
        for (Department dep : all) {
            if(dep.getDepartmentId() == d) {
                result.add(dep);
                System.out.println("dep "+dep.getDepartmentId());
                getChilds(dep.getParentId(),all,result);
            }
        }
        return result;
    }

}
