package UMC.DeVin.study;

import UMC.DeVin.common.base.BaseException;
import UMC.DeVin.file.FileUploadUtil;
import UMC.DeVin.member.Member;
import UMC.DeVin.member.dto.MemberRes;
import UMC.DeVin.study.dto.PostStudyReqDTO;
import UMC.DeVin.study.dto.PostStudyResDTO;
import UMC.DeVin.study.dto.RegionDTO;
import UMC.DeVin.study.dto.StudyResDTO;
import UMC.DeVin.study.entity.Study;
import UMC.DeVin.study.entity.StudyRegion;
import UMC.DeVin.study.repository.StudyRegionRepository;
import UMC.DeVin.study.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static UMC.DeVin.study.entity.StudyRegion.createStudyRegion;

/**
 * 스터디 관련 도메인 로직을 포함한 서비스 클래스입니다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class StudyService {

    private final StudyRepository studyRepository;
    private final StudyRegionRepository studyRegionRepository;
    private final FileUploadUtil fileUploadUtil;

    /**
     * 스터디를 생성하는 함수입니다.
     * @param dto 스터디 생성시 Request Body로 입력받은 DTO입니다.
     * @param member 스터디를 생성한 사용자입니다. (실제 로직에서는 OAuth로 현재 로그인한 사용자가 들어갑니다.)
     * @return 생성된 스터디의 ID를 담고 있는 DTO
     */
    public PostStudyResDTO createStudy(PostStudyReqDTO dto, Member member) throws BaseException {

        // 0. 이미지 파일 업로드
        String imageUrl = fileUploadUtil.uploadFileV1("PROJECT", dto.getFile());

        // 1. Study 엔티티 생성
        Study createdStudy = studyRepository.save(Study.createStudy(dto, imageUrl, member));

        // 2. StudyRegion 엔티티 생성
        for (RegionDTO regionDTO : dto.getRegionDto()) {
            studyRegionRepository.save(createStudyRegion(createdStudy, regionDTO));
        }

        return new PostStudyResDTO(createdStudy.getId());
    }

    /**
     * 전체 스터디를 페이징하여 반환하는 함수입니다.
     * 별도의 필터링 없이 모든 스터디에 대해서 필터링합니다.
     * @param pageable 컨트롤러에서 넘어온 페이징 정보
     * @return 스터디 페이징 결과
     */
    public List<StudyResDTO> findPage(Pageable pageable) {
        List<StudyResDTO> studyRes = new ArrayList<>();

        Page<Study> studies = studyRepository.findAll(pageable);
        for (Study study : studies) {
            List<StudyRegion> regions = studyRegionRepository.findByStudy(study);
            List<RegionDTO> regionDTOs = new ArrayList<>();
            for (StudyRegion studyRegion : regions) {
                regionDTOs.add(new RegionDTO(studyRegion.getRegion()));
            }
            studyRes.add(new StudyResDTO(study.getId(), study.getTitle(), study.getDescription(), study.getLevel(),
                    study.getRecruitNumber(), study.getImageUrl(), new MemberRes(study.getMember().getId(),
                    study.getMember().getEmail(),  study.getMember().getProfileImageUrl(), study.getMember().getNickname(),
                    study.getMember().getDivision(), study.getMember().getRole()), regionDTOs));
        }

        return studyRes;
    }
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<StudyResDTO> searchStudy(String keyword, Pageable pageable) {
        return studyRepository.findByKeyword(keyword, pageable);
    }

}
