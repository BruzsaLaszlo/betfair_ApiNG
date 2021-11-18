package bruzsal.betfair.exceptions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class APINGException extends Throwable {

    @SerializedName("requestUUID")
    @Expose
    private String requestUUID;
    @SerializedName("errorCode")
    @Expose
    private String errorCode;
    @SerializedName("errorDetails")
    @Expose
    private String errorDetails;

    public String getRequestUUID() {
        return requestUUID;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public void printStackTrace() {
        System.err.println(toString());
    }

    @Override
    public String toString() {
        return "APINGException" + '\n' +
                "   requestUUID='" + requestUUID + '\n' +
                "   errorCode='" + errorCode + '\n' +
                "   errorDetails='" + errorDetails + '\n';
    }
}