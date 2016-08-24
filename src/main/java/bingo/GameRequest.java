package bingo;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author chanwook
 */
@Getter
@ToString
public class GameRequest implements Serializable {
    private final String requestUserId;
    private final String opponentUserId;

    public GameRequest(String requestUserId, String opponentUserId) {
        this.requestUserId = requestUserId;
        this.opponentUserId = opponentUserId;
    }
}
