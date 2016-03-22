package funny.controllers;

import funny.Base;
import funny.DB;
import funny.entity.Department;
import funny.entity.Position;
import funny.entity.Schedule;
import funny.models.ModelDepartments;
import funny.models.ModelPositions;
import funny.models.ModelSchedules;
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
        //model.addAttribute("breadcrumbs",getBreadcrumbs(setBreadcrumbs()));
        List<Schedule> list = ModelSchedules.getSchedules(Integer.parseInt(id));
        model.addAttribute("table", list);
        return "schedules";
    }

    @RequestMapping("/schedules/edit")
    public String edit(Model model, @RequestParam(value="number",required = false) String number,@RequestParam(value="pos",required = false) String pos,@RequestParam(value="dep",required = false) String dep,@RequestParam(value="id",required = true) String id) throws SQLException {
        if(dep != null){
            ModelSchedules.updateSchedule(Integer.parseInt(id),Integer.parseInt(dep),Integer.parseInt(pos),Integer.parseInt(number));
        }
        putModel(model);
        model.addAttribute("breadcrumbs",getBreadcrumbs(setBreadcrumbs()));
        model.addAttribute("info", ModelSchedules.getSchedule(Integer.parseInt(id)));
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
        model.addAttribute("Poss", ModelPositions.getPositions());
        model.addAttribute("Deps", ModelDepartments.getDepartmentsAll());
        return "add_schedules";
    }

    private ArrayList<String> setBreadcrumbs()
    {
        ArrayList<String> breads = new ArrayList<String>();
        breads.add("<a href=\"/\">Главная</a>");
        breads.add("<a href=\"/schedules\">Ставки</a>");
        return breads;
    }


}