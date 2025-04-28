package ddalkak.member.service.event;

import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

@Component
public class SnowFlakeIdGenerator implements UniqueIdGenerator{
    private static final long EPOCH = 1735689600000L; // 2025-01-01 UTC
    private static final int WORKER_BITS = 10;
    private static final int SEQ_BITS = 12;
    private static final int MAX_SEQ = 4095;

    private long machineId;
    private long lastMillis = -1L;
    private long sequence = 0L;

    public SnowFlakeIdGenerator() {
        this.machineId = resolveMachineId();
    }

    /**
     * 64비트 SnowFlake 스타일의 Id를 생성 <br>
     * [timestamp(41bits)][machineId(10bits)][sequence(12bits)] <br>
     * 같은 timestamp, 같은 호스트 머신에서 밀리초당 최대 4095개의 id 생성 가능
     * @return uniqueId
     */
    @Override
    public synchronized Long generate() {
        long now = System.currentTimeMillis();
        if (now < lastMillis) {
            throw new IllegalStateException("Clock move backwards, refuse generating Id");
        }
        if (now == lastMillis) {
            sequence = (sequence + 1) & MAX_SEQ;
            if (sequence == 0) { // 오버플로우 발생, 다음 ms까지 대기
                while (now == lastMillis) {
                    now = System.currentTimeMillis();
                }
            }
        } else {
            sequence = 0;
        }
        lastMillis = now;
        return ((now - EPOCH) << WORKER_BITS + SEQ_BITS) | (machineId << SEQ_BITS) | sequence;
    }

    private long resolveMachineId()  {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new IllegalStateException("UnknownHost, refuse generating eventId");
        }
        byte[] ip = address.getAddress();
        int rawHash = Arrays.hashCode(ip);
        return rawHash & 0x3FF; // 하위 10bits만 보존 (0x3FF = 1111111111)
    }
}
