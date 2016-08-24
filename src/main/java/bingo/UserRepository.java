package bingo;

import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @author chanwook
 */
@Repository
public class UserRepository {

    private Map<String, User> userMap = new HashMap<>();

    public Collection<User> getAllUser() {
        return userMap.values();
    }

    public Collection<User> getAllUserExceptMe(User loginUser) {
        List<User> list = new ArrayList<>();
        for (User user : userMap.values()) {
            if (loginUser.getUserId().equals(user.getUserId())) {
                continue;
            }
            list.add(user);
        }
        return list;
    }

    public void addUser(User user) {
        this.userMap.put(user.getUserId(), user);
    }

}
