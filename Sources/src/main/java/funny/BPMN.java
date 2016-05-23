package funny;

import funny.entity.EmployersOfStaffs;
import funny.entity.Schedule;
import funny.models.ModelEmployers;
import funny.models.ModelSchedules;

/**
 * Created by Tony on 21.05.2016.
 */
public class BPMN {

    public boolean checkSchedule(Integer id){
        EmployersOfStaffs e = ModelEmployers.getEmployerOfStaff(id);
        Schedule s = ModelSchedules.getScheduleDepAndPos(e.getDepartment().getDepartmentId(),e.getPosition().getPositionId());
        return s.getActive()==1;
    }


}
