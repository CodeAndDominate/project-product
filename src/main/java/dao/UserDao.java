package dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import model.User;

import java.nio.file.Paths;
import java.util.List;

@RequiredArgsConstructor
public class UserDao {
    private final String filePath = "path-to-your/users.json";
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public User save(String name, String password) {
        List<User> users = getAll();
        users.forEach(user -> {
            if (user.getName().equals(name)) {
                throw new UserAlreadyExistsException();
            }
        });
        int generatedId = users.isEmpty() ? 0 : users.get(users.size() - 1).getId() + 1;
        User user = new User(generatedId, name, password);
        users.add(user);
        objectMapper.writeValue(Paths.get(filePath).toFile(), users);
        return user;
    }

    @SneakyThrows
    public List<User> getAll() {
        return objectMapper.readValue(Paths.get(filePath).toFile(), new TypeReference<>() {});
    }
}
