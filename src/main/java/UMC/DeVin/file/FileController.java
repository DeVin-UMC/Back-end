package UMC.DeVin.file;

import UMC.DeVin.common.base.BaseException;
import UMC.DeVin.common.base.BaseResponse;
import UMC.DeVin.file.dto.DeleteFileDTO;
import UMC.DeVin.file.dto.FileDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FileController {

    private final FileUploadUtil fileUploadUtil;

    /**
     * 파일 업로드 테스트 컨트롤러입니다.
     * 카테고리와 파일을 입력으로 받고, 파일 업로드를 수행합니다.
     * @param category 해당 파일의 분류입니다. category를 project로 할 경우 S3 상에서 project 폴더에 들어가게 됩니다.
     * @param multipartFile 입력으로 들어오는 파일
     * @return 생성된 파일 URL을 담은 DTO response 객체
     * @throws BaseException 파일 업로드 실패 시 예외를 던집니다.
     */
    @PostMapping("/test/file/upload")
    public BaseResponse<FileDTO> uploadFile(@RequestParam("category") String category,
                                            @RequestParam(value = "file")MultipartFile multipartFile) throws BaseException {

        return new BaseResponse<>(new FileDTO(fileUploadUtil.uploadFileV1(category, multipartFile)));
    }

    /**
     * 파일 삭제 테스트 컨트롤러입니다.
     * 파일 이름을 입력으로 받고, 파일 삭제를 수행합니다.
     * @param fileDTO 삭제할 파일 정보를 담고 있는 DTO
     * @return 정상적으로 삭제되었을 경우, 성공 메시지 이외에 유의미한 데이터를 넘겨주진 않습니다.
     */
    @PostMapping("/test/file/delete")
    public BaseResponse<String> deleteFile(@RequestBody DeleteFileDTO fileDTO) {
        fileUploadUtil.deleteFile(fileDTO.getFileName());
        return new BaseResponse<>("삭제에 성공했습니다.");
    }
}
