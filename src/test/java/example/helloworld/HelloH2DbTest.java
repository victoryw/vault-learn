package example.helloworld;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles(profiles = "test")
public class HelloH2DbTest extends HelloWordTest {


    @Test
    public void should_get_the_key_stored_in_the_db() throws Exception {
        List<TestTableEntity> keys = GetAllKeys();
        assertEquals(keys.size(), 0);
    }

    @Test
    public void should_save_new_key_to_db() throws Exception {
        String thePasswordStoreInTheVault = "H@rdT0Gu3ss";
        createKey(thePasswordStoreInTheVault);
        List<TestTableEntity> entities = GetAllKeys();
        assertEquals(entities.size(), 1);
        assertEquals(entities.get(0).testId, thePasswordStoreInTheVault);
    }

}
