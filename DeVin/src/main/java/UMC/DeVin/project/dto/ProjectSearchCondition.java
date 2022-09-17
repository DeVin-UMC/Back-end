package UMC.DeVin.project.dto;

import lombok.Data;

/*
* 페이징 검색 조건
* */
@Data
public class ProjectSearchCondition {
    // 분류
    private String platform;

    // 지역
    private String region;

    // 난이도
    private String level;
}
