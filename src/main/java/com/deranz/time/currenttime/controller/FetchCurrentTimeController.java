package com.deranz.time.currenttime.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FetchCurrentTimeController {
	
	@RequestMapping("/deranz/getTime")
	public String getCurrentTime() {
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(cal.getTime());
	}

}
