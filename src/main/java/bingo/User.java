package bingo;

import lombok.Getter;
import lombok.ToString;

import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * @author chanwook
 */
@Getter
@ToString
public class User implements Serializable {

    private static final String USER_SESSION_KEY = "_bingo_user";

    private String userId;

    public User(String userId) {
        this.userId = userId;
    }

    public static User findUser(HttpSession session) {
        final Object user = session.getAttribute(USER_SESSION_KEY);

        if (user != null && user instanceof User) {
            return (User) user;
        } else {
            return null;
        }
    }

    public static boolean isEntered(HttpSession session) {
        return session.getAttribute(USER_SESSION_KEY) != null;
    }

    public static void enterUser(HttpSession session, User user) {
        session.setAttribute(USER_SESSION_KEY, user);
    }
}
