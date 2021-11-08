package com.example.criptopriceobtainer.service;

import com.example.criptopriceobtainer.model.BitcoinInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class PriceObtainerServiceTest {
    private PriceObtainerService service;
    private PriceConnectorService connectorService;

    @BeforeEach
    void setUp() {
        connectorService = Mockito.mock(PriceConnectorService.class);
        service = new PriceObtainerService(connectorService);
    }

    @Test
    public void obtainBitcoinPrice_serviceWorking_returnsOk(){
        Mockito.when(connectorService.getRequest()).thenReturn(new BitcoinInfo());

        Assertions.assertThat(service.obtain()).isNotNull();
    }

    @Test
    public void obtainBitcoinPrice_serviceNotWorking_throwsRuntimeException(){
        Mockito.when(connectorService.getRequest()).thenThrow(new RuntimeException(PriceConnectorService.ERROR_MESSAGE));

        Assertions.assertThatThrownBy(()-> service.obtain())
                .isInstanceOf(RuntimeException.class)
                .hasMessage(PriceConnectorService.ERROR_MESSAGE);
    }
}
