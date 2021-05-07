package edu.nwmissouri.dv.twitterStreaming;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class CustomObjectDeserializer implements Deserializer<CustomObject> {
    @Override
    public void configure(Map<String, ?> s, boolean arg1) {
    }

    @Override
    public CustomObject deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        Gson gson = new Gson();
        CustomObject customObject = null;
        TrendingNow trendingNow;

        try {
            String str = new String(bytes, StandardCharsets.UTF_8);
            customObject = gson.fromJson(str,CustomObject.class);

        } catch (Exception e) {

        }
        return customObject;
    }
//
//    @Override
//    public CustomObject deserialize(String s, byte[] bytes) {
//        ObjectMapper mapper = new ObjectMapper();
//        CustomObject customObject = null;
//        TrendingNow trendingNow;
//        try {
//            customObject = mapper.readValue(bytes, CustomObject.class);
//
//        } catch (Exception e) {
//
//        }
//        return customObject.;
//    }

    @Override
    public void close() {

    }
}