package funny;

import funny.entity.Users;
import org.hibernate.Criteria;
import org.hibernate.Session;
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

}
