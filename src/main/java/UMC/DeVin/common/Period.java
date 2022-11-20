package UMC.DeVin.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 스터디 / 프로젝트 기간 관련 enum 클래스입니다.
 */
public enum Period {
    /**
     * 1개월 미만
     */
    ONE_MONTH("one_month"),
    /**
     * 2개월
     */
    TWO_MONTH("two_month"),
    /**
     * 3개월
     */
    THREE_MONTH("three_month"),
    /**
     * 4개월
     */
    FOUR_MONTH("four_month"),
    /**
     * 5개월
     */
    FIVE_MONTH("five_month"),
    /**
     * 6개월
     */
    SIX_MONTH("six_month"),
    /**
     * 6개월 ~ 1년
     */
    OVER_SIX_MONTH("over_six_month"),
    /**
     * 1년 이상
     */
    ONE_YEAR("one_year"),
    ;

    @Getter
    private final String value;

    Period(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Period from(String value) {
        for (Period region : Period.values()) {
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
