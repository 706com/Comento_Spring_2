package com.example.air.infrastructure.api.seoul;

import com.example.air.application.AirQualityInfo;
import com.example.air.application.util.AirQualityGradeUtil;
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
public class SeoulAirQualityApiCaller {
    private final SeoulAirQualityApi seoulAirQualityApi;

    public SeoulAirQualityApiCaller(@Value("${api.seoul.base-url}") String baseUrl) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();

        this.seoulAirQualityApi = retrofit.create(SeoulAirQualityApi.class);
    }

    public AirQualityInfo getAirQuality() {
        try {
            var call = seoulAirQualityApi.getAirQuality();
            var response = call.execute().body();

            if (response == null || response.getResponse() == null) {
                throw new RuntimeException("[seoul] getAirQuality 응답값이 존재하지 않습니다.");
            }

            // 요청이 성공하는 경우 응답값 AirQualityInfo로 변환하여 리턴
            if (response.getResponse().isSuccess()) {
                log.info(response.toString());
                return convert(response);
            }

            throw new RuntimeException("[seoul] getAirQuality 응답이 올바르지 않습니다. header=" + response.getResponse().getResult());

        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("[seoul] getAirQuality API error 발생! errorMessage=" + e.getMessage());
        }
    }

    // 서울시 공공 API에서 조회한 정보를 AirQualityInfo로 변환해주는 함수
    private AirQualityInfo convert(SeoulAirQualityApiDto.GetAirQualityResponse response) {
        List<SeoulAirQualityApiDto.Row> rows = response.getResponse().getRows();
        Double sidoPm10Avg = averagePm10(rows);
        String sidoPm10AvgGrade = AirQualityGradeUtil.getPm10Grade(sidoPm10Avg);
        List<AirQualityInfo.GuAirQualityInfo> guList = convert(rows);

        for(int i=0; i<rows.size(); i++){
            System.out.println(rows.get(i));
        }

        return AirQualityInfo.builder()
                .sido("서울시")
                .sidoPm10Avg(sidoPm10Avg)
                .sidoPm10AvgGrade(sidoPm10AvgGrade)
                .guList(guList)
                .build();
    }

    // (성공) TODO: 자치구 목록 정보 변환 함수
    private List<AirQualityInfo.GuAirQualityInfo> convert(List<SeoulAirQualityApiDto.Row> rows) {
        List<AirQualityInfo.GuAirQualityInfo> temp = new ArrayList<>();

        // for-each 문 활용
        // Row 의 값들을 GuAirQualityInfo의 형태로 변환시키기.
        for(SeoulAirQualityApiDto.Row x : rows){
            temp.add(AirQualityInfo.GuAirQualityInfo.builder()
                    .gu(x.getSite())
                    .pm10(x.getPm10())
                    .pm25(x.getPm25())
                    .o3(x.getO3())
                    .no2(x.getNo2())
                    .co(x.getCo())
                    .so2(x.getSo2())
                    .pm10Grade(AirQualityGradeUtil.getPm10Grade(Double.valueOf(x.getPm10())))
                    .pm25Grade(AirQualityGradeUtil.getPm25Grade(Double.valueOf(x.getPm25())))
                    .o3Grade(AirQualityGradeUtil.getO3Grade((Double.valueOf(x.getO3()))))
                    .no2Grade(AirQualityGradeUtil.getNo2Grade(Double.valueOf(x.getNo2())))
                    .coGrade(AirQualityGradeUtil.getCoGrade(Double.valueOf(x.getCo())))
                    .so2Grade(AirQualityGradeUtil.getSo2Grade(Double.valueOf(x.getSo2())))
                    .build());
        }
        return temp;

//        for(int i=0; i<rows.size(); i++){
//            temp.add(AirQualityInfo.GuAirQualityInfo.builder()
//                    .gu(rows.get(i).getSite())
//                    .pm10(rows.get(i).getPm10())
//                    .pm25(rows.get(i).getPm25())
//                    .o3(rows.get(i).getO3())
//                    .no2(rows.get(i).getNo2())
//                    .co(rows.get(i).getCo())
//                    .so2(rows.get(i).getSo2())
//                    .pm10Grade(AirQualityGradeUtil.getPm10Grade(Double.valueOf(rows.get(i).getPm10())))
//                    .pm25Grade(AirQualityGradeUtil.getPm25Grade(Double.valueOf(rows.get(i).getPm25())))
//                    .o3Grade(AirQualityGradeUtil.getO3Grade((Double.valueOf(rows.get(i).getO3()))))
//                    .no2Grade(AirQualityGradeUtil.getNo2Grade(Double.valueOf(rows.get(i).getNo2())))
//                    .coGrade(AirQualityGradeUtil.getCoGrade(Double.valueOf(rows.get(i).getCo())))
//                    .so2Grade(AirQualityGradeUtil.getSo2Grade(Double.valueOf(rows.get(i).getSo2())))
//                    .build());
//        }

    }

    // (성공)TODO: 자치구 목록으로 pm10(미세먼지) 평균값을 구하는 함수
    private Double averagePm10(List<SeoulAirQualityApiDto.Row> rows) {
        double avgPm10 = 0.0;
        for(int i=0; i<rows.size(); i++){
            avgPm10 += rows.get(i).getPm10();
        }
        System.out.println(rows.size());
        avgPm10 /= rows.size();
        return avgPm10;
    }
}
