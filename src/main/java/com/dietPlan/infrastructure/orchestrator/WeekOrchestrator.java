package com.dietPlan.infrastructure.orchestrator;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dietPlan.domain.mapper.WeekMapper;
import com.dietPlan.domain.model.Day;
import com.dietPlan.domain.model.Week;
import com.dietPlan.domain.repository.DayRepository;
import com.dietPlan.domain.repository.WeekRepository;
import com.dietPlan.infrastructure.service.CalculateStats;
import com.dietPlan.web.dto.WeekDto;

@Component
public class WeekOrchestrator {
	private WeekRepository weekRepository;
	private DayRepository dayRepository;
	private CalculateStats calculateStats;
	
	public WeekOrchestrator(WeekRepository weekRepository, DayRepository dayRepository, CalculateStats calculateStats) {
		this.weekRepository = weekRepository;
		this.dayRepository = dayRepository;
		this.calculateStats = calculateStats;
	}
	
	public WeekDto getWeekStats(Long weekId) {
		Week returnedWeekObject = weekRepository.findById(weekId).orElseThrow(() -> new RuntimeException("Week object not found in database in getWeekStats method with id = " + weekId));
		
		calculateStats.calculateTotalWeekStats(returnedWeekObject);
		
		weekRepository.save(returnedWeekObject);
		
		return WeekMapper.INSTANCE.toWeekDto(returnedWeekObject);
	}	

	public WeekDto addDaysToWeek(Long weekId, List<Long> dayIds) {
		Week weekBeingUpdated = weekRepository.findById(weekId).orElseThrow(() -> new RuntimeException("Week object not found in database in addDaysToWeek method with id = " + weekId));
		List<Day> daysAddedToWeekObject = dayRepository.findAllById(dayIds);
		
		for(Day day : daysAddedToWeekObject) {
			calculateStats.calculateTotalDayStats(day);
			day.setWeek(weekBeingUpdated);
		}
		
		weekBeingUpdated.getDaysInList().addAll(daysAddedToWeekObject);
		calculateStats.calculateTotalWeekStats(weekBeingUpdated);
		weekRepository.save(weekBeingUpdated);
		return WeekMapper.INSTANCE.toWeekDto(weekBeingUpdated);
	}
	
	public WeekDto deleteDaysInWeek(Long weekId, List<Long> dayIds) {
		Week weekBeingUpdated = weekRepository.findById(weekId).orElseThrow(() -> new RuntimeException("Week object not found in database in addDaysToWeek method with id = " + weekId));
		List<Day> daysDeletedFromWeekObject = dayRepository.findAllById(dayIds);
		
		weekBeingUpdated.getDaysInList().removeAll(daysDeletedFromWeekObject);
		calculateStats.calculateTotalWeekStats(weekBeingUpdated);
		weekRepository.save(weekBeingUpdated);
		return WeekMapper.INSTANCE.toWeekDto(weekBeingUpdated);
	}
}
