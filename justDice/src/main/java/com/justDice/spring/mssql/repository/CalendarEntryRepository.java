package com.justDice.spring.mssql.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justDice.spring.mssql.model.CalendarEntry;

public interface CalendarEntryRepository extends JpaRepository<CalendarEntry, Long> {
  List<CalendarEntry> findByStopDateAfter(LocalDateTime date);
	}
