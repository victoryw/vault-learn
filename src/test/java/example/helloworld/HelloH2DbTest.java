package example.helloworld;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.Assert.assertEquals;


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
