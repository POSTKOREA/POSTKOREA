package com.ssafy.dmobile.shop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class ShopMemberId implements Serializable {
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "product_id")
    private Long productId;
}
