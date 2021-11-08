package com.example.criptopriceobtainer.service;

import com.example.criptopriceobtainer.exception.NotFoundEntityException;
import com.example.criptopriceobtainer.model.AvgInfo;
import com.example.criptopriceobtainer.model.BitcoinInfo;
import com.example.criptopriceobtainer.repository.BitcoinRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BitcoinServiceTest {
    private BitcoinService service;
    private BitcoinRepository repository;
    private LocalDateTime fromTimestamp;
    private LocalDateTime toTimestamp;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(BitcoinRepository.class);
        PriceObtainerService priceService = Mockito.mock(PriceObtainerService.class);
        service = new BitcoinService(repository, priceService);
        fromTimestamp = LocalDateTime.MIN;
        toTimestamp = LocalDateTime.MAX;
    }

    @Test
    public void obtainPrice_withExistingPriceInTimestamp_returnsPrice(){
        LocalDateTime timestamp = LocalDateTime.now();
        BitcoinInfo bitcoinInfo = new BitcoinInfo();
        bitcoinInfo.setTimestamp(timestamp);
        Mockito.when(repository.findByTimestampEquals(timestamp)).thenReturn(Optional.of(bitcoinInfo));

        Assertions.assertThat(service.obtainPrice(timestamp)).isEqualTo(bitcoinInfo);
    }

    @Test
    public void obtainPrice_withNoExistingPriceInTimestamp_throwsException(){
        LocalDateTime timestamp = LocalDateTime.now();
        Mockito.when(repository.findByTimestampEquals(timestamp)).thenThrow(NotFoundEntityException.class);

        Assertions.assertThatThrownBy(() -> service.obtainPrice(timestamp))
                .isInstanceOf(NotFoundEntityException.class);
    }

    @Test
    public void obtainAverage_withInfosInTimestampRange_returnsAvgInfo(){
        BitcoinInfo oneBitcoinInfo = new BitcoinInfo();
        oneBitcoinInfo.setPrice(BigDecimal.valueOf(5));
        BitcoinInfo anotherBitcoinInfo = new BitcoinInfo();
        anotherBitcoinInfo.setPrice(BigDecimal.TEN);
        List<BitcoinInfo> infos = Arrays.asList(oneBitcoinInfo, anotherBitcoinInfo);
        Mockito.when(repository.findByTimestampBetween(fromTimestamp,toTimestamp)).thenReturn(java.util.Optional.of(infos));

        AvgInfo average = service.obtainAverage(fromTimestamp, toTimestamp);

        Assertions.assertThat(average.getAvg()).isEqualByComparingTo(BigDecimal.valueOf(7.5));
        Assertions.assertThat(average.getMaxValue()).isEqualTo(BigDecimal.valueOf(10));
        Assertions.assertThat(average.getPercentageDifference()).isEqualTo(BigDecimal.valueOf(0.25));
    }

    @Test
    public void obtainAverage_withoutInfosInRange_returnsAvgInfoWithValues0(){
        Mockito.when(repository.findByTimestampBetween(fromTimestamp, toTimestamp)).thenReturn(Optional.empty());

        AvgInfo average = service.obtainAverage(fromTimestamp, toTimestamp);

        Assertions.assertThat(average.getAvg()).isEqualTo(BigDecimal.ZERO);
        Assertions.assertThat(average.getMaxValue()).isEqualTo(BigDecimal.ZERO);
        Assertions.assertThat(average.getPercentageDifference()).isEqualTo(BigDecimal.ZERO);
    }
}
