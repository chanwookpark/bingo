package bingo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chanwook
 */
@Controller
public class GameController {

    private final Logger logger = LoggerFactory.getLogger(GameController.class);

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @RequestMapping("/game")
    public String mainGround() {
        return "game";
    }

    @RequestMapping("/game/request")
    @ResponseBody
    public HttpStatus requestToStartGame(@RequestParam("user") String requestUserId,
                                         @RequestParam("opponent") String opponentUserId) {

        // TODO /user/{username}/queue/position-updates 이렇게 해야 함.
        messagingTemplate.convertAndSend("/queue/game/request-" + opponentUserId, new GameRequest(requestUserId, opponentUserId));

        logger.info("[게임 요청]요청보낸사람: {}, 요청받는사람: {}", requestUserId, opponentUserId);

        return HttpStatus.OK;
    }
}
