package com.projects.stock.TradeAlgo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeAlgoService {
	
	@Autowired
	TradeDirectory directory;
	
	public TradeDetails getTradeDetails(String inputDate)
	{
		JSONObject obj = directory.getData(inputDate);
		String date = (String)obj.get("Date");
		double open = (Double)(obj.get("Open"));
		double high = (Double)(obj.get("High"));
		double low = (Double)(obj.get("Low"));
		double close = (Double)(obj.get("Close"));
		long volume = (Long)(obj.get("Volume"));
		
		double profit = (high-open>open-low)?high-open:open-low;
		String strategy = (high-open>open-low)?"Long":"Short";
		
		
		TradeDetails details = new TradeDetails();
		details.setStrategy(strategy);
		details.setProfit(profit);
		details.setTime((strategy.equals("Long")?"High":"Low"));
		
		return details;
	}
	
	
	public TradeDetails getV2TradeDetails(String fromDate, String toDate)
	{
		
		TradeDetails details = new TradeDetails();
		
		JSONObject fromObj = directory.getData(fromDate);
		JSONObject toObj = directory.getData(toDate);
		
		List<Double> highs = new ArrayList<>();
		List<Double> lows = new ArrayList<>();
		List<Double> opens = new ArrayList<>();
		
		double maxLongProfit = Double.MIN_VALUE;
		double longProfit = 0.0;
		double maxShortProfit = Double.MIN_VALUE;
		double shortProfit = 0.0;
		double maxProfit = 0.0;
		
		SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yy");
		Date d1=null;
		Date d2=null;
		try {
			d1 = sdf.parse(fromDate);
			d2 = sdf.parse(toDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		Calendar cal = Calendar.getInstance();
		cal.setTime(d1);
		
		while(d1.compareTo(d2)<=0)
		{
			highs.add((Double) directory.getData(fromDate).get("High"));
			lows.add((Double) directory.getData(fromDate).get("Low"));
			opens.add((Double) directory.getData(fromDate).get("Open"));
			cal.add(Calendar.DAY_OF_MONTH, 1);
			d1=cal.getTime();
			fromDate=sdf.format(d1);	
		}
		
		for(Double open : opens)
		{
			for(Double high : highs)
			{
				longProfit = high - open;
				maxLongProfit = (maxLongProfit>longProfit)?maxLongProfit:longProfit;
			}
			
			for(Double low : lows)
			{
				shortProfit = open - low;
				maxShortProfit = (maxShortProfit>shortProfit)?maxShortProfit:shortProfit;
			}
			
		}
		
		maxProfit=Math.max(maxShortProfit, maxLongProfit);
		String strategy = (maxShortProfit>maxLongProfit?"Short":"Long");
		
		details.setProfit(maxProfit);
		details.setStrategy(strategy);
		
		return details;
	}

}
