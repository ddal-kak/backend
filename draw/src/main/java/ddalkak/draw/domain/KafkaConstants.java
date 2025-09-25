package ddalkak.draw.domain;

public final class KafkaConstants {
    public static final int MAX_RETRY_ATTEMPTS = 4;
    public static final int MAX_REDRIVE_COUNT = 4;
    public static final String REDRIVE_COUNT = "redrive-count";
    public static final String CAUSE_EXCEPTION = "kafka_exception-cause-fqcn";
    public static final String ERROR_MESSAGE = "kafka_exception-message";
}
