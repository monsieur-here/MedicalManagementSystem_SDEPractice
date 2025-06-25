package com.mms.utils;

import java.io.IOException;
import java.sql.Time;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class TimeTypeAdapter extends TypeAdapter<Time> {
    @Override
    public void write(JsonWriter out, Time value) throws IOException {
        out.value(value.toString()); // outputs "HH:mm:ss"
    }

    @Override
    public Time read(JsonReader in) throws IOException {
        return Time.valueOf(in.nextString()); // expects "HH:mm:ss"
    }
}
