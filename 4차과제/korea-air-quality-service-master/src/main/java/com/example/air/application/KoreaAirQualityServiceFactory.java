package com.example.air.application;

// 그래서 각 sido에 해당하는 ApiCaller를 찾아주는
// KoreaAirQualityServiceFactory.getService(sido)를 통해서 ApiCaller를 얻어오고,
// 그때부터 비로소 KoreaAirQualityService 타입으로 정의된 service 의 getAirQualityInfo를 호출할 수 있게됩니다.
// 팩토리 메서드 패턴의 중요한 점은 "생성의 책임을 한곳에 몰아둔다"는 부분입니다.

import com.example.air.infrastructure.api.busan.BusanAirQualityApiCaller;
import com.example.air.infrastructure.api.seoul.SeoulAirQualityApiCaller;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class KoreaAirQualityServiceFactory {
    private final Map<Sido, KoreaAirQualityService> serviceMap = new HashMap<>();
    /**
     * 생성자를 통해 KoreaAirQualityService 를 상속받은 서비스를 key : value 형태로 저장함
     * { seoul : SeoulAirQualityApiCaller, busan : BusanAirQualityApiCaller }
     */
    public KoreaAirQualityServiceFactory(List<KoreaAirQualityService> services){
        for (var service : services){
            serviceMap.put(service.getSido(), service);
        }
    }

    //TODO : 고도화된 형태의 메서드 패턴이 아닌 아래 방법으로 구현해보기.
    //콜러들을 instance 로 만들어 주시면 됩니다
    //return new SeoulAirQualituApiCaller();
    public KoreaAirQualityService getService(Sido sidoCode){
        if (sidoCode == Sido.seoul) {
            return new SeoulAirQualityApiCaller("서울시");
        }

        if(sidoCode == Sido.busan){
            return new BusanAirQualityApiCaller("부산시");
        }
        throw new RuntimeException(sidoCode + "대기질 정보는 아직 준비중입니다.");
    }

//    과제 가이드에서는 비즈니스 로직을 전혀 수정하지 않고 클래스 추가만으로 확장이 가능한
//    조금 더 고도화된 형태로 팩토리 메서드 패턴을 구현하였는데요.
//    1차원 적으로 가장 간단한 형태로 팩토리 클래스를 구현한다고 하면,
//    getService 부분을 이렇게 구현할 수 있습니다. (생성자는 제외하고) -> 윗부분

//    public KoreaAirQualityService getService(Sido sido) {
//        return Optional.of(serviceMap.get(sido))
//                .orElseThrow(() -> new RuntimeException("대기질 정보를 조회할 수 없는 시/도 입니다."));
//    }

}
