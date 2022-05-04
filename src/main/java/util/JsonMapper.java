package util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonMapper extends ObjectMapper {

    public JsonMapper() {
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
        findAndRegisterModules();
    }

}
