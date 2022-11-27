package UMC.DeVin.project.repository;

import UMC.DeVin.common.Level;
import UMC.DeVin.common.Platform;
import UMC.DeVin.common.Region;
import UMC.DeVin.project.dto.ProjectRes;
import UMC.DeVin.project.dto.ProjectSearchCondition;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.util.List;

import static UMC.DeVin.project.entity.QProject.project;
import static UMC.DeVin.project.entity.QProjectPlatform.projectPlatform;
import static UMC.DeVin.project.entity.QProjectRecruitment.projectRecruitment;
import static UMC.DeVin.project.entity.QProjectRegion.projectRegion;
import static com.nimbusds.oauth2.sdk.util.StringUtils.*;

public class ProjectRepositoryImpl implements ProjectRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public ProjectRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    //분류, 지역, 난이도
    public List<ProjectRes> findPage(ProjectSearchCondition condition, Pageable pageable) {

        List<ProjectRes> content = queryFactory
                .select(Projections.fields(ProjectRes.class,
                        project.id.as("projectId"),
                        project.title,
                        project.imgUrl,
                        project.des.as("content"),
                        project.programLevel.as("programLevel"),
                        projectPlatform.title.as("platform"),
                        projectRegion.title.as("region")))
                .from(project)
                .join(projectPlatform).on(projectPlatform.project.eq(project))
                .join(projectRegion).on(projectRegion.project.eq(project))
                .join(projectRecruitment).on(projectRecruitment.project.eq(project))
                .where(platformEq(condition.getPlatform()),
                        regionEq(condition.getRegion()),
                        levelEq(condition.getLevel())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .distinct()
                .fetch();

        return content;

    }

    @Override
    //분류, 지역, 난이도
    public List<ProjectRes> findByNoCondition(Pageable pageable) {

            List<ProjectRes> content = queryFactory
                    .select(Projections.fields(ProjectRes.class,
                            project.id.as("projectId"),
                            project.title,
                            project.imgUrl,
                            project.des.as("content"),
                            project.programLevel.as("programLevel"),
                            projectPlatform.title.as("platform"),
                            projectRegion.title.as("region")))
                    .from(project)
                    .join(projectPlatform).on(projectPlatform.project.eq(project))
                    .join(projectRegion).on(projectRegion.project.eq(project))
                    .join(projectRecruitment).on(projectRecruitment.project.eq(project))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .distinct()
                    .fetch();

            return content;
    }

    private BooleanExpression levelEq(String level) {
        return isBlank(level) ? null : project.programLevel.eq(Level.valueOf(level.toUpperCase()));
    }

    private BooleanExpression regionEq(String region) {
        return isBlank(region) ? null : projectRegion.title.eq(Region.valueOf(region.toUpperCase()));
    }

    private BooleanExpression platformEq(String platform) {
        return isBlank(platform) ? null : projectPlatform.title.eq(Platform.valueOf(platform.toUpperCase()));
    }


    @Override
    public List<ProjectRes> findByKeyword(String keyword, Pageable pageable) {

        List<ProjectRes> content = queryFactory
                .select(Projections.fields(ProjectRes.class,
                        project.id.as("projectId"),
                        project.title,
                        project.imgUrl,
                        project.des.as("content"),
                        project.programLevel.as("programLevel"),
                        projectPlatform.title.as("platform"),
                        projectRegion.title.as("region")))
                .from(project)
                .join(projectPlatform).on(projectPlatform.project.eq(project))
                .join(projectRegion).on(projectRegion.project.eq(project))
                .join(projectRecruitment).on(projectRecruitment.project.eq(project))
                .where(
                        project.title.contains(keyword) // 프로젝트 제목
                                .or(project.des.contains(keyword)) // 프로젝트 내용
                                .or(projectPlatform.title.stringValue().contains(keyword)) // 플랫폼 : app, web ..
                                .or(projectRecruitment.language.contains(keyword)) // 언어 : spring, react ..
                                .or(projectRegion.title.stringValue().contains(keyword)) // 지역
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .distinct()
                .fetch();

        System.out.println(content);
        return content;

    }


}
