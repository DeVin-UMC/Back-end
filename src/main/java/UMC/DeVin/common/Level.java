package UMC.DeVin.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 스터디 / 프로젝트 레벨 관련 enum 클래스입니다.
 */
public enum Level {
    /**
     * 초급
     */
    BEGINNER("beginner"),
    /**
     * 중급
     */
    MIDDLE("middle"),
    /**
     * 고급
     */
    ADVANCED("advanced");

    @Getter
    private final String value;

    Level(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Level from(String value) {
        for (Level level : Level.values()) {
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
