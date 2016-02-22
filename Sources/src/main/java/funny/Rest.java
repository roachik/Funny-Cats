package funny;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

/**
 * Created by Tony on 21.02.2016.
 */
@Controller
public class Rest extends Base{

    @RequestMapping("/")
    public String index(Model model) {
        HttpSession session = getSession();
        model.addAttribute("name",session.getAttribute("name"));
        return (session.getAttribute("auth")!=null)?"index":"login";
    }

    @RequestMapping("/login")
    public String login(Model model,@RequestParam(value="name",required = false) String login,@RequestParam(value="password",required = false) String password) throws SQLException {
        HttpSession session = getSession();
        if(DB.getInstance().checkUser(login,password)) {
            session.setAttribute("auth", true);
            session.setAttribute("name", login);
            return "redirect:/";
        }
        else return "login";

    }

}
