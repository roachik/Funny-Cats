package funny.entity;

import javax.persistence.*;

/**
 * Created by Tony on 22.02.2016.
 */
@Entity
@Table(name="EmployersOfStaffs")
public class EmployersOfStaffs {

    @Id
    @GeneratedValue()
    private int staffId;

    @ManyToOne
    @JoinColumn(name="departmentId")
    private Departments departmentId;

    @ManyToOne
    @JoinColumn(name="positionId")
    private Positions positionId;

    @ManyToOne
    @JoinColumn(name="employerId")
    private Employers employerId;

    @Column(name="part")
    private double part;

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public Departments getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Departments departmentId) {
        this.departmentId = departmentId;
    }

    public Positions getPositionId() {
        return positionId;
    }

    public void setPositionId(Positions positionId) {
        this.positionId = positionId;
    }

    public Employers getEmployerId() {
        return employerId;
    }

    public void setEmployerId(Employers employerId) {
        this.employerId = employerId;
    }

    public double getPart() {
        return part;
    }

    public void setPart(double part) {
        this.part = part;
    }
}
