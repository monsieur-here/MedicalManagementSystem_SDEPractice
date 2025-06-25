package com.mms.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Time;

public class JsonUtil {
	private static final Gson gson = new Gson();

	public static <T> T fromJson(Reader reader, Class<T> clazz) {
		return gson.fromJson(reader, clazz);
	}

	public static String toJson(Object obj) {
		return gson.toJson(obj);
	}
	
	  public static void writeJsonResponse(HttpServletResponse resp, Object data) throws IOException {
	        resp.setContentType("application/json");
	        resp.setCharacterEncoding("UTF-8");
	        PrintWriter out = resp.getWriter();
	        out.print(gson.toJson(data));
	        out.flush();
	  }
	  
	  
}
