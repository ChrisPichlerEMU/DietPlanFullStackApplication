package com.dietPlan.infrastructure.service;

import org.springframework.stereotype.Service;

import com.dietPlan.domain.mapper.WeekMapper;
import com.dietPlan.domain.model.Week;
import com.dietPlan.domain.repository.WeekRepository;
import com.dietPlan.web.dto.WeekDto;

@Service
public class WeekService {
	
	private WeekRepository weekRepository;
	
	public WeekService(WeekRepository weekRepository) {
		this.weekRepository = weekRepository;
	}
	
	public WeekDto addWeek(WeekDto weekDto) {
		Week weekModel = WeekMapper.INSTANCE.toWeek(weekDto);
		Week week = weekRepository.save(weekModel);
		return WeekMapper.INSTANCE.toWeekDto(week);
	}
	
	public WeekDto deleteWeek(Long weekId) {
		Week weekToBeSoftDeleted = weekRepository.findById(weekId).orElseThrow(() -> new RuntimeException("Week object not found in database in deleteWeek method with id = " + weekId));
		weekToBeSoftDeleted.setDeleted(true);
		weekRepository.save(weekToBeSoftDeleted);
		return WeekMapper.INSTANCE.toWeekDto(weekToBeSoftDeleted);
	}
}
