package UMC.DeVin.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/*
* 필터링 검색 조건
* */
@Data
public class ProjectSearchCondition {

    // 분류
    @Schema(description = "분류", example="ios")
    private String platform;

    // 지역
    @Schema(description = "지역", example="seoul")
    private String region;

    // 난이도
    @Schema(description = "단계", example="beginner")
    private String level;

    // 난이도
    @Schema(description = "기간", example="two_month")
    private String period;

    // 난이도
    @Schema(description = "사용언어", example="react")
    private String language;
}
