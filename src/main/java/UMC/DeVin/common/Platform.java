package UMC.DeVin.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 스터디 / 프로젝트 분류 관련 enum 클래스입니다.
 */
public enum Platform {
    /**
     * 미정
     */
    NONE("none"),
    /**
     * 반응형 웹
     */
    WEB("web"),
    /**
     * 안드로이드 앱
     */
    ANDROID("android"),
    /**
     * IOS 앱
     */
    IOS("ios"),
    /**
     * 데스크탑 프로그램
     */
    DESKTOP("desktop"),
    /**
     * 기타
     */
    ETC("etc");

    @Getter
    private final String value;

    Platform(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Platform from(String value) {
        for (Platform level : Platform.values()) {
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
