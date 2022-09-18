package UMC.DeVin.common.base;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, HttpStatus.OK, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, HttpStatus.BAD_REQUEST, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, HttpStatus.UNAUTHORIZED, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, HttpStatus.UNAUTHORIZED, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003, HttpStatus.FORBIDDEN, "권한이 없는 유저의 접근입니다."),
    NO_LOGIN_USER(false,2004, HttpStatus.BAD_REQUEST, "로그인된 사용자가 없습니다."),
    INVALID_REFRESH_TOKEN(false,2005, HttpStatus.BAD_REQUEST, "refresh token이 유효하지 않습니다."),
    INVALID_ACCESS_TOKEN(false,2006, HttpStatus.BAD_REQUEST, "access token이 유효하지 않습니다."),
    LOGIN_WITH_WRONG_IP_ADDRESS(false,2007, HttpStatus.BAD_REQUEST, "서로 다른 IP 주소에서 같은 refresh token을 이용해 접근을 시도했습니다."),
    WRONG_ACCESS(false,2008, HttpStatus.BAD_REQUEST, "잘못된 접근입니다."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, HttpStatus.BAD_REQUEST,  "유저 아이디 값을 확인해주세요."),

    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, HttpStatus.BAD_REQUEST, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, HttpStatus.BAD_REQUEST,  "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,2017, HttpStatus.BAD_REQUEST, "중복된 이메일입니다."),
    ALREADY_JOIN_BEFORE(false,2018, HttpStatus.BAD_REQUEST, "이미 존재하는 회원입니다."),


    INVALID_TAG(false,2020, HttpStatus.BAD_REQUEST, "유효하지 않은 태그입니다."),

    HTTP_MESSAGE_NOT_READABLE(false,2030, HttpStatus.BAD_REQUEST, "잘못된 Request 입니다."),
    MISSING_REQUEST_PARAMETER(false,2031, HttpStatus.BAD_REQUEST, "필수 Request Parameter가 누락되었습니다."),
    VALIDATION_EXCEPTION(false,2032, HttpStatus.BAD_REQUEST, "Validation 오류가 발생했습니다."),

    // qna
    EMPTY_QUESTION_ID(false,2040,HttpStatus.BAD_REQUEST,"질문 인덱스 값을 확인해주세요."),
    EMPTY_ANSWER_ID(false,2041,HttpStatus.BAD_REQUEST,"답변 인덱스 값을 확인해주세요."),
    ALREADY_LIKE(false,2042,HttpStatus.BAD_REQUEST,"이미 추천되었습니다."),
    ALREADY_UNLIKE(false,2043,HttpStatus.BAD_REQUEST,"이미 비추천되었습니다."),
    REQUEST_KEYWORD(false,2044,HttpStatus.BAD_REQUEST,"2글자 이상 입력해주세요."),




    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, HttpStatus.INTERNAL_SERVER_ERROR, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, HttpStatus.BAD_REQUEST,  "중복된 이메일입니다."),
    DUPLICATED_LOGIN_ID(false, 3014, HttpStatus.BAD_REQUEST,"중복된 로그인 ID입니다."),
    DUPLICATED_PHONE_NUMBER(false, 3016, HttpStatus.BAD_REQUEST,"중복된 핸드폰 번호입니다."),


    INCORRECT_ID(false,3014, HttpStatus.BAD_REQUEST,"존재하지 않는 ID입니다."),
    INCORRECT_PASSWORD(false,3014, HttpStatus.BAD_REQUEST,"비밀번호가 일치하지 않습니다."),



    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, HttpStatus.INTERNAL_SERVER_ERROR,"데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, HttpStatus.INTERNAL_SERVER_ERROR, "서버와의 연결에 실패하였습니다."),
    UNKNOWN_SERVER_EXCEPTION(false,4002, HttpStatus.BAD_REQUEST, "알 수 없는 서버 오류가 발생했습니다."),


    PASSWORD_ENCRYPTION_ERROR(false, 4011, HttpStatus.INTERNAL_SERVER_ERROR,"비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, HttpStatus.INTERNAL_SERVER_ERROR,"비밀번호 복호화에 실패하였습니다."),


    FILE_SAVE_ERROR(false, 5000, HttpStatus.INTERNAL_SERVER_ERROR,"파일 저장에 실패했습니다."),
    FILE_DOWNLOAD_ERROR(false, 5001, HttpStatus.INTERNAL_SERVER_ERROR,"파일 다운로드에 실패했습니다."),


    EXTERNAL_API_ERROR(false, 5011, HttpStatus.INTERNAL_SERVER_ERROR,"외부 API 통신에 실패했습니다.");



    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    private BaseResponseStatus(boolean isSuccess, int code, HttpStatus httpStatus,  String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
