package funny.controllers;

import funny.Base;
import funny.DB;
import funny.entity.Schedules;
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
public class Positions extends Base {

    @RequestMapping("/positions")
    public String positions(Model model, @RequestParam(value="id",required = true) String id) throws SQLException {
        putModel(model);
        //model.addAttribute("breadcrumbs",getBreadcrumbs(setBreadcrumbs()));
        List<Schedules> list = DB.getInstance().getPositions(Integer.parseInt(id));
        model.addAttribute("table", list);
        return "positions";
    }

    /*@RequestMapping("/positions/edit")
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
    }*/


}
