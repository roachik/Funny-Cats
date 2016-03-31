package funny.controllers;

import funny.Base;
import funny.HibernateSessionFactory;
import funny.entity.Employer;
import funny.entity.EmployersOfStaffs;
import funny.entity.Position;
import funny.entity.Schedule;
import funny.models.*;
import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tony on 10.03.2016.
 */
@Controller
public class Employers extends Base {

    @RequestMapping("/employersofstaffs")
    public String employersofstaff(Model model, @RequestParam(value="dep",required = true) String dep,
                                   @RequestParam(value="pos",required = true) String pos)
            throws SQLException {
        putModel(model);
        //model.addAttribute("breadcrumbs",breads);
        List<EmployersOfStaffs> list = ModelEmployers.getEmployersOfStaffs(Integer.parseInt(dep),Integer.parseInt(pos));
        model.addAttribute("table", list);
        Employer e = (Employer) getSession().getAttribute("emp");
        model.addAttribute("role", ModelMain.getRoleForDep(Integer.parseInt(dep),Integer.parseInt(pos),
                e.getEmployerId()));
        return "employersofstaffs";
    }

    @RequestMapping("/employersofstaffs/delete")
    public String employersofstaffdelete(Model model,@RequestParam(value="id",required = true) String id)
            throws SQLException {
        ModelEmployers.deleteById(EmployersOfStaffs.class, Integer.parseInt(id));
        return "redirect:/";
    }

    @RequestMapping("/employersofstaffs/add")
    public String employersofstaffadd(Model model,@RequestParam(value="part",required = false) String part,
                      @RequestParam(value="emp",required = false) String emp,
                      @RequestParam(value="pos",required = true) String pos,
                      @RequestParam(value="dep",required = true) String dep) throws SQLException {
        if(pos != null && emp != null && dep != null && part != null){
            ModelEmployers.addStaff(Integer.parseInt(dep),Integer.parseInt(pos),Integer.parseInt(emp),Double.parseDouble(part));
            return "redirect:/employersofstaffs/?dep="+dep+"&pos="+pos;
        }
        model.addAttribute("Emps", ModelEmployers.getEmployers());
        return "add_employerofstaff";
    }

    @RequestMapping("/employersofstaffs/edit")
    public String employersofstaffedit(Model model, @RequestParam(value="part",required = false) String part,
                                       @RequestParam(value="id",required = true) String id,
                                       @RequestParam(value="active",required = false) String active) throws SQLException {
        if(part != null){
            ModelEmployers.updateStaff(Integer.parseInt(id),Double.parseDouble(part),Integer.parseInt(active));
        }
        putModel(model);
        EmployersOfStaffs e = ModelEmployers.getEmployerOfStaff(Integer.parseInt(id));
        model.addAttribute("info", e);
        Employer em = (Employer) getSession().getAttribute("emp");
        model.addAttribute("role", ModelMain.getRoleForDep(e.getDepartment().getDepartmentId(),e.getPosition().getPositionId(),
                em.getEmployerId()));
        return "edit_employerofstaff";
    }


    @RequestMapping("/employers")
    public String employers(Model model)
            throws SQLException {
        putModel(model);
        ArrayList<String> breads = new ArrayList<String>();
        breads.add("<a href=\"/\">Главная</a>");
        breads.add("<a href=\"/employers\">Сотрудники</a>");
        model.addAttribute("breadcrumbs",getBreadcrumbs(breads));
        List<Employer> list = ModelEmployers.getEmployers();
        model.addAttribute("table", list);
        return "employers";
    }

    @RequestMapping("/employers/delete")
    public String delete(Model model,@RequestParam(value="id",required = true) String id)
            throws SQLException {
        ModelSchedules.deleteById(Employer.class, Integer.parseInt(id));
        return "redirect:/employers";
    }

    @RequestMapping("/employers/add")
    public String add(Model model,@RequestParam(value="name",required = false) String name) throws SQLException {
        if(name!=null){
            ModelEmployers.addEmployer(name);
            return "redirect:/employers";
        }
        ArrayList<String> breads = new ArrayList<String>();
        breads.add("<a href=\"/\">Главная</a>");
        breads.add("<a href=\"/employers\">Сотрудники</a>");
        breads.add("<a href=\"/employers/add\">Новый сотрудник</a>");
        model.addAttribute("breadcrumbs",getBreadcrumbs(breads));
        return "add_employer";
    }

    @RequestMapping("/employers/edit")
    public String edit(Model model, @RequestParam(value="name",required = false) String name,@RequestParam(value="id",required = true) String id) throws SQLException {
        if(name != null){
            ModelEmployers.updateEmployer(Integer.parseInt(id),name);
        }
        putModel(model);
        Employer e = ModelEmployers.getEmployer(Integer.parseInt(id));
        model.addAttribute("info", e);
        ArrayList<String> breads = new ArrayList<String>();
        breads.add("<a href=\"/\">Главная</a>");
        breads.add("<a href=\"/employers\">Сотрудники</a>");
        breads.add("<a href=\"/employers/edit?id="+id+"\">"+e.getName()+"</a>");
        model.addAttribute("breadcrumbs",getBreadcrumbs(breads));
        return "edit_employer";
    }


}
