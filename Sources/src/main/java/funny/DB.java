package funny;

import funny.entity.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
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





}
