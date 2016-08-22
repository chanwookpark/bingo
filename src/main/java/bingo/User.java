package bingo;

import lombok.Getter;

import javax.servlet.http.HttpSession;

/**
 * @author chanwook
 */
@Getter
public class User {
    private static final String USER_SESSION_KEY = "_bingo_user";

    private final String userId;

    public User(String userId) {
        this.userId = userId;
    }

    public static User findUser(HttpSession session) {
        final Object user = session.getAttribute(USER_SESSION_KEY);

        if (user != null && user instanceof User) {
            return (User) user;
        } else {
            User createdUser = createUser(session);
            session.setAttribute(USER_SESSION_KEY, createdUser);
            return createdUser;
        }
    }

    public static User createUser(HttpSession session) {
        //TODO id 발행 로직 개선
        String userId = "사용자-" + System.nanoTime();
        return new User(userId);
    }
}
