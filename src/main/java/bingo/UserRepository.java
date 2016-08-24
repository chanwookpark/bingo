package bingo;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chanwook
 */
@Repository
public class UserRepository {

    private Map<String, User> userMap = new HashMap<>();

    public Collection<User> getAllUser() {
        return userMap.values();
    }

    public void addUser(User user) {
        this.userMap.put(user.getUserId(), user);
    }
}
