package funny;

import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tony on 22.02.2016.
 */
public class Base {

    protected static Integer USER_ROLE_USER = 0;
    protected static Integer USER_ROLE_MANAGER = 1;
    protected static Integer USER_ROLE_CHIEF = 2;

    protected HttpSession getSession(){
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true);
    }

    protected void putModel(Model model) {
        HttpSession session = getSession();
        model.addAttribute("name",session.getAttribute("name"));
        model.addAttribute("role",session.getAttribute("role"));
    }


    protected String getBreadcrumbs(ArrayList<String> breads)
    {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (String bread : breads) {
            i++;
            builder.append(bread);
            if(i < breads.size()) builder.append(" / ");
        }
        return builder.toString();
    }
}
