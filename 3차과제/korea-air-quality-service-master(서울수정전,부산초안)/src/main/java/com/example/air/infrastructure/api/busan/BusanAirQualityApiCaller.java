package com.example.air.infrastructure.api.busan;

import com.example.air.application.AirQualityInfo;
import com.example.air.application.util.AirQualityGradeUtil;
import com.example.air.infrastructure.api.seoul.SeoulAirQualityApiDto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class BusanAirQualityApiCaller {
    private final BusanAirQualityApi busanAirQualityApi;

    public BusanAirQualityApiCaller(@Value("${api.busan.base-url}") String baseUrl) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();

//        System.out.println("retrofit : "+ retrofit.baseUrl()+" "+retrofit.callAdapterFactories());
        this.busanAirQualityApi = retrofit.create(BusanAirQualityApi.class);
    }
    //BusanAirQualityApiDto.GetAirQualityResponse
    //4단계 : seoul 과 마찬가지로 convert 함수로 넘겨주도록 바꿨는데...
    // Q) 왜 반환형식을 서울과 다르게 했을까?
    //  AirQualityInfo로 반환할 시, 오류가 뜬다.. BusanDto.GetResponse 로 그대로 넘겨주는 이유는?
    // 그리고 그것을 사용하기 위해서 가공시켜야하는 함수를 짜야하는건가?

    //출력으로 결과를 확인해봤을때, response 에 값이 아예 안담긴다..(불러오질 못한다.) 왜지?
    public BusanAirQualityApiDto.GetAirQualityResponse getAirQuality() {
        try {
            var call = busanAirQualityApi.getAirQuality();
            var response = call.execute().body();

            if (response == null || response.getResponse() == null) {
                throw new RuntimeException("[busan] getAirQuality 응답값이 존재하지 않습니다.");
            }

            for(int i=0; i<response.getResponse().getBody().getItems().getItems().size(); i++) {
                System.out.println(response.getResponse().getBody().getItems().getItems().get(i).getSite());
            }

            if (response.getResponse().isSuccess()) {
                log.info(response.toString());
                return response;
            }

            throw new RuntimeException("[busan] getAirQuality 응답이 올바르지 않습니다. header=" + response.getResponse().getHeader());

        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("[busan] getAirQuality API error 발생! errorMessage=" + e.getMessage());
        }
    }
}

//    //1단계 : convert 함수 짜기
//    // 부산시 공공 API에서 조회한 정보를 AirQualityInfo로 변환해주는 함수
//    private AirQualityInfo convert(BusanAirQualityApiDto.GetAirQualityResponse response) {
//        List<BusanAirQualityApiDto.Item> items = response.getResponse().getItems();
//        Double BusansidoPm10Avg = averagePm10(items);
//        String BusansidoPm10AvgGrade = AirQualityGradeUtil.getPm10Grade(BusansidoPm10Avg);   //구한 pm10 평균값 넘겨서 지수화
//        List<AirQualityInfo.GuAirQualityInfo> guList = convert(items);
//
//        System.out.println("api에서 받아온 item 값들 출력");
//        for(int i=0; i<items.size(); i++){
//            System.out.println(items.get(i));
//        }
//
//        System.out.println("GuList 로 가공시킨 items들 출력");
//        for(int i=0; i<guList.size(); i++){
//            System.out.println(guList.get(i));
//        }
//
//        return AirQualityInfo.builder()
//                .sido("부산시")
//                .sidoPm10Avg(BusansidoPm10Avg)
//                .sidoPm10AvgGrade(BusansidoPm10AvgGrade)
//                .guList(guList)
//                .build();
//    }
//
//    //3단계 : BusanDto 값들을 AirQualityInfo 형식으로 변환
//    // Q) Busan 에서는 Seoul 과 달리 지수가 변수로 명시되어 있는데, util 에 어떤식으로 넘길까? 새로 함수를 만들어야할까?
//    private List<AirQualityInfo.GuAirQualityInfo> convert(List<BusanAirQualityApiDto.Item> items) {
//        List<AirQualityInfo.GuAirQualityInfo> temp = new ArrayList<>();
//
//        // for-each 문 활용
//        // Row 의 값들을 GuAirQualityInfo의 형태로 변환시키기.
//        for (BusanAirQualityApiDto.Item x : items) {
//            temp.add(AirQualityInfo.GuAirQualityInfo.builder()
//                    .gu(x.getSite())
//                    .pm10(Integer.valueOf(x.getPm10()))
//                    .pm25(Integer.valueOf(x.getPm25()))
//                    .o3(Double.valueOf(x.getO3()))
//                    .no2(Double.valueOf(x.getNo2()))
//                    .co(Double.valueOf(x.getCo()))
//                    .so2(Double.valueOf(x.getSo2()))
//
//                    //1. util 안거치고 해보기
//                    .pm10Grade(x.getPm10Cai())
//                    .pm25Grade(x.getPm25Cai())
//                    .o3Grade(AirQualityGradeUtil.getO3Grade((Double.valueOf(x.getO3()))))
//                    .no2Grade(AirQualityGradeUtil.getNo2Grade(Double.valueOf(x.getNo2())))
//                    .coGrade(AirQualityGradeUtil.getCoGrade(Double.valueOf(x.getCo())))
//                    .so2Grade(AirQualityGradeUtil.getSo2Grade(Double.valueOf(x.getSo2())))
//                    .build());
//        }
//        return temp;
//    }
//
//    //2단계 : 평균 pm10 구하기
//    private Double averagePm10(List<BusanAirQualityApiDto.Item> items) {
//        double avgPm10 = 0.0;
//        for(int i=0; i<items.size(); i++){
//            avgPm10 += Double.valueOf(items.get(i).getPm10());
//        }
//        System.out.println(items.size());
//        avgPm10 /= items.size();
//        return avgPm10;
//    }
//}
