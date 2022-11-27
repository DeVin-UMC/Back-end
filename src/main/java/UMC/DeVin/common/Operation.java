package UMC.DeVin.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 스터디 / 프로젝트 운영 방식 관련 enum 클래스입니다.
 */
public enum Operation {
    /**
     * 온라인
     */
    ONLINE("online"),
    /**
     * 오프라인
     */
    OFFLINE("offline"),
    /**
     * 온라인 오프라인 병행
     */
    BOTH("both");

    @Getter
    private final String value;

    Operation(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Operation from(String value) {
        for (Operation level : Operation.values()) {
            if (level.getValue().equals(value)) {
                return level;
            }
        }
        return null;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
