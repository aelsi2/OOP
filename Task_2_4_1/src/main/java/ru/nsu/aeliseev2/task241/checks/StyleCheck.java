package ru.nsu.aeliseev2.task241.checks;

import com.google.common.io.MoreFiles;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.xml.sax.InputSource;
import ru.nsu.aeliseev2.task241.model.TaskStatus;

/**
 * A task action that runs checkstyle on the project.
 */
public class StyleCheck implements TaskCheck {
    private final Configuration configuration;

    private static Configuration getDefaultConfiguration() {
        InputSource inputSource = new InputSource(
            StyleCheck.class.getResourceAsStream("/google_checks.xml")
        );
        try {
            return ConfigurationLoader.loadConfiguration(
                inputSource,
                new PropertiesExpander(System.getProperties()),
                ConfigurationLoader.IgnoredModulesOptions.EXECUTE
            );
        } catch (CheckstyleException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Initializes a new instance of {@code StyleCheck} using the default configuration.
     */
    public StyleCheck() {
        this(getDefaultConfiguration());
    }

    /**
     * Initializes a new instance of {@code StyleCheck}.
     *
     * @param configuration The configuration to use.
     */
    public StyleCheck(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean run(Path project, TaskStatus taskStatus) {
        try {
            Checker checker = new Checker();
            checker.setModuleClassLoader(Checker.class.getClassLoader());
            checker.configure(configuration);

            List<AuditEvent> events = new ArrayList<>();
            AuditListener listener = new AuditListener() {
                @Override
                public void auditStarted(AuditEvent event) {
                }

                @Override
                public void auditFinished(AuditEvent event) {
                }

                @Override
                public void fileStarted(AuditEvent event) {
                }

                @Override
                public void fileFinished(AuditEvent event) {
                }

                @Override
                public void addError(AuditEvent event) {
                    events.add(event);
                }

                @Override
                public void addException(AuditEvent event, Throwable throwable) {
                    events.add(event);
                }
            };
            checker.addListener(listener);
            checker.addListener(new DefaultLogger(System.err, Checker.OutputStreamOptions.NONE));

            System.err.println("Running checkstyle on " + project.toString());
            List<File> files;
            try (Stream<Path> fileStream = java.nio.file.Files.walk(project)) {
                files = fileStream
                    .filter(file -> "java".equals(MoreFiles.getFileExtension(file)))
                    .map(Path::toFile)
                    .toList();
            }
            checker.process(files);
            taskStatus.stylePassed = events.isEmpty();
            return taskStatus.stylePassed;
        } catch (Exception e) {
            System.err.println("Checkstyle error:\n" + e);
            return false;
        }
    }
}
