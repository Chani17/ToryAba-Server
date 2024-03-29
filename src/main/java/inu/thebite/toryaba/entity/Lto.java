package inu.thebite.toryaba.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_lto")
public class Lto extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lto_seq", length = 11, nullable = false)
    private Long id;

    // 템플릿 번호
    @Column(name = "tmpl_seq", length = 11)
    private int templateNum;

    // 장기 목표 상태
    @Column(name = "lto_status", nullable = false, length = 11)
    private String status;

    // 장기 목표 이름
    @Column(name = "lto_name", nullable = false, length = 200)
    private String name;

    // 장기 목표 내용
    @Column(name = "lto_contents", length = 200)
    private String contents;

    // 발달 타입
    @ElementCollection
    @Column(name = "lto_develop_type")
    private List<String> developType;

    // 장기 목표 도달 일자
    @Column(name = "lto_arr_dt")
    private String achieveDate;

    // 등록 일자
    @Column(name = "lto_reg_dt", nullable = false)
    private String registerDate;

    // 삭제 여부
    @Column(name = "del_yn", nullable = false, length = 1)
    private String delYN;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "domain_seq")
    private Domain domain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_seq")
    private Student student;

    @JsonBackReference
    @OneToMany(mappedBy = "lto", cascade = CascadeType.REMOVE)
    private List<Sto> stos = new ArrayList<>();


    public static Lto createLto(int templateNum, String name, String content, List<String> developType, Domain domain, Student student) {
        Lto lto = new Lto();
        lto.templateNum = templateNum;
        lto.status = "READY";
        lto.name = name;
        lto.contents = content;
        lto.developType = developType;
        lto.achieveDate = "Not yet";
        lto.registerDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        lto.delYN = "N";
        lto.domain = domain;
        lto.student = student;
        return lto;
    }

    // update LTO status when LTO status is stop or in progress
    public void updateLtoStatus(String status) {
        this.status = status;
    }

    // update LTO status when LTO status is hit
    public void updateLtoHitStatus(String status) {
        this.status = status;
        this.achieveDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }

    // update LTO contents
    public void updateLTO(String name, String contents) {
        this.name = name;
        this.contents = contents;
    }

    // select develop type
    public void selectDevelopType(String development) {
        this.developType.add(development);
    }

    // remove develop type
    public void removeDevelopType(String development) {
        this.developType.remove(development);
    }
}