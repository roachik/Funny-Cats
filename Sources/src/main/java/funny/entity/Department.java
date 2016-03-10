package funny.entity;

import javax.persistence.*;

/**
 * Created by Tony on 22.02.2016.
 */
@Entity
@Table(name="Departments")
public class Department {

    @Id
    @GeneratedValue()
    private int departmentId;

    @Column(name="parent_id")
    private int parentId;

    @Column(name="name")
    private String name;

    //Означает, что нет в БД в таблице такого поля, чтобы гибернейт не проверял его в базе.
    @Transient
    private boolean hasChilds;

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

    public boolean isHasChilds() {
        return hasChilds;
    }

    public void setHasChilds(boolean hasChilds) {
        this.hasChilds = hasChilds;
    }
}
