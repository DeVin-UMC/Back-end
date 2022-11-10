package UMC.DeVin.file;

import UMC.DeVin.common.base.BaseException;
import UMC.DeVin.common.base.BaseResponse;
import UMC.DeVin.file.dto.FileDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FileController {

    private final FileUploadUtil fileUploadUtil;

    @PostMapping("/test/upload")
    public BaseResponse<FileDTO> uploadFile(@RequestParam("category") String category,
                                            @RequestParam(value = "file")MultipartFile multipartFile) throws BaseException {

        return new BaseResponse<>(new FileDTO(fileUploadUtil.uploadFileV1(category, multipartFile)));
    }
}
