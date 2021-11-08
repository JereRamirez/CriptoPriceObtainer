package com.example.criptopriceobtainer.converter;

import com.example.criptopriceobtainer.model.BitcoinInfo;
import com.example.criptopriceobtainer.model.dto.BitcoinInfoDTO;

public class BitcoinInfoToBitcoinInfoDTOConverter {
    public static BitcoinInfoDTO convert(BitcoinInfo bitcoinInfo) {
        return BitcoinInfoDTO.builder()
                .price(bitcoinInfo.getPrice())
                .timestamp(bitcoinInfo.getTimestamp())
                .build();
    }
}
