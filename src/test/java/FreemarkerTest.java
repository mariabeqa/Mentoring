import freemarker.Freemarker;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FreemarkerTest {

    public static final String TEMPLATE_NAME = "example";

    @Test
    public void testFreemarker() throws IOException {
        Map<String, Object> freemarkerModel = new HashMap<>();

        Freemarker freemarker = new Freemarker();
        File xml = freemarker.onData(freemarkerModel)
                .tmpDir(Files.createTempDirectory(UUID.randomUUID().toString()).toFile())
                .withTemplate(TEMPLATE_NAME)
                .addParam("to", "Maria")
                .addParam("from", "Mur")
                .addParam("title", "Hello")
                .addParam("text", "Hey now brown cow")
                .create(TEMPLATE_NAME);
    }

}
