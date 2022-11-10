package UMC.DeVin.file.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 파일 업로드 시 return되는 DTO입니다.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDTO {
    private String fileUrl;
}
