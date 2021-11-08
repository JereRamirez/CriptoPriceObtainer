package com.example.criptopriceobtainer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BitcoinInfo {
    @Id
    @GeneratedValue
    private Long id;
    private BigDecimal price;
    private LocalDateTime timestamp;
}

