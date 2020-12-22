package engine.models.userdomain;


import java.util.Optional;

public interface UserDao {
    Optional<User> selectByEmail(String email);
    User save(User user);
}