package funny.entity;

import javax.persistence.*;

/**
 * Created by Tony on 22.02.2016.
 */
@Entity
@Table(name="Employers")
public class Employer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int employerId;

    @Column(name="new_id")
    private int newId;

    @Column(name="is_dismissed")
    private boolean isDismissed;

    @Column(name = "name")
    private String name;

    @Column(name = "info")
    private String info;

    public int getEmployerId() {
        return employerId;
    }

    public void setEmployerId(int employerId) {
        this.employerId = employerId;
    }

    public int getNewId() {
        return newId;
    }

    public void setNewId(int newId) {
        this.newId = newId;
    }

    public boolean isDismissed() {
        return isDismissed;
    }

    public void setDismissed(boolean dismissed) {
        isDismissed = dismissed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}