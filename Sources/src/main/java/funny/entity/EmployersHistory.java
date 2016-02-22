package funny.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Tony on 22.02.2016.
 */
@Entity
@Table(name="EmployersHistory")
public class EmployersHistory {

    @Id
    @GeneratedValue()
    private int historyId;

    @ManyToOne
    @JoinColumn(name="employerId")
    private Employers employerId;

    @Column(name="currentEmployerId")
    private int currentEmployerId;

    @Column(name="event")
    private String event;

    @Column(name="startDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name="endDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public int getCurrentEmployerId() {
        return currentEmployerId;
    }

    public void setCurrentEmployerId(int currentEmployerId) {
        this.currentEmployerId = currentEmployerId;
    }

    public Employers getEmployerId() {
        return employerId;
    }

    public void setEmployerId(Employers employerId) {
        this.employerId = employerId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
