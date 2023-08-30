package jskang.springboot.multitenant.api;

import java.util.LinkedList;
import java.util.List;
import jskang.springboot.multitenant.database.DatabaseContextHolder;
import jskang.springboot.multitenant.database.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "user")
public class SampleController {

    private SampleService sampleService;

    @Autowired
    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @GetMapping
    public Mono<ResponseEntity> getUsers() {
        String company = DatabaseContextHolder.getCustomerContext().getName();
        System.out.println("Connected : " +company);

        List<String> username = new LinkedList<>();
        username.add("권호중");
        username.add("강준성");
        username.add("유승환");
        username.add("황다영");
        username.add("최준혁");
        System.out.println(username);

        List<Users> users = this.sampleService.getUser();
        return Mono.just(ResponseEntity.ok(users));
    }
}
