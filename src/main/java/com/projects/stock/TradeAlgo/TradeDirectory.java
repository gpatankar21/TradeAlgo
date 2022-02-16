package com.projects.stock.TradeAlgo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.tomcat.util.json.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class TradeDirectory {
	
	
	public JSONObject getData(String inputDate)
	{
		JSONParser parser = new JSONParser();
		
		Object obj=null;
		try {
			obj = parser.parse(new FileReader("test.json"));
		} catch (IOException | org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONArray jsonArray = (JSONArray) obj;
		
		for(Object o : jsonArray)
		{
			JSONObject trade = (JSONObject) o;
			String date = (String) trade.get("Date");
			if(inputDate.equals(date))
				return trade;
		}
		
		return null;
	}

}
