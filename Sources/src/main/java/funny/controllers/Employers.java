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
        model.addAttribute("Emps", ModelEmployers.getEmployers());
        return "add_employerofstaff";
    }

    @RequestMapping("/employersofstaffs/edit")
    public String employersofstaffedit(Model model, @RequestParam(value="part",required = false) String part,
                                       @RequestParam(value="id",required = true) String id,
                                       @RequestParam(value="active",required = false) String active) throws SQLException {
       System.out.println(Integer.parseInt(id));
       // System.out.println(part);

       // System.out.println(Integer.parseInt(active));
        putModel(model);
        EmployersOfStaffs e = ModelEmployers.getEmployerOfStaff(Integer.parseInt(id));
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
        List<Employer> list = ModelEmployers.getEmployers();
        model.addAttribute("table", list);
        Employer e = (Employer) getSession().getAttribute("emp");
        model.addAttribute("role", ModelMain.getRole(e.getEmployerId()));
        return "employers";
    }

    @RequestMapping("/employers/delete")
    public String delete(Model model,@RequestParam(value="id",required = true) String id)
            throws SQLException {
        ModelSchedules.deleteById(Employer.class, Integer.parseInt(id));
        return "redirect:/";
    }

    @RequestMapping("/employers/add")
    public String add(Model model,@RequestParam(value="name",required = false) String name) throws SQLException {
        if(name!=null){
            ModelEmployers.addEmployer(name);
            return "redirect:/employers";
        }
        return "add_employer";
    }

    @RequestMapping("/employers/edit")
    public String edit(Model model, @RequestParam(value="name",required = false) String name,@RequestParam(value="id",required = true) String id) throws SQLException {
        if(name != null){
            ModelEmployers.updateEmployer(Integer.parseInt(id),name);
        }
        putModel(model);
        model.addAttribute("info", ModelEmployers.getEmployer(Integer.parseInt(id)));
        return "edit_employer";
    }


}