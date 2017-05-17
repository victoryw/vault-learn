package example.helloworld;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestTableEntityRepo extends JpaRepository<TestTableEntity, String> {
    
}
