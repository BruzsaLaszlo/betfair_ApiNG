import bruzsal.betfair.exceptions.FaultData;
import bruzsal.betfair.api.Operations;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class GsonTest {

    String response = "{\"faultcode\":\"Client\",\"faultstring\":\"ANGX-0015\",\"detail\":{\"APINGException\":{\"requestUUID\":\"ie2-ang26a-prd-11021016-000b565618\",\"errorCode\":\"ACCESS_DENIED\",\"errorDetails\":\"\"},\"exceptionname\":\"APINGException\"}}";

    @Test
    void error() throws IOException {
        FaultData err = Operations.GSON.fromJson(response, FaultData.class);
        assertEquals("ANGX-0015", err.getFaultstring());
        assertEquals("APINGException", err.getDetail().getExceptionname());
        assertNotNull(err.getDetail().getAPINGException());
        assertEquals("ACCESS_DENIED",err.getDetail().getAPINGException().getErrorCode());

    }

    @Test
    void errTest() throws IOException {
//        ObjectMapper om = new ObjectMapper();
//        FaultData root = om.readValue(response, FaultData.class);
//        assertEquals("ACCESS_DENIED",root.getDetail().getAPINGException().getErrorCode());
    }

}
