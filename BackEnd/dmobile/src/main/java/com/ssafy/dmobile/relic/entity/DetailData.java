package com.ssafy.dmobile.relic.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "relic_detail")
public class DetailData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relic_id")
    private Integer relicId;

    @Column(name = "list_id")   // FK
    private Integer listId;

    @Column(name = "ccma_name")
    private String ccmaName;
    @Column(name = "ccba_mnm1")
    private String ccbaMnm1;
    @Column(name = "ccba_mnm2")
    private String ccbaMnm2;
    @Column(name = "ccba_kdcd")
    private String ccbaKdcd;
    @Column(name = "ccba_ctcd")
    private String ccbaCtcd;
    @Column(name = "ccba_asno")
    private String ccbaAsno;
    @Column(name = "ccba_cpno")
    private String ccbaCpno;
    @Column(name = "longitude")
    private String longitude;
    @Column(name = "latitude")
    private String latitude;
    @Column(name = "gcode_name")
    private String gcodeName;
    @Column(name = "bcode_name")
    private String bcodeName;
    @Column(name = "mcode_name")
    private String mcodeName;
    @Column(name = "scode_name")
    private String scodeName;
    @Column(name = "ccba_quan")
    private String ccbaQuan;
    @Column(name = "ccba_asdt")
    private String ccbaAsdt;
    @Column(name = "ccba_lcad")
    private String ccbaLcad;
    @Column(name = "ccce_name")
    private String ccceName;
    @Column(name = "ccba_poss")
    private String ccbaPoss;
    @Column(name = "ccba_admin")
    private String ccbaAdmin;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tag_relic",
            joinColumns = @JoinColumn(name = "relic_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;
}