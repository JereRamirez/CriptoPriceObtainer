package com.example.criptopriceobtainer.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.stream.Stream;

@Getter @Setter
public class BitcoinAnalytic {
    private BigDecimal max;
    private BigDecimal sum;
    private BigDecimal count;

    public BitcoinAnalytic() {
        this.max = BigDecimal.ZERO;
        this.sum = BigDecimal.ZERO;
        this.count = BigDecimal.ZERO;
    }

    public BitcoinAnalytic analyze(Stream<BigDecimal> prices) {
        prices.forEach( price -> {
            if(this.max.compareTo(price) < 0)
                this.max = price;

            this.sum = this.sum.add(price);
            this.count = this.count.add(BigDecimal.ONE);
        });
        return this;
    }
}
