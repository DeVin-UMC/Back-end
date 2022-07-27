package UMC.DeVin.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetProjectDto {

    private String title;
    private String img;
    private String content;
    private String programLevel;
    private String platform;
    private String region;

}
