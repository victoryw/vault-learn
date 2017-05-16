package example.helloworld;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/keys")
public class HelloPGDbController {

    private TestTableEntityRepo testTableEntityRepo;

    public HelloPGDbController(TestTableEntityRepo testTableEntityRepo) {
        this.testTableEntityRepo = testTableEntityRepo;
    }

    @GetMapping(value = "")
    public List<TestTableEntity> list() {
        List<TestTableEntity> entities = testTableEntityRepo.findAll();
        return entities;
    }

}
