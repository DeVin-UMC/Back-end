package UMC.DeVin.project.dto;

import UMC.DeVin.common.Level;
import UMC.DeVin.common.Platform;
import UMC.DeVin.common.Region;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectRes {

    private Long projectId;
    private String title;
    private String imgUrl;
    private String content;
    private Level programLevel;
    private Platform platform;
    private Region region;

}
