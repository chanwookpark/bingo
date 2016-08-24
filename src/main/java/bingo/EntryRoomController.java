package bingo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chanwook
 */
@Controller
public class EntryRoomController {

    private final Logger logger = LoggerFactory.getLogger(EntryRoomController.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private Map<String, User> userMap = new HashMap<>();

    @RequestMapping("/entry.v")
    public String entry(HttpSession session, ModelMap model) {
        final User loginUser = User.findUser(session);

        if (!userMap.containsKey(loginUser.getUserId())) {
            // 새로 접속한 사용자를 현재 접속자 목록에 추가하고 사용자에게 알리기 위한 메시지
            logger.info("새로운 사용자 입장 ({})", loginUser);
            userMap.put(loginUser.getUserId(), loginUser);

            messagingTemplate.convertAndSend("/topic/user/add", loginUser);
        } else {
            logger.info("이미 등록된 사용자입니다 ({})", loginUser);
        }

        model.put("loginUser", loginUser);
        model.put("users", toList());
        return "entry";
    }

    private List<User> toList() {
        List list = new ArrayList();
        for (User u : userMap.values()) {
            list.add(u);
        }
        return list;
    }
}
