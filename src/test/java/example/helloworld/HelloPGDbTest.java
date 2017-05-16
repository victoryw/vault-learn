package example.helloworld;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class HelloPGDbTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${password}")
    String password;

    @Before
    public void baseBefore() {
        this.mockMvc =
                MockMvcBuilders.
                        webAppContextSetup(this.wac).
                        build();
    }

    @Test
    public void should_get_the_key_stored_in_the_db() throws Exception {
        List<TestTableEntity> keys = GetAllKeys();
        assertEquals(keys.size(), 1);
        assertEquals(keys.get(0).testId,"1");
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
        this.mockMvc.perform(delete(String.format("/admin/keys/%s",key)))
                .andExpect(status().isOk());
        List<TestTableEntity> entities = GetAllKeys();
        assertEquals(entities.size(), 1);
        assertEquals(entities.get(0).testId,"1");
    }

    private void createKey(String key) throws Exception {
        this.mockMvc.perform(post("/admin/keys")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(new TestTableEntity(key))))
                .andExpect(status().isCreated());
    }

    private List<TestTableEntity> GetAllKeys() throws Exception {
        MvcResult mvcResult = this.mockMvc
                .perform(get("/admin/keys"))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                new TypeReference<List<TestTableEntity>>(){});
    }


}
