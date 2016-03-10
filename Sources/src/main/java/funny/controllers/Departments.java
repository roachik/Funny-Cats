package funny.controllers;

import funny.Base;
import funny.DB;
import funny.entity.Department;
import funny.models.ModelDepartments;
import funny.models.ModelPositions;
import funny.models.ModelSchedules;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Tony on 05.03.2016.
 */
@Controller
public class Departments extends Base {

    @RequestMapping("/departments")
    public String departments(Model model, @RequestParam(value="id",required = false) String id) throws SQLException {
        putModel(model);
        model.addAttribute("breadcrumbs",getBreadcrumbs(setBreadcrumbs()));
        int pid = (id!=null)?Integer.parseInt(id):0;
        model.addAttribute("table", ModelDepartments.getDepartments(pid));
        return "departments";
    }

    @RequestMapping("/departments/edit")
    public String edit(Model model, @RequestParam(value="name",required = false) String newname,@RequestParam(value="id",required = true) String id) throws SQLException {
        if(newname != null){
            ModelDepartments.updateDepartment(Integer.parseInt(id),newname);
        }
        putModel(model);
        model.addAttribute("breadcrumbs",getBreadcrumbs(setBreadcrumbs()));
        model.addAttribute("info", ModelDepartments.getDepartment(Integer.parseInt(id)));
        return "edit_departments";
    }

    @RequestMapping("/departments/delete")
    public String delete(Model model,@RequestParam(value="id",required = true) String id) throws SQLException {
        Department info = ModelDepartments.getDepartment(Integer.parseInt(id));
        ModelDepartments.deleteById(Department.class, Integer.parseInt(id));
        return "redirect:/departments?id="+info.getParentId();
    }

    @RequestMapping("/departments/add")
    public String add(Model model,@RequestParam(value="id",required = false) String id,
                      @RequestParam(value="name",required = false) String name) throws SQLException {
        if(name != null){
            if(id == null) id = "0";
            ModelDepartments.add(Integer.parseInt(id),name);
            return "redirect:/departments?id="+id;
        }
        return "add_department";
    }

    private ArrayList<String> setBreadcrumbs()
    {
        ArrayList<String> breads = new ArrayList<String>();
        breads.add("<a href=\"/\">Главная</a>");
        breads.add("<a href=\"/departments\">Департаменты</a>");
        return breads;
    }


}
