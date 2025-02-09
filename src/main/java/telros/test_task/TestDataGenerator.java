package telros.test_task;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import telros.test_task.model.User;
import telros.test_task.repository.UserRepository;


/**
 * Класс для проверки реализации авторизации
 * Создаётся пользователь admin с аналогичным паролем
 */
@Component
public class TestDataGenerator {

    @Autowired
    private UserRepository userRepository;
    static Long id = 1L;

    public TestDataGenerator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void generateUsers() {
        saveUser(userRepository, "admin");
    }

    public void saveUser(UserRepository userRepository, String login) {
        User user = new User();
        user.setId(id++);
        user.setLogin(login);
        user.setPassword(login);
        user.setRole(login);
        userRepository.save(user);
    }
}
