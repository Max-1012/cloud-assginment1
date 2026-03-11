package sparta.cloudassignment.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonError  {

    // -- 1000:  요청 오류 --
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"E1001","서버 내부 오류가 발생했습니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST,"E002","유효하지 않은 입력입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "E003", "존재하지 않는 멤버입니다");

    private final HttpStatus status;
    private final String code;
    private final String message;

}

