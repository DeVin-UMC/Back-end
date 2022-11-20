package UMC.DeVin.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 스터디 / 프로젝트 모집 분야 관련 enum 클래스입니다.
 */
public enum Recruitment {
    /**
     * UI/UX 디자인
     */
    SEOUL("seoul"),
    /**
     * 기획
     */
    SEJONG("sejong"),
    /**
     * PM
     */
    INCHEON("incheon"),
    /**
     * IOS 개발
     */
    DAEJEON("deajeon"),
    /**
     * 안드로이드 개발
     */
    GWANGJU("gwangju"),
    /**
     * 프론트엔드 개발
     */
    DAEGU("deagu"),
    /**
     * 울산
     */
    ULSAN("ulsan"),
    /**
     * 부산
     */
    BUSAN("busan"),
    /**
     * 경기
     */
    GYEONGGI("gyeonggi"),
    /**
     * 강원
     */
    GANGWON("gangwon"),
    /**
     * 충북
     */
    CHUNGBUK("chungbuk"),
    /**
     * 충남
     */
    CHUNGNAM("chungnam"),
    /**
     * 전북
     */
    JEONBUK("jeonbuk"),
    /**
     * 전남
     */
    JEONNAM("jeonnam"),
    /**
     * 경북
     */
    GYEONGBUK("gyeongbuk"),
    /**
     * 경남
     */
    GYEONGNAM("gyeongnam"),
    /**
     * 제주
     */
    JEJU("jeju");

    @Getter
    private final String value;

    Recruitment(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Recruitment from(String value) {
        for (Recruitment region : Recruitment.values()) {
            if (region.getValue().equals(value)) {
                return region;
            }
        }
        return null;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
