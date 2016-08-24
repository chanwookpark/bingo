package bingo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author chanwook
 */
@Controller
public class GameController {

    private final Logger logger = LoggerFactory.getLogger(GameController.class);

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @RequestMapping("/game")
    public String gameRoom(HttpSession session,
                           ModelMap model,
                           @RequestParam("opponentUserId") String opponentUserId) {
        if (!UserHandler.isEntered(session)) {
            return "redirect:/entry";
        }
        final User loginUser = UserHandler.getUser(session);
        final User opponentUser = new User(opponentUserId);
        UserHandler.setOpponentUser(session, opponentUser);


        model.put("loginUser", loginUser);
        model.put("opponentUser", opponentUser);
        return "game";
    }

    @RequestMapping("/game/request")
    @ResponseBody
    public HttpStatus requestToStartGame(HttpSession session,
                                         @RequestParam("opponent") String opponentUserId) {
        final User user = UserHandler.getUser(session);

        // TODO /user/{username}/queue/position-updates 이렇게 해야 함.
        messagingTemplate.convertAndSend("/queue/game/request-to-" + opponentUserId, new GameRequest(user.getUserId(), opponentUserId));

        logger.info("[게임 요청]요청보낸사람(본인): {}, 요청받는사람(상대방): {}", user.getUserId(), opponentUserId);

        return HttpStatus.OK;
    }

    @RequestMapping("/game/approval")
    public String approvalGameRequest(HttpSession session,
                                      @RequestParam("requestUserId") String requestUserId) {
        final User user = UserHandler.getUser(session);

        messagingTemplate.convertAndSend("/queue/game/approval-for-" + requestUserId, user.getUserId());
        logger.info("[게임 요청 승인]  승인자(본인):" + user.getUserId() + ", 요청자(상대방): " + requestUserId);
        return "redirect:/game?opponentUserId=" + requestUserId;
    }
}
