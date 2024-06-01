package com.justDice.spring.mssql.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.justDice.spring.mssql.model.CalendarEntry;

@DataJpaTest
public class CalendarEntryRepositoryTest {

	@Autowired
	private CalendarEntryRepository calendarEntryRepository;

	@Test
	public void testGetActiveEntries() {
		CalendarEntry entry = new CalendarEntry();
		entry.setStartDate(LocalDateTime.now().minusDays(1));
		entry.setStopDate(LocalDateTime.now().minusDays(1));
		entry.setCreatedAt(LocalDateTime.now());
		entry.setUpdatedAt(LocalDateTime.now());
		CalendarEntry entry2 = new CalendarEntry();

		calendarEntryRepository.saveAll(List.of(entry, entry2));

		List<CalendarEntry> activeEntries = calendarEntryRepository.findByStopDateAfter(LocalDateTime.now());

		assertEquals(1, activeEntries.size());
	}
}