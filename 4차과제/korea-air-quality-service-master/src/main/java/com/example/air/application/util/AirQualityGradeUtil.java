package com.example.air.application.util;

public class AirQualityGradeUtil {
    private AirQualityGradeUtil() {
    }

    // (성공) TODO: pm25 등급정보 구하는 함수
    public static String getPm25Grade(Double pm25) {
        if(0<=pm25 && pm25<16){
            return "좋음";
        }
        else if(16<=pm25 && pm25<36){
            return "보통";
        }
        else if(36<=pm25 && pm25<76){
            return "나쁨";
        }
        else if(76<=pm25){
            return "매우나쁨";
        }
        else{
            return "측정 오류";
        }
    }

    // (성공) TODO: pm10 등급정보 구하는 함수
    public static String getPm10Grade(Double pm10) {
        if(0<=pm10 && pm10<31){
            return "좋음";
        }
        else if(31<=pm10 && pm10<81){
            return "보통";
        }
        else if(81<=pm10 && pm10<151){
            return "나쁨";
        }
        else if(151<=pm10){
            return "매우나쁨";
        }
        else{
            return null;
        }
    }

    // (성공) TODO: o3 등급정보 구하는 함수
    public static String getO3Grade(Double o3) {
        if(0<=o3 && o3<0.031){
            return "좋음";
        }
        else if(0.031<=o3 && o3<0.091){
            return "보통";
        }
        else if(0.091<=o3 && o3<0.151){
            return "나쁨";
        }
        else if(0.151<=o3){
            return "매우나쁨";
        }
        else{
            return null;
        }
    }

    // (성공) TODO: no2 등급정보 구하는 함수
    public static String getNo2Grade(Double no2) {
        if(0<=no2 && no2<0.031){
            return "좋음";
        }
        else if(0.031<=no2 && no2<0.061){
            return "보통";
        }
        else if(0.061<=no2 && no2<0.201){
            return "나쁨";
        }
        else if(0.201<=no2){
            return "매우나쁨";
        }
        else{
            return null;
        }
    }

    // (성공) TODO: co 등급정보 구하는 함수
    public static String getCoGrade(Double co) {
        if(0<=co && co<2.01){
            return "좋음";
        }
        else if(2.01<=co && co<9.01){
            return "보통";
        }
        else if(9.01<=co && co<15.01){
            return "나쁨";
        }
        else if(15.01<=co){
            return "매우나쁨";
        }
        else{
            return null;
        }
    }

    // (성공) TODO: so2 등급정보 구하는 함수
    public static String getSo2Grade(Double so2) {
        if(0<=so2 && so2<0.021){
            return "좋음";
        }
        else if(0.021<=so2 && so2<0.051){
            return "보통";
        }
        else if(0.051<=so2 && so2<0.151){
            return "나쁨";
        }
        else if(0.151<=so2){
            return "매우나쁨";
        }
        else{
            return null;
        }
    }

    public static String getBusanGrade(Integer Cai){
        if(Cai==1){
            return "좋음";
        }
        else if(Cai==2){
            return "보통";
        }
        else if(Cai==3){
            return "나쁨";
        }
        else if(Cai==4){
            return "매우나쁨";
        }
        else{
            return null;
        }
    }
}
