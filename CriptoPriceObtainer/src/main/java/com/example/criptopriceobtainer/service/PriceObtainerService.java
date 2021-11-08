package com.example.criptopriceobtainer.service;

import com.example.criptopriceobtainer.model.BitcoinInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceObtainerService {
    private final PriceConnectorService connectorService;

    @Autowired
    public PriceObtainerService(PriceConnectorService connectorService) {
        this.connectorService = connectorService;
    }

    public BitcoinInfo obtain() {
        return connectorService.getRequest();
    }
}
