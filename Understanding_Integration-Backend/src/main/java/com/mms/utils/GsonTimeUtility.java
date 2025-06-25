package com.mms.utils;

import java.sql.Time;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonTimeUtility {
	 private static final Gson gson = new GsonBuilder()
		        .registerTypeAdapter(Time.class, new TimeTypeAdapter())
		        .create();

		    public static Gson getGson() {
		        return gson;
		    }
}
