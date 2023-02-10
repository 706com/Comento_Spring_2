package com.example.air.application;

import com.example.air.application.util.AirQualityGradeUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
@Builder
public class AirQualityInfo {
    private String sido;
    private Double sidoPm10Avg;
    private String sidoPm10AvgGrade;
    private List<GuAirQualityInfo> guList;

    public AirQualityInfo searchByGu(String gu) {
        if (gu == null) {
            return this;
        }

        var searchGuInfo = searchGuAirQualityInfo(gu);
        //guList = Arrays.asList(searchGuInfo);
        guList = Collections.singletonList(searchGuInfo);   //메모리 절약을 위해서 요소가 없거나(empty) 하나인 경우에는
                                                            // Collections.emptyList() 또는
                                                            // Collections.singletonList()를 사용해라.
                                                            // == Arrays.asList와 같은말 ??
        return this;
    }
    private GuAirQualityInfo searchGuAirQualityInfo(String gu) {
        return guList.stream()
                .filter(guAirQualityInfo -> guAirQualityInfo.getGu().equals(gu))
                .findFirst()    // findFirst()는 filter 조건에 일치하는 element 1개를 Optional로 리턴합니다.
                                // 조건에 일치하는 요소가 없다면 empty가 리턴됩니다.
                .orElseThrow(() -> new IllegalArgumentException(gu + "에 해당되는 자치구가 존재하지 않습니다."));
    }

    @Getter
    // (성공) TODO: 자치구 대기질 정보 명세서대로 파라미터 정의
    //@JsonProperty는 파라미터를 내가 원하는 변수명으로 맵핑하고 싶은 경우 사용
    public static class GuAirQualityInfo {
        private String gu;
        private Integer pm10;
        private Integer pm25;
        private Double o3;
        private Double no2;
        private Double co;
        private Double so2;
        private String pm10Grade;
        private String pm25Grade;
        private String o3Grade;
        private String no2Grade;
        private String coGrade;
        private String so2Grade;

        //생성자를 만듬으로서, 다양한 지역에서도 형변환에 구애받지않고 간단하게 사용가능
        public GuAirQualityInfo(String gu, Integer pm10, Integer pm25, Double o3, Double no2, Double co, Double so2) {
            this.gu = gu;
            this.pm10 = pm10;
            this.pm25 = pm25;
            this.o3 = o3;
            this.no2 = no2;
            this.co = co;
            this.so2 = so2;
            this.pm10Grade = AirQualityGradeUtil.getPm10Grade(Double.valueOf(pm10));
            this.pm25Grade = AirQualityGradeUtil.getPm25Grade(Double.valueOf(pm25));
            this.o3Grade = AirQualityGradeUtil.getO3Grade(o3);
            this.no2Grade = AirQualityGradeUtil.getNo2Grade(no2);
            this.coGrade = AirQualityGradeUtil.getCoGrade(co);
            this.so2Grade = AirQualityGradeUtil.getSo2Grade(so2);
        }
    }
}

