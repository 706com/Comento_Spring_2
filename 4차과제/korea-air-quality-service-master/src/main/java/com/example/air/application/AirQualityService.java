package com.example.air.application;

// 이번 과제의 키워드는 inteface 와 factory method pattern 이였는데요.
// 인터페이스는 같은 행위를 하는 클래스를 하나의 타입으로 정의(추상화)할 때 사용됩니다.
// 서울시와 부산시 ApiCaller 를 보면 getAirQuality 라는 대기질 정보를 얻어오는 공통된 행위를 하고있고,
// 파라미터와 응답까지 내부 컨버터를 통해 AirQualityInfo 로 맞춰두었는데요.
// 이런 공통 동작은 인터페이스로 추상화하여 묶을 수 있습니다.

import com.example.air.application.util.AirQualityGradeUtil;
import com.example.air.infrastructure.api.busan.BusanAirQualityApiCaller;
import com.example.air.infrastructure.api.busan.BusanAirQualityApiDto;
import com.example.air.infrastructure.api.seoul.SeoulAirQualityApiCaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j      //로그남기기
@Service
@RequiredArgsConstructor
public class AirQualityService {
    private final KoreaAirQualityServiceFactory koreaAirQualityServiceFactory;

//    private final SeoulAirQualityApiCaller seoulAirQualityApiCaller;
//    private final BusanAirQualityApiCaller busanAirQualityApiCaller;

    public AirQualityInfo getAirQualityInfo(Sido sidoCode, String gu) {
        KoreaAirQualityService service = koreaAirQualityServiceFactory.getService(sidoCode);

        var airQualityInfo = service.getAirQualityInfo();
        if(gu != null){
            return airQualityInfo.searchByGu(gu);
        }
        return airQualityInfo;
    }
}


//        if (sidoCode == Sido.seoul) {
//            //타입추론 : var =  변수의 타입을 명시적으로 적어주지 않고도, 컴파일러가 알아서 이 변수의 타입을 대입된 리터럴로 추론하는 것
//            var airQualityInfo = seoulAirQualityApiCaller.getAirQuality();
//            if (gu != null) {
//                return airQualityInfo.searchByGu(gu);
//            }
//            return airQualityInfo;
//        }
//        if(sidoCode == Sido.busan){
//            var airQualityInfo = busanAirQualityApiCaller.getAirQuality();
//
//            if(gu != null){
//                return airQualityInfo.searchByGu(gu);
//            }
//            return airQualityInfo;
//        }
//        throw new RuntimeException(sidoCode + "대기질 정보는 아직 준비중입니다.");



//    public AirQualityInfo getAirQualityInfoSeoul(String site) {
//        //String test = "중구";
//        AirQualityInfo airQualityInfo = seoulAirQualityApiCaller.getAirQuality();
//        List<AirQualityInfo.GuAirQualityInfo> infoSearchGu = new ArrayList<>();
//
//        // (성공) TODO: 자치구 검색 로직 추가 (시간 남는 경우)
//
//        // 로직 설명 : request parameter 를 통해서 값을 받아왔을 때,
//        // airQulaityInfo.Gulist 의 site를 탐색하여,
//        // 찾았을 경우, 객체를 새로 생성하여 리턴.
//        for(AirQualityInfo.GuAirQualityInfo x : airQualityInfo.getGuList()) {
//            if (x.getGu().equals(site)) {
//                infoSearchGu.add(x.builder()
//                        .gu(x.getGu())
//                        .pm10(x.getPm10())
//                        .pm25(x.getPm25())
//                        .o3(x.getO3())
//                        .no2(x.getNo2())
//                        .co(x.getCo())
//                        .so2(x.getSo2())
//                        .pm10Grade(x.getPm10Grade())
//                        .pm25Grade(x.getPm25Grade())
//                        .o3Grade(x.getO3Grade())
//                        .no2Grade(x.getNo2Grade())
//                        .coGrade(x.getCoGrade())
//                        .so2Grade(x.getSo2Grade())
//                        .build());
//
//                return AirQualityInfo.builder()
//                        .sido("서울시")
//                        .sidoPm10Avg(airQualityInfo.getSidoPm10Avg())
//                        .sidoPm10AvgGrade(airQualityInfo.getSidoPm10AvgGrade())
//                        .guList(infoSearchGu)
//                        .build();
//            }
//        }
//        return airQualityInfo;
//    }
//
//    public BusanAirQualityApiDto.GetAirQualityResponse getAirQualityInfoBusan() {
//        BusanAirQualityApiDto.GetAirQualityResponse airQualityInfobusan = busanAirQualityApiCaller.getAirQuality();
//        System.out.println("busan모두 출력");
//
//        return airQualityInfobusan;
//    }
//
//    public AirQualityInfo getAirQualityInfoBusanGu(String site){
//        BusanAirQualityApiDto.GetAirQualityResponse airQualityInfobusanGu = busanAirQualityApiCaller.getAirQuality();
//        List<AirQualityInfo.GuAirQualityInfo> infoSearchBusanGu = new ArrayList<>();
//
//        Double pm10AvgBusan = 0.0;
//        Integer pm10AvgGrade = 0;
//        for(BusanAirQualityApiDto.Item x : airQualityInfobusanGu.getResponse().getBody().getItems().getItems()){
//            if(x.getSite().equals(site)){
//                System.out.println("들어왔숑");
//                infoSearchBusanGu.add(AirQualityInfo.GuAirQualityInfo.builder()
//                        .gu(x.getSite())
//                        .pm10(Integer.valueOf(x.getPm10()))
//                        .pm25(Integer.valueOf(x.getPm25()))
//                        .o3(Double.valueOf(x.getO3()))
//                        .no2(Double.valueOf(x.getNo2()))
//                        .co(Double.valueOf(x.getCo()))
//                        .so2(Double.valueOf(x.getSo2()))
//                        .pm10Grade(AirQualityGradeUtil.getBusanGrade(Integer.valueOf(x.getPm10Cai())))
//                        .pm25Grade(AirQualityGradeUtil.getBusanGrade(Integer.valueOf(x.getPm25Cai())))
//                        .o3Grade(AirQualityGradeUtil.getBusanGrade(Integer.valueOf(x.getO3Cai())))
//                        .no2Grade(AirQualityGradeUtil.getBusanGrade(Integer.valueOf(x.getNo2Cai())))
//                        .coGrade(AirQualityGradeUtil.getBusanGrade(Integer.valueOf(x.getCoCai())))
//                        .so2Grade(AirQualityGradeUtil.getBusanGrade(Integer.valueOf(x.getSo2Cai())))
//                        .build());
//            }
//            pm10AvgBusan += Double.valueOf(x.getPm10());
//            pm10AvgGrade += Integer.valueOf(x.getPm10Cai());
//
//        }
//        pm10AvgBusan /= airQualityInfobusanGu.getResponse().getBody().getItems().getItems().size();
//        String resultPm10AvgBusan = String.format("%.2f",pm10AvgBusan);
//        System.out.println(airQualityInfobusanGu.getResponse().getBody().getItems().getItems().size()); //리스트 개수확인
//        pm10AvgGrade /= airQualityInfobusanGu.getResponse().getBody().getItems().getItems().size();
//
//        return AirQualityInfo.builder()
//                .sido("부산시")
//                .sidoPm10Avg(Double.valueOf(resultPm10AvgBusan))
//                .sidoPm10AvgGrade(AirQualityGradeUtil.getBusanGrade(pm10AvgGrade))
//                .guList(infoSearchBusanGu)
//                .build();
//    }
//}
