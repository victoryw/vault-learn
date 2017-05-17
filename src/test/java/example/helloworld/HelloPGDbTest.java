package example.helloworld;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HelloPGDbTest extends HelloWordTest {

    @Value("${password}")
    String password;

    @Test
    public void should_get_the_key_stored_in_the_db() throws Exception {
        List<TestTableEntity> keys = GetAllKeys();
        assertEquals(keys.size(), 1);
        assertEquals(keys.get(0).testId, "1");
    }

    @Test
    public void should_save_new_key_to_db() throws Exception {
        createKey(password);
        List<TestTableEntity> entities = GetAllKeys();
        assertEquals(entities.size(), 2);
        List<TestTableEntity> sortedEntity = entities.stream()
                .sorted(comparing(entity -> entity.testId))
                .collect(Collectors.toList());
        String thePasswordStoreInTheVault = "H@rdT0Gu3ss";
        assertEquals(sortedEntity.get(1).testId, thePasswordStoreInTheVault);
    }

    @Test
    public void should_delete_key() throws Exception {
        String key = "3";
        createKey(key);
        this.mockMvc.perform(delete(String.format("/admin/keys/%s", key)))
                .andExpect(status().isOk());
        List<TestTableEntity> entities = GetAllKeys();
        assertEquals(entities.size(), 1);
        assertEquals(entities.get(0).testId,  "1");
    }
}
