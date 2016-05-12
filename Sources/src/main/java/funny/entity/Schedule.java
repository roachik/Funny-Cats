package funny.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;


/**
 * Created by Tony on 22.02.2016.
 */
@Entity
@Table(name="Schedules")
public class Schedule {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int scheduleId;

    @ManyToOne
    @JoinColumn(name="departmentId")
    private Department department;

    @ManyToOne
    @JoinColumn(name="positionId")
    private Position position;

    @Column(name="number")
    private int number;

    @Column(name="active")
    private int active;

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int sceduleId) {
        this.scheduleId = sceduleId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Position getPosition() {
        return position;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department departmentId) {
        this.department = departmentId;
    }

    public void setPosition(Position positionId) {
        this.position = positionId;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
