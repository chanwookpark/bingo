package bingo;

import javax.servlet.http.HttpSession;

/**
 * @author chanwook
 */
public class UserHandler {

    private static final String LOGIN_USER_SESSION_KEY = "_login_user";
    public static final String OPPONENT_USER_SESSION_KEY = "_opponent_user";

    public static User getUser(HttpSession session) {
        final Object user = session.getAttribute(LOGIN_USER_SESSION_KEY);

        if (user != null && user instanceof User) {
            return (User) user;
        } else {
            return null;
        }
    }

    public static boolean isEntered(HttpSession session) {
        return session.getAttribute(LOGIN_USER_SESSION_KEY) != null;
    }

    public static void enterUser(HttpSession session, User user) {
        session.setAttribute(LOGIN_USER_SESSION_KEY, user);
    }

    public static void setOpponentUser(HttpSession session, User user) {
        session.setAttribute(OPPONENT_USER_SESSION_KEY, user);
    }
}
