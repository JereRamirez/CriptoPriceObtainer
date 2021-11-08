package com.example.criptopriceobtainer.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter @Setter @Builder
public class AvgInfoDTO {
    private BigDecimal avg;
    private BigDecimal maxValue;
    private BigDecimal percentageDifference;
}
