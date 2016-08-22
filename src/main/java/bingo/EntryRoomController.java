package bingo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author chanwook
 */
@Controller
public class EntryRoomController {

    @RequestMapping("/entry")
    public String entry(HttpSession session, ModelMap model) {

        final User loginUser = User.findUser(session);
        model.put("loginUser", loginUser);
        return "entry";
    }
}
