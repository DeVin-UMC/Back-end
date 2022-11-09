package UMC.DeVin.project.repository;

import UMC.DeVin.project.dto.ProjectRes;
import UMC.DeVin.project.dto.ProjectSearchCondition;
import UMC.DeVin.project.entity.level.ProgramLevel;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.util.List;

import static UMC.DeVin.project.entity.QProject.project;
import static UMC.DeVin.project.entity.QProjectPlatform.projectPlatform;
import static UMC.DeVin.project.entity.QProjectRecruitment.projectRecruitment;
import static UMC.DeVin.project.entity.QProjectRegion.projectRegion;
import static com.nimbusds.oauth2.sdk.util.StringUtils.*;
import static org.springframework.data.support.PageableExecutionUtils.*;

public class ProjectRepositoryImpl implements ProjectRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public ProjectRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    //분류, 지역, 난이도
    public Page<ProjectRes> findPage(ProjectSearchCondition condition, Pageable pageable) {

        List<ProjectRes> content = queryFactory
                .select(Projections.fields(ProjectRes.class,
                        project.title,
                        project.img,
                        project.des.as("content"),
                        project.programLevel.stringValue().as("programLevel"),
                        projectPlatform.title.as("platform"),
                        projectRegion.title.as("region")))
                .from(project)
                .join(projectPlatform).on(projectPlatform.project.eq(project))
                .join(projectRegion).on(projectRegion.project.eq(project))
                .join(projectRecruitment).on(projectRecruitment.project.eq(project))
                .where(platformEq(condition.getPlatform()),
                        regionEq(condition.getRegion()),
                        levelEq(condition.getLevel()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .distinct()
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(project.count())
                .from(project)
                .join(projectPlatform).on(projectPlatform.project.eq(project))
                .join(projectRegion).on(projectRegion.project.eq(project))
                .join(projectRecruitment).on(projectRecruitment.project.eq(project))
                .where(platformEq(condition.getPlatform()),
                        regionEq(condition.getRegion()),
                        levelEq(condition.getLevel()));

        return getPage(content,pageable,countQuery::fetchOne);

    }

    private BooleanExpression levelEq(String level) {
        return isBlank(level) ? null : project.programLevel.eq(ProgramLevel.valueOf(level.toUpperCase()));
    }

    private BooleanExpression regionEq(String region) {
        return isBlank(region) ? null : projectRegion.title.eq(region);
    }

    private BooleanExpression platformEq(String platform) {
        return isBlank(platform) ? null : projectPlatform.title.eq(platform);
    }


    @Override
    public List<ProjectRes> findByKeyword(String keyword, Pageable pageable) {

        List<ProjectRes> content = queryFactory
                .select(Projections.fields(ProjectRes.class,
                        project.title,
                        project.img,
                        project.des.as("content"),
                        project.programLevel.stringValue().as("programLevel"),
                        projectPlatform.title.as("platform"),
                        projectRegion.title.as("region")))
                .from(project)
                .join(projectPlatform).on(projectPlatform.project.eq(project))
                .join(projectRegion).on(projectRegion.project.eq(project))
                .join(projectRecruitment).on(projectRecruitment.project.eq(project))
                .where(
                        project.title.contains(keyword) // 프로젝트 제목
                                .or(project.des.contains(keyword)) // 프로젝트 내용
                                .or(projectPlatform.title.contains(keyword)) // 플랫폼 : app, web ..
                                .or(projectRecruitment.language.contains(keyword)) // 언어 : spring, react ..
                                .or(projectRegion.title.contains(keyword)) // 지역
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .distinct()
                .fetch();

        return content;

    }


}
