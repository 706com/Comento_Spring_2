package com.example.air.application;

import com.example.air.application.util.AirQualityGradeUtil;
import com.example.air.infrastructure.api.seoul.SeoulAirQualityApiCaller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AirQualityService {
    private final SeoulAirQualityApiCaller seoulAirQualityApiCaller;

    public AirQualityInfo getAirQualityInfo() {
        AirQualityInfo airQualityInfo = seoulAirQualityApiCaller.getAirQuality();
        System.out.println("111111111111");

        return airQualityInfo;
    }

    public AirQualityInfo getAirQualityInfoSeoul(String site) {
        //String test = "중구";
        AirQualityInfo airQualityInfo = seoulAirQualityApiCaller.getAirQuality();
        List<AirQualityInfo.GuAirQualityInfo> infoSearchGu = new ArrayList<>();

        // (성공) TODO: 자치구 검색 로직 추가 (시간 남는 경우)


        // 로직 설명 : request parameter 를 통해서 값을 받아왔을 때,
        // airQulaityInfo.Gulist 의 site를 탐색하여,
        // 찾았을 경우, 객체를 새로 생성하여 리턴.
        for(AirQualityInfo.GuAirQualityInfo x : airQualityInfo.getGuList()) {
            if (x.getGu().equals(site)) {
                infoSearchGu.add(x.builder()
                        .gu(x.getGu())
                        .pm10(x.getPm10())
                        .pm25(x.getPm25())
                        .o3(x.getO3())
                        .no2(x.getNo2())
                        .co(x.getCo())
                        .so2(x.getSo2())
                        .pm10Grade(x.getPm10Grade())
                        .pm25Grade(x.getPm25Grade())
                        .o3Grade(x.getO3Grade())
                        .no2Grade(x.getNo2Grade())
                        .coGrade(x.getCoGrade())
                        .so2Grade(x.getSo2Grade())
                        .build());

                return AirQualityInfo.builder()
                        .sido("서울시")
                        .sidoPm10Avg(airQualityInfo.getSidoPm10Avg())
                        .sidoPm10AvgGrade(airQualityInfo.getSidoPm10AvgGrade())
                        .guList(infoSearchGu)
                        .build();
            }
        }
        return airQualityInfo;
    }
}
