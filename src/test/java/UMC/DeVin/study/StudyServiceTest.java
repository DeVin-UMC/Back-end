package UMC.DeVin.study;

import UMC.DeVin.common.Region;
import UMC.DeVin.common.base.BaseException;
import UMC.DeVin.member.Member;
import UMC.DeVin.member.repository.MemberRepository;
import UMC.DeVin.study.dto.PostStudyReqDTO;
import UMC.DeVin.study.dto.PostStudyResDTO;
import UMC.DeVin.study.dto.RegionDTO;
import UMC.DeVin.study.dto.StudyResDTO;
import UMC.DeVin.study.entity.Study;
import UMC.DeVin.study.entity.StudyRegion;
import UMC.DeVin.study.repository.StudyRegionRepository;
import UMC.DeVin.study.repository.StudyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static UMC.DeVin.common.Level.BEGINNER;
import static UMC.DeVin.common.Level.MIDDLE;
import static UMC.DeVin.common.Region.*;
import static UMC.DeVin.config.oauth.entity.ProviderType.GOOGLE;
import static UMC.DeVin.member.Member.createMember;
import static UMC.DeVin.study.entity.Study.createStudy;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * StudyService 관련 테스트 클래스입니다.
 */
@Transactional
@SpringBootTest
class StudyServiceTest {

    @Autowired
    private StudyService studyService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private StudyRepository studyRepository;
    @Autowired
    private StudyRegionRepository studyRegionRepository;

    private Member member;

    /**
     * 테스트 멤버 삽입
     */
    @PostConstruct
    public void init() {
        // 1. 테스트 member 삽입
        member = memberRepository.save(createMember("홍길동", "abcde@email.com", "img url", GOOGLE));

        // 2. 테스트 Study 삽입
        List<RegionDTO> dtos = new ArrayList<>();
        dtos.add(new RegionDTO(GYEONGGI));
        dtos.add(new RegionDTO(GYEONGBUK));
        studyRepository.save(createStudy(new PostStudyReqDTO("스터디 제목1", "스터디 설명1",
                MIDDLE, 10, null, dtos), "", member));

        studyRepository.save(createStudy(new PostStudyReqDTO("스터디 제목2", "스터디 설명2",
                MIDDLE, 10, null, dtos), "", member));

        studyRepository.save(createStudy(new PostStudyReqDTO("스터디 제목3", "스터디 설명3",
                MIDDLE, 10, null, dtos), "", member));

        studyRepository.save(createStudy(new PostStudyReqDTO("스터디 제목4", "스터디 설명4",
                MIDDLE, 10, null, dtos), "", member));

        studyRepository.save(createStudy(new PostStudyReqDTO("스터디 제목5", "스터디 설명5",
                MIDDLE, 10, null, dtos), "", member));

        studyRepository.save(createStudy(new PostStudyReqDTO("스터디 제목6", "스터디 설명6",
                MIDDLE, 10, null, dtos), "", member));
    }

    /**
     * createStudy 메서드 테스트 함수입니다.
     */
    @Test
    void createStudyTest() throws BaseException {
        List<RegionDTO> dtos = new ArrayList<>();
        dtos.add(new RegionDTO(BUSAN));
        dtos.add(new RegionDTO(SEOUL));
        dtos.add(new RegionDTO(GYEONGGI));
        PostStudyResDTO dto = studyService.createStudy(new PostStudyReqDTO("스터디 제목",
                "스터디 설명", BEGINNER, 10, null, dtos), member);
        Optional<Study> findStudy = studyRepository.findByTitle("스터디 제목");


        // 1. 정상적으로 스터디가 저장되었는지
        assertThat(findStudy).isNotEmpty();
        // 2. 정상적으로 스터디 작성자가 저장되었는지
        assertThat(findStudy.get().getMember()).isEqualTo(member);

        List<StudyRegion> findRegions = studyRegionRepository.findByStudy(findStudy.get());
        // 3. 정상적으로 지역이 저장되었는지
        assertThat(findRegions.size()).isEqualTo(3);

    }

    /**
     * findPage 메서드 (페이징) 테스트 함수입니다.
     */
    @Test
    void findPageTest() {
        PageRequest pageRequest = PageRequest.of(0, 3);
        List<StudyResDTO> studyPages = studyService.findPage(pageRequest);

        assertThat(studyPages.size()).isEqualTo(3);
        assertThat(studyPages.get(0).getTitle()).isEqualTo("스터디 제목1");
    }
}