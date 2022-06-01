package aping.entities;

import aping.exceptions.ApiNgException;
import com.fasterxml.jackson.annotation.JsonProperty;

public record FaultData(

        String faultcode,
        String faultstring,
        Detail detail

) {

    private record Detail(
            @JsonProperty("APINGException")
            ApiNgExceptionDto apiNgExceptionDto,
            String exceptionname
    ) {
    }

    private record ApiNgExceptionDto(
            String errorCode,
            String requestUUID,
            String errorDetails
    ) {
    }

    public ApiNgException apiNgException() {
        return new ApiNgException(
                detail.apiNgExceptionDto.errorCode,
                detail.apiNgExceptionDto.requestUUID,
                detail.apiNgExceptionDto.errorDetails);
    }
}





