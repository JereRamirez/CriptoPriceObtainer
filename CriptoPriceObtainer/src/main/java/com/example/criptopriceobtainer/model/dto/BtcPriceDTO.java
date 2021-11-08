package com.example.criptopriceobtainer.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BtcPriceDTO {
    @JsonProperty
    private String lprice;
    @JsonProperty
    private String curr1;
    @JsonProperty
    private String curr2;
}
