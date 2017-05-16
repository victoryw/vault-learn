package example.helloworld;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Before
    public void baseBefore() {
        this.mockMvc =
                MockMvcBuilders.
                        webAppContextSetup(this.wac).
                        build();
    }

    @Test
    public void should_get_thRe_key_stored_in_the_db() throws Exception {
        MvcResult mvcResult = this.mockMvc
                .perform(get("/admin/keys"))
                .andExpect(status().isOk())
                .andReturn();

        List<TestTableEntity> keys = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                new TypeReference<List<TestTableEntity>>(){});
        assertEquals(keys.size(), 1);
        assertEquals(keys.get(0).testId,"1");
    }
}
