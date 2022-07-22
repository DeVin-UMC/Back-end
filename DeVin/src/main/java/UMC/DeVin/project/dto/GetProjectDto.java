package UMC.DeVin.project.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetProjectDto {

    private String title;
    private String img;
    private String content;
    private String programLevel;
    private List<String> platform;
    private List<String> region;
    private List<String> position;

}
