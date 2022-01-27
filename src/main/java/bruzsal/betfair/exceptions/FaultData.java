package bruzsal.betfair.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FaultData(

        String faultcode,
        String faultstring,
        Detail detail

) {

    public record Detail(

            @JsonProperty("APINGException")
            ApiNgException APINGException,
            String exceptionname

    ) {
    }

}





