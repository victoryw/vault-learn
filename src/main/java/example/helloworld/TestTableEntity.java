package example.helloworld;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TestTableEntity {
    @Id
    public String testId;
}
