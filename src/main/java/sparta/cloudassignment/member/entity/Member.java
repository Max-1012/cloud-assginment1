package sparta.cloudassignment.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.cloudassignment.global.common.BaseEntity;
import sparta.cloudassignment.member.enums.MbtiEnums;

@Getter
@Entity
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private MbtiEnums mbti;

    @Column
    private String profileImageKey;

    @Builder
    public Member(String name, int age, MbtiEnums mbti) {
        this.name = name;
        this.age = age;
        this.mbti = mbti;
    }

    public void uploadMemberImage(String profileImageKey) {
        this.profileImageKey = profileImageKey;
    }
}
