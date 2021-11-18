package bruzsal.betfair.exceptions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class FaultData {

    @SerializedName("faultcode")
    @Expose
    private String faultcode;
    @SerializedName("faultstring")
    @Expose
    private String faultstring;
    @SerializedName("detail")
    @Expose
    private Detail detail;

    public String getFaultcode() {
        return faultcode;
    }

    public void setFaultcode(String faultcode) {
        this.faultcode = faultcode;
    }

    public String getFaultstring() {
        return faultstring;
    }

    public void setFaultstring(String faultstring) {
        this.faultstring = faultstring;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

}





