package aping.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Component;

@Component
public class JsonMapper extends ObjectMapper {

    public JsonMapper() {
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
        findAndRegisterModules();
    }

    @Override
    public String writeValueAsString(Object value) {
        try {
            return super.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Can not write value as a String: " + value, e);
        }
    }

    @Override
    public <T> T readValue(String content, TypeReference<T> valueTypeRef) {
        try {
            return super.readValue(content, valueTypeRef);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Cant not serialize String: " + content, e);
        }
    }

    @Override
    public <T> T readValue(String content, Class<T> valueType) {
        try {
            return super.readValue(content, valueType);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Cant not serialize String: " + content, e);
        }
    }
}
