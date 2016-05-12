package funny.controllers;

import funny.Base;
import funny.entity.Department;
import funny.entity.Employer;
import funny.entity.Schedule;
import funny.models.ModelDepartments;
import funny.models.ModelMain;
import funny.models.ModelTasks;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Tony on 05.03.2016.
 */
@Controller
public class Tasks extends Base {

    @RequestMapping("/tasks")
    public String tasks(Model model) throws SQLException {
        putModel(model);
        ArrayList<String> breads = new ArrayList<String>();
        breads.add("<a href=\"/\">Главная</a>");
        breads.add("<a href=\"/tasks\">Задачи</a>");
        model.addAttribute("breadcrumbs",getBreadcrumbs(breads));

        Employer e = (Employer) getSession().getAttribute("emp");
        model.addAttribute("userid",e.getEmployerId());

        // текущий пользователь менеджер
        if(ModelMain.isInRole(e.getEmployerId(),1)){
            model.addAttribute("employers", ModelTasks.getEmployersWithoutStaffs());
        }

        // текущий пользователь начальник
        if(ModelMain.isInRole(e.getEmployerId(),2)){
            model.addAttribute("schedules", ModelTasks.getSchedules(e.getEmployerId()));
            model.addAttribute("staffs", ModelTasks.getStaffs(e.getEmployerId()));

        }


        model.addAttribute("role", ModelMain.getRole(e.getEmployerId()));
        return "tasks";
    }



}