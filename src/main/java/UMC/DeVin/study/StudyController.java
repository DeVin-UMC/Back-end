package UMC.DeVin.study;

import UMC.DeVin.common.base.BaseException;
import UMC.DeVin.common.base.BaseResponse;
import UMC.DeVin.common.base.BaseResponseStatus;
import UMC.DeVin.member.Member;
import UMC.DeVin.member.role.MemberRole;
import UMC.DeVin.project.dto.PostProjectReqDto;
import UMC.DeVin.project.dto.PostProjectResDto;
import UMC.DeVin.study.dto.StudyResDTO;
import UMC.DeVin.study.dto.StudySearchCondition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Study", description = "스터디 API")
@RestController
@AllArgsConstructor
public class StudyController {

    private final StudyService studyService;

//    @Operation(summary = "프로젝트 생성 요청", description = "프로젝트가 생성됩니다.")
//    @PostMapping("/study")
//    public BaseResponse<PostProjectResDto> createProject(@RequestPart @Valid PostProjectReqDto dto, @RequestPart MultipartFile file) throws BaseException {
//
//        // 로그인 유저
//        Member loginMember = oAuthLoginUserUtil.getLoginMemberWithContext();
//
//        // 게스트가 글쓰기에 접근한 경우
//        if (loginMember.getRole().equals(MemberRole.GUEST)) {
//            return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
//        }
//
//        PostStudyResDto postStudyResDto = studyService.createStudy(dto, loginMember ,file);
//        return new BaseResponse<>(postStudyResDto);
//
//    }

    /**
     * 필터링 API
     *
     * [Get] /study?platform=web&region=seoul&level=beginner&page=0&size=10
     * - page : 현재 페이지 (default 0)
     * - size : 조회할 데이터 수 (default 10)
     * @return Page<GetStudyDto>
     */
    @Operation(summary = "스터디 필터링", description = "스터디를 (플랫폼, 지역, 난이도 별로) 필터링 처리 합니다. 필터링 조건이 없을 경우 프로젝트 전체 리스트를 반환합니다. ")
    @GetMapping("/study")
    public BaseResponse<List<StudyResDTO>> findPage(@ParameterObject StudySearchCondition condition,
                                                    @ParameterObject @PageableDefault(direction = Sort.Direction.DESC) Pageable pageable){
        return new BaseResponse<>(studyService.findPage(pageable));
    }


    /**
     * 검색 API
     * [Get] /study/search?keyword=검색어
     * @return Page<GetStudyDto>
     */
    @Operation(summary = "스터디 검색", description = "검색어(keyword)를 이용해 스터디를 검색합니다.")
    @GetMapping("/study/search")
    public BaseResponse<List<StudyResDTO>> searchStudy(@Parameter(example = "검색어") @RequestParam String keyword,
                                                       @ParameterObject @PageableDefault(direction = Sort.Direction.DESC) Pageable pageable){
        // 검색어 2글자 이상 입력
        if(keyword.length()<2){
            return new BaseResponse<>(BaseResponseStatus.REQUEST_KEYWORD);
        }
        return new BaseResponse<>(studyService.searchStudy(keyword,pageable));
    }

}

