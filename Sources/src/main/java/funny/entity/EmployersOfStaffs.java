package funny.entity;

import javax.persistence.*;

/**
 * Created by Tony on 22.02.2016.
 */
@Entity
@Table(name="EmployersOfStaffs")
public class EmployersOfStaffs {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int staffId;

    @ManyToOne
    @JoinColumn(name="departmentId")
    private Department department;

    @ManyToOne
    @JoinColumn(name="positionId")
    private Position position;

    @ManyToOne
    @JoinColumn(name="employerId")
    private Employer employer;

    @Column(name="part")
    private double part;

    @Column(name="active")
    private int isActive;

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department departmentId) {
        this.department = departmentId;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position positionId) {
        this.position = positionId;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employerId) {
        this.employer = employerId;
    }

    public double getPart() {
        return part;
    }

    public void setPart(double part) {
        this.part = part;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }
}
