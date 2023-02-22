package com.example.air.application.Cache;

import com.example.air.application.AirQualityInfo;
import com.example.air.application.KoreaAirQualityService;
import com.example.air.application.KoreaAirQualityServiceFactory;
import com.example.air.application.Sido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Slf4j      //로그남기기
@Service
@RequiredArgsConstructor
@Component
//@EnableCaching
public class CacheKoreaAirQuality {
    private final KoreaAirQualityServiceFactory koreaAirQualityServiceFactory;

    //cahceNames 의 역할?
    @Cacheable(value = "info" , key = "#sidoCode")
    public AirQualityInfo getCache(Sido sidoCode) {

        KoreaAirQualityService service = koreaAirQualityServiceFactory.getService(sidoCode);

        return service.getAirQualityInfo();
    }

    @CachePut(value = "info" , key = "#sidoCode")
    public AirQualityInfo updateCache(Sido sidoCode) {

        KoreaAirQualityService service = koreaAirQualityServiceFactory.getService(sidoCode);
        System.out.println("갱신");

        return service.getAirQualityInfo();
    }


}