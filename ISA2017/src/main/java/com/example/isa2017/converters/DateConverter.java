package com.example.isa2017.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateConverter {

	public String dateToString(Date d){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String date = sdf.format(d); 
		return date;
	}
	
	public String timeToString(Date d){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String date = sdf.format(d); 
		return date;
	}
	
	public Date stringToDate(String s) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date date = sdf.parse(s);
		return date;
	}
	
/*	public String calendarToString(Calendar c){
		String runtime = c.get(Calendar.HOUR_OF_DAY) + "h " + c.get(Calendar.MINUTE) + c.get(Calendar.SECOND) + "";
		return runtime;
	}
	
	public Calendar stringToCalendar(String s){
		Calendar runtime = 
	}*/
	
	public Date addRuntime(Date date, int runtime){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, runtime);
		
		return calendar.getTime();
	}
	
}
