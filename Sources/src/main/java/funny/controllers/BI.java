package funny.controllers;

import funny.Base;
import funny.entity.Employer;
import funny.models.ModelMain;
import funny.models.ModelTasks;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Tony on 05.03.2016.
 */
@Controller
public class BI extends Base {

    @RequestMapping("/bi")
    public String tasks(Model model) throws SQLException {
        putModel(model);
        Employer e = (Employer) getSession().getAttribute("emp");
        ArrayList<String> breads = new ArrayList<String>();
        if(ModelMain.isInRole(e.getEmployerId(),2)){
        breads.add("<a href=\"/\">Главная</a>");
        breads.add("<a href=\"/tasks\">Бизнес-аналитика</a>");} else {
            breads.add("<th >Ошибка доступа</th>");
        }
        model.addAttribute("breadcrumbs",getBreadcrumbs(breads));


        model.addAttribute("userid",e.getEmployerId());


        model.addAttribute("role", ModelMain.getRole(e.getEmployerId()));
        return "bi";
    }



}