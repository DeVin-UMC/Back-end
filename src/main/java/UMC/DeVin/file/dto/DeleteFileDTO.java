package UMC.DeVin.file.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 파일 삭제를 위해 파일 정보를 담고 있는 DTO입니다.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteFileDTO {
    String fileName;
}
