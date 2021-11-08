package com.example.criptopriceobtainer.controller;

import com.example.criptopriceobtainer.converter.AvgInfoToAvgInfoDTOConverter;
import com.example.criptopriceobtainer.converter.BitcoinInfoToBitcoinInfoDTOConverter;
import com.example.criptopriceobtainer.model.dto.AvgInfoDTO;
import com.example.criptopriceobtainer.model.dto.BitcoinInfoDTO;
import com.example.criptopriceobtainer.service.BitcoinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Slf4j
@Controller
@RequestMapping("/api/priceObtainer")
public class BitcoinController {
    private final BitcoinService bitcoinService;

    @Autowired
    public BitcoinController(BitcoinService bitcoinService) {
        this.bitcoinService = bitcoinService;
    }

    @GetMapping
    public ResponseEntity<BitcoinInfoDTO> getBitcoinPriceIn(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime timestamp){
        log.info("Received request to get Bitcoin information in timestamp: {}", timestamp);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BitcoinInfoToBitcoinInfoDTOConverter.convert(bitcoinService.obtainPrice(timestamp)));
    }

    @GetMapping("/avg")
    public ResponseEntity<AvgInfoDTO> getBitcoinAvgBetween(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromTimestamp,
                                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toTimestamp){
        log.info("Received request to get Bitcoin information between timestamps: {} - {}", fromTimestamp, toTimestamp);
        return ResponseEntity.status(HttpStatus.OK)
                .body(AvgInfoToAvgInfoDTOConverter.convert(bitcoinService.obtainAverage(fromTimestamp, toTimestamp)));
    }
}
