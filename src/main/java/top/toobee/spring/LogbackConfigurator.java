package top.toobee.spring;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.rolling.DefaultTimeBasedFileNamingAndTriggeringPolicy;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;

import java.time.Instant;

final class LogbackConfigurator extends DefaultTimeBasedFileNamingAndTriggeringPolicy<ILoggingEvent> {
    private final int periods;
    private final int maxLogSizeKb;

    LogbackConfigurator(int periods, int maxLogSizeKb, LoggerContext context) {
        this.periods = periods;
        this.maxLogSizeKb = maxLogSizeKb;
        this.context = context;
    }

    @Override
    protected long computeNextCheck(long timestamp) {
        return rc.getEndOfNextNthPeriod(Instant.ofEpochMilli(timestamp), periods).toEpochMilli();
    }

    void configure0() {
        addInfo("Setting up custom configuration.");

        final var e1 = new PatternLayoutEncoder();
        final var ca = new ConsoleAppender<ILoggingEvent>();
        e1.setPattern("%green(%d{MM-dd'T'HH:mm:ss.SSS}) %highlight(%-5level) [%thread] %cyan(%logger): %msg%n");
        e1.setContext(context);
        e1.setParent(ca);
        e1.start();
        ca.setContext(context);
        ca.setName("console");
        ca.setEncoder(e1);
        ca.start();

        final var fa = new RollingFileAppender<ILoggingEvent>();
        final var e2 = new PatternLayoutEncoder();
        final var pl = new SizeAndTimeBasedRollingPolicy<ILoggingEvent>();
        e2.setContext(context);
        e2.setPattern("%d{yyyy-MM-dd'T'HH:mm:ss.SSS} %-5level [%thread] \\(\\(%logger\\)\\) %msg%n");
        e2.setParent(fa);
        e2.start();
        pl.setContext(context);
        pl.setFileNamePattern("logs/%d{yyyy-MM-dd}.%i.log");
        pl.setTimeBasedFileNamingAndTriggeringPolicy(this);
        pl.setMaxFileSize(FileSize.valueOf(maxLogSizeKb + "KB"));
        pl.setParent(fa);
        pl.start();
        fa.setContext(context);
        fa.setAppend(true);
        fa.setName("file");
        fa.setFile("logs/latest.log");
        fa.setEncoder(e2);
        fa.setRollingPolicy(pl);
        fa.start();

        final var rl = ((LoggerContext) context).getLogger(Logger.ROOT_LOGGER_NAME);
        rl.detachAndStopAllAppenders();
        rl.addAppender(ca);
        rl.addAppender(fa);
        rl.setLevel(Level.INFO);
    }
}
