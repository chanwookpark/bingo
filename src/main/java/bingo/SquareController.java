package bingo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Collection;

/**
 * @author chanwook
 */
@Controller
public class SquareController {

    private final Logger logger = LoggerFactory.getLogger(SquareController.class);

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/square")
    public String entry(HttpSession session, ModelMap model) {
        if (!UserHandler.isEntered(session)) {
            return "redirect:/entry";
        }
        final User loginUser = UserHandler.getUser(session);
        final Collection<User> allUser = userRepository.getAllUserExceptMe(loginUser);

        logger.debug("로그인한 사용자 수 - {}", allUser.size());

        model.put("loginUser", loginUser);
        model.put("users", allUser);
        return "square";
    }
}
