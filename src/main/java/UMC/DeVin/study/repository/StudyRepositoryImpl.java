package UMC.DeVin.study.repository;

import UMC.DeVin.common.Region;
import UMC.DeVin.study.dto.StudyResDTO;
import UMC.DeVin.study.dto.StudySearchCondition;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;


import static UMC.DeVin.study.entity.QStudy.study;

import static UMC.DeVin.study.entity.QStudyRegion.studyRegion;
import static com.nimbusds.oauth2.sdk.util.StringUtils.isBlank;

public class StudyRepositoryImpl implements StudyRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public StudyRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }



    @Override
    //분류, 지역, 난이도
    public List<StudyResDTO> findPage(StudySearchCondition condition, Pageable pageable) {
        List<StudyResDTO> content = queryFactory
                .select(Projections.fields(StudyResDTO.class,
                        study.title,
                        study.imageUrl,
                        study.description.as("content"),
                        study.level.as("studyLevel"),
                        studyRegion.region.as("region")))
                .from(study)
                .join(studyRegion).on(studyRegion.study.eq(study))
                .where(
                        regionEq(condition.getRegion())
                        )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .distinct()
                .fetch();

        return content;

    }



    @Override
    //분류, 지역, 난이도
    public List<StudyResDTO> findByNoCondition(Pageable pageable) {

            List<StudyResDTO> content = queryFactory
                    .select(Projections.fields(StudyResDTO.class,
                            study.title,
                            study.imageUrl,
                            study.description.as("content"),
                            study.level.as("programLevel"),
                            studyRegion.region.as("region")))
                    .from(study)
                    .join(studyRegion).on(studyRegion.study.eq(study))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .distinct()
                    .fetch();

            return content;
    }

    private BooleanExpression regionEq(String region) {
        return isBlank(region) ? null : studyRegion.region.eq(Region.valueOf(region.toUpperCase()));
    }



    @Override
    public List<StudyResDTO> findByKeyword(String keyword, Pageable pageable) {

        List<StudyResDTO> content = queryFactory
                .select(Projections.fields(StudyResDTO.class,
                        study.title,
                        study.imageUrl,
                        study.description.as("content"),
                        study.level.as("programLevel"),
                        studyRegion.region.as("region")))
                .from(study)
                .join(studyRegion).on(studyRegion.study.eq(study))
                .where(
                        study.title.contains(keyword) // 스터디 제목
                                .or(study.description.contains(keyword)) // 스터디 내용
                                .or(studyRegion.region.stringValue().contains(keyword)) // 지역
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .distinct()
                .fetch();

        System.out.println(content);
        return content;

    }


}
