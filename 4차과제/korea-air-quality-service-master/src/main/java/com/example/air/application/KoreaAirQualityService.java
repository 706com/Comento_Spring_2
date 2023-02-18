package com.example.air.application;
// 인터페이스는 같은 행위를 하는 클래스를 하나의 타입으로 정의(추상화)할 때 사용됩니다.
// 서울시와 부산시 ApiCaller 를 보면 getAirQuality 라는 대기질 정보를 얻어오는 공통된 행위를 하고있고,
// 파라미터와 응답까지 내부 컨버터를 통해 AirQualityInfo 로 맞춰두었는데요.
// 이런 공통 동작은 인터페이스로 추상화하여 묶을 수 있습니다.

// 첨부드린 과제의 KoreaAirQualityService 가 그 인터페이스이고 타입과 메소드명만 정의가 되어있습니다.
// 이것을 각 ApiCaller들이 상속받아 getAirQualityInfo 를 구현하는 형태로 되었습니다.

// 이 상태에서 저희는 KoreaAirQualityService만을 바라보고 로직을 활용할 수 있게 되는데

// 이것만 가지고는 KoreaAirQualityService.getAirQualityInfo() 이런 형태로 단순 호출은 불가능 합니다.
// 구현하고 있는 구현체가 2개이기 때문에 코드에서 어느것을 타야할 지 모르기 때문이죠.

// KoreaAirQualityServiceFactory.getService(sido)를 통해서 사용 가능합니다.

public interface KoreaAirQualityService {

    Sido getSido();

    AirQualityInfo getAirQualityInfo();
}
