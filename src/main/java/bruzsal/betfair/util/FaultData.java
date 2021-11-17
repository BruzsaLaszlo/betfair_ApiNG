package bruzsal.betfair.util;


import bruzsal.betfair.exceptions.ApiNgException;


public class FaultData {
    private String faultcode;
    private String faultstring;
    private Detail detail;

    public String getFaultcode() {
        return faultcode;
    }

    public String getFaultstring() {
        return faultstring;
    }

    public Detail getDetail() {
        return detail;
    }

    public FaultData() {
    }

    public class Detail {

        private ApiNgException APINGException;
        private String exceptionname;

        public Detail() {
        }

        public ApiNgException getAPINGException() {
            return APINGException;
        }

        public String getExceptionname() {
            return exceptionname;
        }
    }
}





