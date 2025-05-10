package service;

import dao.UserDao;
import lombok.RequiredArgsConstructor;
import model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private final BCryptPasswordEncoder passwordEncoder;

    public User create(String login, CharSequence password) {
        return userDao.save(login, passwordEncoder.encode(password));
    }

    public Optional<User> getByCredentials(String login, CharSequence password) {
        return userDao.getAll().stream()
                .filter(user -> user.getName().equals(login) && passwordEncoder.matches(password, user.getPassword()))
                .findAny();
    }
}
