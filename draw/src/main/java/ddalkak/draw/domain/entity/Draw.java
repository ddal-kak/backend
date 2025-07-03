package ddalkak.draw.domain.entity;

import ddalkak.draw.domain.DrawResult;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;

@Entity
public class Draw extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long memberId;
    private long prizeId;
    private String prizeName;
    @Enumerated(EnumType.STRING)
    private DrawResult result;

    public Draw() {
    }

    @Builder(access = AccessLevel.PRIVATE)
    private Draw(long memberId, long prizeId, String prizeName, DrawResult result) {
        this.memberId = memberId;
        this.prizeId = prizeId;
        this.prizeName = prizeName;
        this.result = result;
    }

    public static Draw createWinPendingDraw(final long memberId, final long prizeId, final String prizeName) {
        return Draw.builder()
                .memberId(memberId)
                .prizeId(prizeId)
                .prizeName(prizeName)
                .result(DrawResult.PENDING)
                .build();
    }

    public static Draw createFailureDraw(final long memberId, final long prizeId, final String prizeName) {
        return Draw.builder()
                .memberId(memberId)
                .prizeId(prizeId)
                .prizeName(prizeName)
                .result(DrawResult.LOSE)
                .build();
    }

    public boolean isWinPending() {
        if (this.result == DrawResult.PENDING) {
            return true;
        }
        return false;
    }
}
