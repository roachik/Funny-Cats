package funny.controllers;

import funny.Base;
import funny.HibernateSessionFactory;
import funny.entity.*;
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
        Department d = ModelDepartments.getDepartment(Integer.parseInt(dep));
        Position p = ModelPositions.getPosition(Integer.parseInt(pos));
        ArrayList<String> breads = new ArrayList<String>();
        breads.add("<a href=\"/\">Главная</a>");
        breads.add("<a href=\"/departments\">Отделы</a>");
        breads.add("<a href=\"/departments?id="+dep+"\">"+d.getName()+"</a>");
        breads.add("<a href=\"/schedules?id="+dep+"\">Ставки</a>");
        breads.add("<a href=\"/employersofstaffs?dep="+dep+"&pos="+pos+"\">"+p.getName()+"</a>");
        model.addAttribute("breadcrumbs",getBreadcrumbs(breads));
        List<EmployersOfStaffs> list = ModelEmployers.getEmployersOfStaffs(Integer.parseInt(dep),Integer.parseInt(pos));
        model.addAttribute("table", list);
        Employer e = (Employer) getSession().getAttribute("emp");
        if((ModelMain.getRole(e.getEmployerId())==2)&&(ModelMain.getRoleDep(Integer.parseInt(dep), e.getEmployerId())==-1)) {
            model.addAttribute("role", 0);
        } else {
            model.addAttribute("role", ModelMain.getRole(e.getEmployerId()));
        }
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
        putModel(model);
        Department d = ModelDepartments.getDepartment(Integer.parseInt(dep));
        Position p = ModelPositions.getPosition(Integer.parseInt(pos));
        ArrayList<String> breads = new ArrayList<String>();
        breads.add("<a href=\"/\">Главная</a>");
        breads.add("<a href=\"/departments\">Отделы</a>");
        breads.add("<a href=\"/departments?id="+dep+"\">"+d.getName()+"</a>");
        breads.add("<a href=\"/schedules?id="+dep+"\">Ставки</a>");
        breads.add("<a href=\"/employersofstaffs?dep="+dep+"&pos="+pos+"\">"+p.getName()+"</a>");
        breads.add("<a href=\"/employersofstaffs/add?dep="+dep+"&pos="+pos+"\">Новая запись</a>");
        model.addAttribute("breadcrumbs",getBreadcrumbs(breads));
        model.addAttribute("Emps", ModelEmployers.getEmployers());
        return "add_employerofstaff";
    }

    @RequestMapping("/employersofstaffs/edit")
    public String employersofstaffedit(Model model, @RequestParam(value="part",required = false) String part,
                                       @RequestParam(value="id",required = true) String id,
                                       @RequestParam(value="active",required = false) String active) throws SQLException {

        putModel(model);
        EmployersOfStaffs e = ModelEmployers.getEmployerOfStaff(Integer.parseInt(id));
        ArrayList<String> breads = new ArrayList<String>();
        breads.add("<a href=\"/\">Главная</a>");
        breads.add("<a href=\"/departments\">Отделы</a>");
        breads.add("<a href=\"/departments?id="+e.getDepartment().getDepartmentId()+"\">"+e.getDepartment().getName()+"</a>");
        breads.add("<a href=\"/schedules?id="+e.getDepartment().getDepartmentId()+"\">Ставки</a>");
        breads.add("<a href=\"/employersofstaffs?dep="+e.getDepartment().getDepartmentId()+"&pos="+e.getPosition().getPositionId()+"\">"+e.getPosition().getName()+"</a>");
        model.addAttribute("breadcrumbs",getBreadcrumbs(breads));
        model.addAttribute("info", e);
        Employer em = (Employer) getSession().getAttribute("emp");

        if(part != null){
            System.out.println(part);
            if(active != null) {
                ModelEmployers.updateStaff(Integer.parseInt(id), Double.parseDouble(part), Integer.parseInt(active));
            } else {
                ModelEmployers.updateStaff(Integer.parseInt(id), Double.parseDouble(part), e.getIsActive());

            }
        }

        if((ModelMain.getRole(em.getEmployerId())==2)&&(ModelMain.getRoleDep(e.getDepartment().getDepartmentId(), em.getEmployerId())==-1)) {
            model.addAttribute("role", 0);
        } else {
            model.addAttribute("role", ModelMain.getRole(em.getEmployerId()));
        }
        if(part!=null){
            return "redirect:/";
        } else {
            return "edit_employerofstaff";
        }
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
        putModel(model);
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