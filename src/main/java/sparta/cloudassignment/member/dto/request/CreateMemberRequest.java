package sparta.cloudassignment.member.dto.request;

import jakarta.validation.constraints.*;
import sparta.cloudassignment.member.enums.MbtiEnums;


public record CreateMemberRequest (
        @NotBlank(message = "이름은 필수 입력 항목입니다.")
        @Size(max= 10, message = "이름은 최대 10자 이하이어야 합니다.")
        String name,

        @Min(value = 0, message = "나이는 0 이상이어야 합니다.")
        @Max(value = 150, message = "나이는 150 이하이어야 합니다.")
        Integer age,

        @NotNull(message = "역할 선택은 필수입니다.") // Enum이므로 @NotBlank 대신 @NotNull 사용
        MbtiEnums mbti
){
}
