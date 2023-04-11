package ru.yandex.practicum.filmorate.adapter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Duration;


public class CustomDurationDeserializer extends JsonDeserializer<Duration> {

    @Override
    public Duration deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
       if (jsonParser.getValueAsLong() <0) {
           return Duration.ZERO;
       }

        return Duration.ofMinutes(jsonParser.getValueAsLong());
    }
}
