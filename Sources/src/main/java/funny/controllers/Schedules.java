package funny.controllers;

import funny.Base;
import funny.DB;
import funny.entity.*;
import funny.models.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tony on 05.03.2016.
 */
@Controller
public class Schedules extends Base {

    @RequestMapping("/schedules")
    public String schedules(Model model, @RequestParam(value="id",required = true) String id) throws SQLException {
        putModel(model);
        ArrayList<String> bread = setBreadcrumbs();
        Department d = ModelDepartments.getDepartment(Integer.parseInt(id));
        bread.add("<a href=\"/departments?id="+d.getDepartmentId()+"\">"+d.getName()+"</a>");
        bread.add("<a href=\"/schedules?id="+d.getDepartmentId()+"\">Ставки</a>");
        model.addAttribute("breadcrumbs",getBreadcrumbs(bread));
        List<Schedule> list = ModelSchedules.getSchedules(Integer.parseInt(id));
        model.addAttribute("table", list);
        Employer em = (Employer) getSession().getAttribute("emp");
        model.addAttribute("role", ModelMain.getRole(em.getEmployerId()));
        return "schedules";
    }

    @RequestMapping("/schedules/edit")
    public String edit(Model model, @RequestParam(value="number",required = false) String number,@RequestParam(value="pos",required = false) String pos,@RequestParam(value="dep",required = false) String dep,@RequestParam(value="active",required = false) String active,@RequestParam(value="id",required = true) String id) throws SQLException {
        if(dep != null){
            ModelSchedules.updateSchedule(Integer.parseInt(id),Integer.parseInt(dep),Integer.parseInt(pos),Integer.parseInt(number));
        }
        if(active != null && Integer.parseInt(active) == 1){
            ModelSchedules.activate(Integer.parseInt(id));
        }
        putModel(model);
        Schedule s = ModelSchedules.getSchedule(Integer.parseInt(id));
        ArrayList<String> bread = setBreadcrumbs();
        bread.add("<a href=\"/departments?id="+s.getDepartment().getDepartmentId()+"\">"+s.getDepartment().getName()+"</a>");
        bread.add("<a href=\"/schedules?id="+s.getDepartment().getDepartmentId()+"\">Ставки</a>");
        bread.add("<a href=\"/schedules/edit?id="+s.getScheduleId()+"\">Редактирование ставки</a>");
        model.addAttribute("breadcrumbs",getBreadcrumbs(bread));
        model.addAttribute("info", s);
        model.addAttribute("Poss", ModelPositions.getPositions());
        model.addAttribute("Deps", ModelDepartments.getDepartmentsAll());
        return "edit_schedules";
    }

    @RequestMapping("/schedules/delete")
    public String delete(Model model,@RequestParam(value="id",required = true) String id) throws SQLException {
        ModelSchedules.deleteById(Schedule.class, Integer.parseInt(id));
        return "redirect:/";
    }

    @RequestMapping("/schedules/add")
    public String add(Model model,@RequestParam(value="number",required = false) String number,@RequestParam(value="pos",required = false) String pos,@RequestParam(value="id",required = false) String dep) throws SQLException {
        if(pos != null){
            ModelSchedules.addSchedule(Integer.parseInt(dep),Integer.parseInt(pos),Integer.parseInt(number));
            return "redirect:/schedules?id="+dep;
        }
        putModel(model);
        ArrayList<String> bread = setBreadcrumbs();
        Department d = ModelDepartments.getDepartment(Integer.parseInt(dep));
        bread.add("<a href=\"/departments?id="+d.getDepartmentId()+"\">"+d.getName()+"</a>");
        bread.add("<a href=\"/schedules?id="+d.getDepartmentId()+"\">Ставки</a>");
        bread.add("<a href=\"/schedules/add?id="+d.getDepartmentId()+"\">Новая ставка</a>");
        model.addAttribute("breadcrumbs", getBreadcrumbs(bread));
        model.addAttribute("Poss", ModelPositions.getPositions());
        model.addAttribute("Deps", ModelDepartments.getDepartmentsAll());
        return "add_schedules";
    }

    private ArrayList<String> setBreadcrumbs()
    {
        ArrayList<String> breads = new ArrayList<String>();
        breads.add("<a href=\"/\">Главная</a>");
        breads.add("<a href=\"/departments\">Отделы</a>");
        return breads;
    }


}