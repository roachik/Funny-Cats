package funny.controllers;

import funny.Base;
import funny.DB;
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
public class Index extends Base {

    @RequestMapping("/")
    public String index(Model model) {
        HttpSession session = getSession();
        putModel(model);
        return (session.getAttribute("auth")!=null)?"redirect:/departments":"login";
    }
}
