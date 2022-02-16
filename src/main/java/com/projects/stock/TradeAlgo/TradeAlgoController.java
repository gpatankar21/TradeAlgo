package com.projects.stock.TradeAlgo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TradeAlgoController {
	
	@Autowired
	TradeAlgoService service;
	
	@GetMapping("/api/v1/{date}")
	public TradeDetails getV1TradeDetails(@PathVariable String date) throws ParseException
	{
		SimpleDateFormat dt1 = new SimpleDateFormat("dd-mm-yyyy");
		SimpleDateFormat dt2 = new SimpleDateFormat("mm/dd/yy");
		Date d = dt1.parse(date);
		String inputDate = dt2.format(d);
		TradeDetails details = service.getTradeDetails(inputDate);
		return details;
	}
	
	@RequestMapping(path="/api/v2/{fromDate}/{toDate}", method = RequestMethod.GET)
	public TradeDetails getV2TradeDetails(@PathVariable("fromDate") String fromDate,@PathVariable("toDate") String toDate) throws ParseException
	{
		SimpleDateFormat dt1 = new SimpleDateFormat("dd-mm-yyyy");
		SimpleDateFormat dt2 = new SimpleDateFormat("mm/dd/yy");
		Date d1 = dt1.parse(fromDate);
		Date d2 = dt1.parse(toDate);
		String inputDate1 = dt2.format(d1);
		String inputDate2 = dt2.format(d2);
		TradeDetails details = service.getV2TradeDetails(inputDate1,inputDate2);
		return details;
	}

}
