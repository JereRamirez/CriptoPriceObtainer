package com.example.criptopriceobtainer.service;

import com.example.criptopriceobtainer.model.BitcoinInfo;
import com.example.criptopriceobtainer.model.dto.BtcPriceDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
public class PriceConnectorService {

    public static final String ERROR_MESSAGE = "Bitcoin price service could not retrieve valid data.";
    public static final String SERVICE_URL = "https://cex.io/api/last_price/BTC/USD";

    public BitcoinInfo getRequest(){
        try{
            log.info("Obtaining Bitcoin price from external service.");
            String retrievedPriceJson = WebClient.create(SERVICE_URL)
                    .method(HttpMethod.GET)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return getBitcoinInfo(retrievedPriceJson);
        }catch (WebClientException | IOException exception){
            log.error(ERROR_MESSAGE);
            throw new RuntimeException(ERROR_MESSAGE);
        }
    }

    private BitcoinInfo getBitcoinInfo(String retrievedPriceJson) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        String btcPrice = om.readValue(retrievedPriceJson, BtcPriceDTO.class).getLprice();
        BitcoinInfo bitcoinInfo = new BitcoinInfo();
        bitcoinInfo.setPrice(new BigDecimal(btcPrice));
        bitcoinInfo.setTimestamp(LocalDateTime.now());
        return bitcoinInfo;
    }
}
