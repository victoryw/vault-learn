package example.helloworld;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping(value = "")
    public ResponseEntity create(@RequestBody TestTableEntity entity) {
        testTableEntityRepo.save(entity);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{key}")
    public void list(@PathVariable String key) {
        testTableEntityRepo.delete(key);
    }

}
