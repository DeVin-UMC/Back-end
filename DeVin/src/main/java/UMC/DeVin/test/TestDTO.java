package UMC.DeVin.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 *  DTO, Validation 테스트 용입니다.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestDTO {

    @NotBlank
    private String a;
    @NotBlank
    private String b;
}
