package top.toobee.spring;

import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogFile;
import org.springframework.boot.logging.LoggingInitializationContext;
import org.springframework.boot.logging.logback.LogbackLoggingSystem;

public final class TooBeeLoggingSystem extends LogbackLoggingSystem {
    public TooBeeLoggingSystem(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    protected void loadDefaults(LoggingInitializationContext initializationContext, LogFile logFile) {
        final var context = (LoggerContext) LoggerFactory.getILoggerFactory();
        new LogbackConfigurator(
                Integer.parseInt(System.getProperty("logging.periods", "1")),
                Integer.parseInt(System.getProperty("logging.max_size_kb", "1024")),
                context
        ).configure0();
        context.start();
    }
}
