package UMC.DeVin.project.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PostProjectReqDto {

    private Long memberId;
    @NotNull(message = "프로젝트 제목을 입력해주세요 !")
    private String title;
    @NotNull(message = "프로젝트 설명을 입력해주세요 !")
    private String des;
    @NotNull(message = "프로젝트 레벨을 선택해주세요 !")
    private String programLevel;
    private String img;
    private List<PlatformDto> platformList;
    private List<RecruitmentDto> recruitmentList;
    private List<RegionDto> regionList;

}
