package com.betfair.aping;


 import com.betfair.aping.exceptions.ApiNgException;


public class FaultData{
    public String faultcode;
    public String faultstring;
    public Detail detail;

    public FaultData() {
    }

    public class Detail{

        public ApiNgException APINGException;
        public String exceptionname;

        public Detail() {
        }

    }
}





