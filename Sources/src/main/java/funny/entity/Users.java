package funny.entity;

import javax.persistence.*;

/**
 * Created by Tony on 22.02.2016.
 */
@Entity
@Table(name="users")
public class Users {
    @Id
    @GeneratedValue()
    private int userId;

    @Column(name="name")
    private String name;

    @Column(name="password")
    private String password;


    public int getUserId() {

        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
