package bingo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @author chanwook
 */
@Controller
public class EntryController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/entry", method = RequestMethod.GET)
    public String entryView(HttpSession session, ModelMap model) {

        if (UserHandler.isEntered(session)) {
            return "redirect:/square";
        } else {
            return "entry";
        }
    }

    @RequestMapping(value = "/entry", method = RequestMethod.POST)
    public String entry(HttpSession session, @RequestParam("userId") String userId) {

        final User user = new User(userId);

        // TODO 함수 합치기
        UserHandler.enterUser(session, user);
        userRepository.addUser(user);

        messagingTemplate.convertAndSend("/topic/user/add", user);

        return "redirect:/square";
    }
}
