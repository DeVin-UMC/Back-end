package UMC.DeVin.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/*
* 페이징 검색 조건
* */
@Data
public class ProjectSearchCondition {

    // 분류
    @Schema(description = "플랫폼", example="web")
    private String platform;

    // 지역
    @Schema(description = "지역", example="seoul")
    private String region;

    // 난이도
    @Schema(description = "난이도", example="beginner")
    private String level;
}
