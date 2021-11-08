package com.example.criptopriceobtainer.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @Builder
public class BitcoinInfoDTO {
    private BigDecimal price;
    private LocalDateTime timestamp;
}
