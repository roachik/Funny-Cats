package funny.entity;

import javax.persistence.*;

/**
 * Created by Tony on 22.02.2016.
 */
@Entity
@Table(name="Departments")
public class Departments {

    @Id
    @GeneratedValue()
    private int departmentId;

    @Column(name="parent_id")
    private int parentId;

    @Column(name="name")
    private String name;

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
