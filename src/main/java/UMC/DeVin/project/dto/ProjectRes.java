package UMC.DeVin.project.dto;

import UMC.DeVin.common.Level;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectRes {

    private String title;
    private String imgUrl;
    private String content;
    private Level programLevel;
    private String platform;
    private String region;

}
