import com.betfair.aping.FaultData;
import com.betfair.aping.api.ApiNgRescriptOperations;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class GsonTest {

    String response = "{\"faultcode\":\"Client\",\"faultstring\":\"ANGX-0015\",\"detail\":{\"APINGException\":{\"requestUUID\":\"ie2-ang26a-prd-11021016-000b565618\",\"errorCode\":\"ACCESS_DENIED\",\"errorDetails\":\"\"},\"exceptionname\":\"APINGException\"}}";

    @Test
    void error() throws IOException {
        FaultData err = ApiNgRescriptOperations.gson.fromJson(response, FaultData.class);
        assertEquals("ANGX-0015", err.faultstring);
        assertEquals("APINGException", err.detail.exceptionname);
        assertNotNull(err.detail.APINGException);
        assertEquals("ACCESS_DENIED",err.detail.APINGException.getErrorCode());

    }

    @Test
    void errTest() throws IOException {
        ObjectMapper om = new ObjectMapper();
        FaultData root = om.readValue(response, FaultData.class);
        assertEquals("ACCESS_DENIED",root.detail.APINGException.getErrorCode());
    }

}
