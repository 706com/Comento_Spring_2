package com.example.air.interfaces.api;

import com.example.air.application.AirQualityService;
import com.example.air.application.AirQualityInfo;
import com.example.air.application.Sido;
import com.example.air.infrastructure.api.busan.BusanAirQualityApiDto;
import com.example.air.infrastructure.api.seoul.SeoulAirQualityApiCaller;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
//RequestMapping 은 클래스 레벨에서 사용. (클래스와 메서드 수준에서 모두 사용할 수 있다.)
// @RequestMapping("/api") // localhost:8080/api 가 매칭
@RequestMapping("/api/v1/air-quality")
public class AirQualityApiController {
    private final AirQualityService airQualityService;

    // (성공) TODO: 시도와 구정보를 parameter 로 받는 GET API 작성


    //GetMapping 은 메소드에만 적용.
    //@PathVariable = url 변수명
    //@RequestParam = url 파라미터 ?gu="중구" (디폴트 선택 가능)
    @GetMapping("/{sidoCode}")
    public AirQualityInfo getAirQualityInfo(@PathVariable("sidoCode") Sido sidoCode,
                                            @RequestParam(value = "gu", required = false) String gu) {
        return airQualityService.getAirQualityInfo(sidoCode, gu);
    }
}

//    @GetMapping("/seoul")     //localhost:8080
//    public AirQualityInfo getAirQualityInfo() {
//        return airQualityService.getAirQualityInfo();
//    }


//    public AirQualityInfo getAirQualityInfo() {
//        return airQualityService.getAirQualityInfo();
//    }



//    @GetMapping("/api/v1/air-quality/")
//    public AirQualityInfo getAirQualityInfo(){
//        return airQualityService.getAirQualityInfo();
//
//        public AirQualityInfo getAirQualityInfo()
//    }






//    @GetMapping("/busan")     //localhost:8080
//    public BusanAirQualityApiDto.GetAirQualityResponse getAirQualityInfoBusan() {
//        return airQualityService.getAirQualityInfoBusan();
//    }
//
//    @GetMapping("/api/v1/air-quality/busan")
//    public AirQualityInfo getAirQualityInfoBusanGu(
//            @RequestParam(value="gu", required = false) String site){
//
//        return airQualityService.getAirQualityInfoBusanGu(site);
//    }
//}

