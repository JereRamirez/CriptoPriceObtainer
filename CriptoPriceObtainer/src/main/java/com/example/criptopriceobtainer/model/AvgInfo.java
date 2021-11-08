package com.example.criptopriceobtainer.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class AvgInfo {
    @Id
    @GeneratedValue
    private Long id;
    private BigDecimal avg;
    private BigDecimal maxValue;
    private BigDecimal percentageDifference;
}

