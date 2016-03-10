package funny.controllers;

import funny.Base;
import funny.DB;
import funny.entity.Department;
import funny.entity.Position;
import funny.entity.Schedule;
import funny.models.ModelDepartments;
import funny.models.ModelPositions;
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
    public String positions(Model model) throws SQLException {
        putModel(model);
        //model.addAttribute("breadcrumbs",getBreadcrumbs(setBreadcrumbs()));
        List<Position> list = ModelPositions.getPositions();
        model.addAttribute("table", list);
        return "positions";
    }

    @RequestMapping("/positions/edit")
    public String edit(Model model, @RequestParam(value="name",required = false) String newname,@RequestParam(value="id",required = true) String id) throws SQLException {
        if(newname != null){
            ModelPositions.updatePosition(Integer.parseInt(id),newname);
        }
        putModel(model);
        model.addAttribute("breadcrumbs",getBreadcrumbs(setBreadcrumbs()));
        model.addAttribute("info", ModelPositions.getPosition(Integer.parseInt(id)));
        return "edit_positions";
    }

    @RequestMapping("/positions/delete")
    public String delete(Model model,@RequestParam(value="id",required = true) String id) throws SQLException {
        ModelPositions.deleteById(Position.class, Integer.parseInt(id));
        return "redirect:/positions";
    }

    @RequestMapping("/positions/add")
    public String add(Model model,
                      @RequestParam(value="name",required = false) String name) throws SQLException {
        if(name != null){

            ModelPositions.add(name);
            return "redirect:/positions";
        }
        return "add_position";
    }

    private ArrayList<String> setBreadcrumbs()
    {
        ArrayList<String> breads = new ArrayList<String>();
        breads.add("<a href=\"/\">Главная</a>");
        breads.add("<a href=\"/positions\">Должности</a>");
        return breads;
    }


}
