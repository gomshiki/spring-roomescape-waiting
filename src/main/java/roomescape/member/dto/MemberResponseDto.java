package roomescape.member.dto;

import roomescape.member.domain.Member;

public class MemberResponseDto {

    private Long id;
    private String name;
    private String email;
    private String role;

    public MemberResponseDto(String name) {
        this(null, name);
    }

    public MemberResponseDto(Long id, String name) {
        this(id, name, null, null);
    }

    public MemberResponseDto(Long id, String name, String email, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public static MemberResponseDto from(Member member) {
        return new MemberResponseDto(member.getId(), member.getName());
    }

    public Long getId() { return id; }

    public String getName() {
        return name;
    }

    public String getEmail() { return email; }

    public String getRole() { return role; }

    @Override
    public String toString() {
        return "MemberResponseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
