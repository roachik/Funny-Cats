package funny.controllers;

import funny.Base;
import funny.DB;
import funny.entity.Users;
import funny.models.ModelMain;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

/**
 * Created by Tony on 05.03.2016.
 */
@Controller
public class Login extends Base {

    @RequestMapping("/login")
    public String login(Model model, @RequestParam(value="name",required = false) String login, @RequestParam(value="password",required = false) String password) throws SQLException {
        HttpSession session = getSession();
        Users u = ModelMain.checkUser(login,password);
        if(u != null) {
            session.setAttribute("auth", true);
            session.setAttribute("name", u.getName());
            session.setAttribute("emp", u.getEmployer());
            //session.setAttribute("role", u.getEmployer().getRole());
            return "redirect:/";
        }
        else
        {
            session.invalidate();
            return "login";
        }

    }
}
