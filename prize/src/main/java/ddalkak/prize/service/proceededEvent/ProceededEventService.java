package ddalkak.prize.service.proceededEvent;



public interface ProceededEventService {
    boolean isProceededEvent(Long eventId);

    void save(Long eventId);
}
