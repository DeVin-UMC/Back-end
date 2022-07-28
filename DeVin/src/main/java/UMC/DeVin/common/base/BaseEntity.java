package UMC.DeVin.common.base;

import UMC.DeVin.auth.OAuthLoginUserUtil;
import UMC.DeVin.member.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 *  1. 모든 Entity에서 BaseEntity를 상속받습니다.
 *  2. 자동으로 등록일자, 등록자, 수정일자, 수정자에 대해 추가됩니다.
 *  TODO : 등록자, 수정자에 대한 로직은 아직 없습니다. (null이 입력되는게 정상입니다.)
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {


    /**
     *  등록일자
     */
    @Column(updatable = false, name = "create_date")
    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    /**
     *  등록자
     */
    @CreatedBy
    private Long registration;

    /**
     *  수정일자
     */
    @Column(name = "update_date")
    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedDate;


    /**
     *  수정자
     */
    @LastModifiedBy
    private Long modifier;


    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updatedDate = now;
    }

    @PreUpdate
    public void preUpdate() {
        updatedDate = LocalDateTime.now();
    }

}
