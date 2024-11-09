package com.inventory.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseDTO {
    private Long id;
    private Long itemId;
    private Integer quantity;
}
