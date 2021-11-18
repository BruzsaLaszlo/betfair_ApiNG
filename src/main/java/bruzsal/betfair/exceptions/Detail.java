package bruzsal.betfair.exceptions;

import bruzsal.betfair.exceptions.APINGException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Detail {

    @SerializedName("APINGException")
    @Expose
    private APINGException aPINGException;
    @SerializedName("exceptionname")
    @Expose
    private String exceptionname;

    public APINGException getAPINGException() {
        return aPINGException;
    }

    public void setAPINGException(APINGException aPINGException) {
        this.aPINGException = aPINGException;
    }

    public String getExceptionname() {
        return exceptionname;
    }

    public void setExceptionname(String exceptionname) {
        this.exceptionname = exceptionname;
    }

}