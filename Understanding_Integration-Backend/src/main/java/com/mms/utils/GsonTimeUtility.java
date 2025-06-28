package com.mms.utils;

import java.lang.reflect.Type;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class GsonTimeUtility {
	private static final String TIME_FORMAT = "yyyy-mm-dd HH:mm:ss";
    private static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat(TIME_FORMAT);

    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final SimpleDateFormat TIMESTAMP_FORMATTER = new SimpleDateFormat(TIMESTAMP_FORMAT);

    
    // Static Gson instance to be reused throughout the application
    private static final Gson gsonInstance;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        // Register the custom TypeAdapter for java.sql.Time
        gsonBuilder.registerTypeAdapter(Time.class, new TimeTypeAdapter());
        gsonBuilder.setPrettyPrinting(); // Optional: for human-readable JSON output
        gsonInstance = gsonBuilder.create();
    }

    /**
     * Returns a pre-configured Gson instance that can correctly serialize and deserialize java.sql.Time.
     * @return A Gson instance.
     */
    public static Gson getGson() {
        return gsonInstance;
    }

    /**
     * Custom TypeAdapter for java.sql.Time.
     * Handles parsing "HH:MM:SS" strings to java.sql.Time and vice-versa.
     */
    public static class TimeTypeAdapter implements JsonSerializer<Time>, JsonDeserializer<Time> {

        @Override
        public JsonElement serialize(Time src, Type typeOfSrc, JsonSerializationContext context) {
            // Convert java.sql.Time object to "HH:MM:SS" string
            return new JsonPrimitive(TIME_FORMATTER.format(src));
        }

        @Override
        public Time deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String timeString = json.getAsString();
            try {
                // Parse "HH:MM:SS" string to java.util.Date, then convert to java.sql.Time
                long parsedTime = TIME_FORMATTER.parse(timeString).getTime();
                return new Time(parsedTime);
            } catch (ParseException e) {
                // Throw a JsonParseException if the format is incorrect
                throw new JsonParseException("Failed parsing '" + timeString + "' as SQL Time; expected format 'HH:MM:SS'.", e);
            }
        }
    }
    
    public static class TimestampTypeAdapter implements JsonSerializer<Timestamp>, JsonDeserializer<Timestamp> {

        @Override
        public JsonElement serialize(Timestamp src, Type typeOfSrc, JsonSerializationContext context) {
            // Convert java.sql.Timestamp object to "yyyy-MM-dd HH:mm:ss" string
            return new JsonPrimitive(TIMESTAMP_FORMATTER.format(src));
        }

        @Override
        public Timestamp deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String timestampString = json.getAsString();
            try {
                // Parse "yyyy-MM-dd HH:mm:ss" string into a java.util.Date, then convert to java.sql.Timestamp
                Date parsedDate = TIMESTAMP_FORMATTER.parse(timestampString);
                return new Timestamp(parsedDate.getTime());
            } catch (ParseException e) {
                // Throw a JsonParseException if the timestamp string format is incorrect
                throw new JsonParseException("Failed parsing '" + timestampString + "' as SQL Timestamp; expected format 'yyyy-MM-dd HH:mm:ss'.", e);
            }
        }
    }
    
}
