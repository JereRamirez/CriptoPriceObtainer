package com.example.criptopriceobtainer.service;

import com.example.criptopriceobtainer.exception.NotFoundEntityException;
import com.example.criptopriceobtainer.model.AvgInfo;
import com.example.criptopriceobtainer.model.BitcoinAnalytic;
import com.example.criptopriceobtainer.model.BitcoinInfo;
import com.example.criptopriceobtainer.repository.BitcoinRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;


@Slf4j
@Service
public class BitcoinService {

    public static final String MAX = "max";
    public static final String SUM = "sum";
    public static final String COUNT = "count";
    private final BitcoinRepository repository;
    private final PriceObtainerService priceService;

    @Autowired
    public BitcoinService(BitcoinRepository repository, PriceObtainerService priceService) {
        this.repository = repository;
        this.priceService = priceService;
    }

    @Scheduled(fixedRate = 10000)
    public void addBitcoinInfo(){
        BitcoinInfo bitcoinInfo = priceService.obtain();
        log.info("Price obtained for timestamp: {}.", bitcoinInfo.getTimestamp());
        this.repository.save(bitcoinInfo);
    }

    public BitcoinInfo obtainPrice(LocalDateTime timestamp) {
        return repository.findByTimestampEquals(timestamp)
                .orElseThrow(() -> new NotFoundEntityException(String.format("No Bitcoin information in timestamp: %s", timestamp)));
    }

    public AvgInfo obtainAverage(LocalDateTime fromTimestamp, LocalDateTime toTimestamp) {
        BitcoinAnalytic bitcoinAnalytic = getSumCountMaxValues(fromTimestamp, toTimestamp);

        BigDecimal average = getAverage(bitcoinAnalytic.getSum(), bitcoinAnalytic.getCount());

        BigDecimal percentageDif = getPercentageDif(average, bitcoinAnalytic.getMax());

        return AvgInfo.builder()
                .avg(average)
                .maxValue(bitcoinAnalytic.getMax())
                .percentageDifference(percentageDif).build();
    }

    private BitcoinAnalytic getSumCountMaxValues(LocalDateTime fromTimestamp, LocalDateTime toTimestamp){
        BitcoinAnalytic bitcoinAnalytic = new BitcoinAnalytic();

         return bitcoinAnalytic.analyze(getPrices(fromTimestamp, toTimestamp));
    }

    private BigDecimal getAverage(BigDecimal sum, BigDecimal count) {
        BigDecimal result = BigDecimal.ZERO;
        if(!count.equals(BigDecimal.ZERO))
            result = sum.divide(count,2, RoundingMode.HALF_EVEN);
        return result;
    }

    private BigDecimal getPercentageDif(BigDecimal average, BigDecimal max) {
        BigDecimal percentageDif = BigDecimal.ZERO;

        //Difference is so small that decided to go with Rounding mode up, but when working with money it should be half even
        if(!Objects.equals(max, BigDecimal.ZERO))
            percentageDif = max.subtract(average)
                    .divide(max,2, RoundingMode.UP);

        return percentageDif;
    }

    private Stream<BigDecimal> getPrices(LocalDateTime fromTimestamp, LocalDateTime toTimestamp) {
        List<BitcoinInfo> infosBetweenTimestamps = repository.findByTimestampBetween(fromTimestamp, toTimestamp)
                .orElse(new ArrayList<>());

        return infosBetweenTimestamps.stream()
                .map(BitcoinInfo::getPrice);
    }


    public List<BitcoinInfo> getAll(){
        return (List<BitcoinInfo>) repository.findAll();

    }
}
