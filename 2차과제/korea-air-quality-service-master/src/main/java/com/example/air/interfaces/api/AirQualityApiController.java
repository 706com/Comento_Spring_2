package com.example.air.interfaces.api;

import com.example.air.application.AirQualityService;
import com.example.air.application.AirQualityInfo;
import com.example.air.infrastructure.api.seoul.SeoulAirQualityApiCaller;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AirQualityApiController {
    private final AirQualityService airQualityService;

    // (성공) TODO: 시도와 구정보를 parameter 로 받는 GET API 작성
    @GetMapping("")     //localhost:800
    public AirQualityInfo getAirQualityInfo() {
        return airQualityService.getAirQualityInfo();
    }
    //★ api 생성하기 위해 service 와 연동시켜 return 시켜야함
    @GetMapping("/api/v1/air-quality/seoul")    //localhost:8080/api/v1/air-quality/seoul
                                                    //localhost:8080//api/v1/air-quality/seoul?gu=__
    public AirQualityInfo getAirQualityInfoSeoul(@RequestParam(value = "gu",required = false) String site) {
        return airQualityService.getAirQualityInfoSeoul(site);
    }
}
