package jskang.springboot.multitenant.api;

import java.util.List;
import jskang.springboot.multitenant.database.repository.UserRepository;
import jskang.springboot.multitenant.database.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SampleService {

    private final UserRepository userRepository;

    @Autowired
    public SampleService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Users> getUser() {
        return this.userRepository.findAll();
    }
}
