package UMC.DeVin.study;

import UMC.DeVin.common.base.BaseException;
import UMC.DeVin.member.Member;
import UMC.DeVin.member.repository.MemberRepository;
import UMC.DeVin.study.dto.PostStudyReqDTO;
import UMC.DeVin.study.dto.PostStudyResDTO;
import UMC.DeVin.study.dto.RegionDTO;
import UMC.DeVin.study.entity.Study;
import UMC.DeVin.study.entity.StudyRegion;
import UMC.DeVin.study.repository.StudyRegionRepository;
import UMC.DeVin.study.repository.StudyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static UMC.DeVin.common.Level.BEGINNER;
import static UMC.DeVin.common.Region.*;
import static UMC.DeVin.config.oauth.entity.ProviderType.GOOGLE;
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
        member = memberRepository.save(Member.createMember("홍길동", "abcde@email.com", "img url", GOOGLE));
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
}