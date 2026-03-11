package sparta.cloudassignment.member.dto.response;

import sparta.cloudassignment.member.entity.Member;
import sparta.cloudassignment.member.enums.MbtiEnums;

public record GetMemberResponse(
        Long id,
        String name,
        int age,
        MbtiEnums mbti
) {

    public static GetMemberResponse from(Member member) {
        return new GetMemberResponse(member.getId(),member.getName(), member.getAge(), member.getMbti());
    }
}
