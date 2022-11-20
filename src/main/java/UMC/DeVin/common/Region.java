package UMC.DeVin.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 스터디 / 프로젝트 지역 관련 enum 클래스입니다.
 */
public enum Region {
    /**
     * 서울
     */
    SEOUL("seoul"),
    /**
     * 세종
     */
    SEJONG("sejong"),
    /**
     * 인천
     */
    INCHEON("incheon"),
    /**
     * 대전
     */
    DAEJEON("deajeon"),
    /**
     * 광주
     */
    GWANGJU("gwangju"),
    /**
     * 대구
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

    Region(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Region from(String value) {
        for (Region region : Region.values()) {
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
