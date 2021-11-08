package com.example.criptopriceobtainer.converter;

import com.example.criptopriceobtainer.model.AvgInfo;
import com.example.criptopriceobtainer.model.dto.AvgInfoDTO;

public class AvgInfoToAvgInfoDTOConverter {
    public static AvgInfoDTO convert(AvgInfo avgInfo) {
        return AvgInfoDTO.builder()
                .avg(avgInfo.getAvg())
                .maxValue(avgInfo.getMaxValue())
                .percentageDifference(avgInfo.getPercentageDifference()).build();
    }
}
