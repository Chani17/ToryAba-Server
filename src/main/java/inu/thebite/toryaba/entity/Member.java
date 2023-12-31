package inu.thebite.toryaba.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
@Table(name = "tb_member")
@Getter
public abstract class Member {

    @Id
    @Column(name = "member_id", length = 25, nullable = false, unique = true)
    protected String id;

    @Column(name = "member_pw", length = 100, nullable = false, unique = true)
    protected String password;

    @Column(name = "member_name", length = 45)
    protected String name;

    @Column(name = "member_email", length = 45)
    protected String email;

    @Column(name = "member_phone", length = 13, unique = true)
    protected String phone;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "member_auth", length = 20)
    protected MemberStatus auth;

    @Column(name = "member_approval_yn", length = 1)
    protected String approvalYN;

    @Column(name = "member_register_dt")
    protected String registerDate;
}
