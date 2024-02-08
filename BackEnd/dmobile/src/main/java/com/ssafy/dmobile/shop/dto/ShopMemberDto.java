package com.ssafy.dmobile.shop.dto;

import com.ssafy.dmobile.shop.entity.Shop;
import com.ssafy.dmobile.shop.entity.ShopMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Function;

@Getter
@Setter
public class ShopMemberDto {
    private Long productId;
    private Long productDate;

    public ShopMemberDto(Long productId, Long productDate) {
        this.productId = productId;
        this.productDate = productDate;
    }

    public static ShopMemberDto mapFromShopMember(ShopMember shopMember) {
        return new ShopMemberDto(shopMember.getShop().getProductId(), shopMember.getProductDate());
    }
    public static ShopMemberDto mapFromShop(Shop shop) {
        ShopMemberDto dto = new ShopMemberDto(shop.getProductId(), null); // productDate는 Shop 엔티티에 없으니까 null
        // 어차피 구매 안 한 것
        return dto;
    }


//    public static ShopMemberDto from(ShopMember shopMember) {
//        ShopMemberDto dto = new ShopMemberDto();
//        dto.setProductId(shopMember.getShop().getProductId());
//        dto.setProductDate(shopMember.getProductDate());
//        return dto;
//    }
}