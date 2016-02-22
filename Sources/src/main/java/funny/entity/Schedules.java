package funny.entity;

import javax.persistence.*;

/**
 * Created by Tony on 22.02.2016.
 */
@Entity
@Table(name="Schedules")
public class Schedules {

    @Id
    @GeneratedValue()
    private int sceduleId;

    @ManyToOne
    @JoinColumn(name="departmentId")
    private Departments departmentId;

    @ManyToOne
    @JoinColumn(name="positionId")
    private Positions positionId;

    public void setDepartmentId(Departments departmentId) {
        this.departmentId = departmentId;
    }

    public void setPositionId(Positions positionId) {
        this.positionId = positionId;
    }

    @Column(name="number")
    private int number;

    public int getSceduleId() {
        return sceduleId;
    }

    public void setSceduleId(int sceduleId) {
        this.sceduleId = sceduleId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
