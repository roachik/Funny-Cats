package funny.controllers;

import funny.Base;
import funny.DB;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

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
        model.addAttribute("table", DB.getInstance().getDepartments(pid));
        return "departments";
    }

    @RequestMapping("/departments/edit")
    public String edit(Model model, @RequestParam(value="name",required = false) String newname,@RequestParam(value="id",required = true) String id) throws SQLException {
        if(newname != null){
            DB.getInstance().updateDepartment(Integer.parseInt(id),newname);
        }
        putModel(model);
        model.addAttribute("breadcrumbs",getBreadcrumbs(setBreadcrumbs()));
        model.addAttribute("info", DB.getInstance().getDepartment(Integer.parseInt(id)));
        return "edit_departments";
    }

    private ArrayList<String> setBreadcrumbs()
    {
        ArrayList<String> breads = new ArrayList<String>();
        breads.add("<a href=\"/\">Главная</a>");
        breads.add("<a href=\"/departments\">Департаменты</a>");
        return breads;
    }


}
