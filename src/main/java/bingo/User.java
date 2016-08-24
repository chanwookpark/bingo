package bingo;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author chanwook
 */
@Getter
@ToString
public class User implements Serializable {

    private String userId;

    public User(String userId) {
        this.userId = userId;
    }
}
