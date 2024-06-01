package com.justDice.spring.mssql.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justDice.spring.mssql.model.CalendarEntry;
import com.justDice.spring.mssql.repository.CalendarEntryRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class CalendarController {

	@Autowired
	CalendarEntryRepository calendarRepository;

	@GetMapping("/getAllCalendar")
	public ResponseEntity<List<CalendarEntry>> getAllTutorials() {
		try {
			List<CalendarEntry> calendars = new ArrayList<CalendarEntry>();
			calendarRepository.findByStopDateAfter(LocalDateTime.now()).forEach(calendars::add);

			if (calendars.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(calendars, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/calendar")
	public ResponseEntity<CalendarEntry> createCalendarEntry(@RequestBody CalendarEntry calendar) {
		try {
			CalendarEntry calendarEntry = calendarRepository.save(new CalendarEntry());
			return new ResponseEntity<>(calendarEntry, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
