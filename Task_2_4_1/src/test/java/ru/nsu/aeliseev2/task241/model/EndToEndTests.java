package ru.nsu.aeliseev2.task241.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task241.Application;

class EndToEndTests {
    @Test
    void test() throws IOException {
        URL tasks = EndToEndTests.class.getResource("/e2e/check.groovy");
        Assertions.assertNotNull(tasks);
        String filePath = tasks.getFile();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Application.main(new String[]{filePath});
        Assertions.assertNotEquals(0, outputStream.size());
        String projectBaseDir = System.getProperty("user.dir");
        Path reportPath = Path.of(projectBaseDir, "build", "reports", "check.html");
        Files.write(reportPath, outputStream.toByteArray());
        System.err.printf("Test report written to %s\n", reportPath.toUri());
    }
}
