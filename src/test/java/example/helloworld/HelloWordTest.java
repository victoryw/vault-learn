package example.helloworld;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public abstract class HelloWordTest {
    @Autowired
    private WebApplicationContext wac;
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void baseBefore() {
        this.mockMvc =
                MockMvcBuilders.
                        webAppContextSetup(this.wac).
                        build();
    }

    List<TestTableEntity> GetAllKeys() throws Exception {
        MvcResult mvcResult = this.mockMvc
                .perform(get("/admin/keys"))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                new TypeReference<List<TestTableEntity>>(){});
    }

    void createKey(String key) throws Exception {
        this.mockMvc.perform(post("/admin/keys")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(new TestTableEntity(key))))
                .andExpect(status().isCreated());
    }
}
