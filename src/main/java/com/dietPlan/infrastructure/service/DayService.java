package com.dietPlan.infrastructure.service;

import org.springframework.stereotype.Service;

import com.dietPlan.domain.mapper.DayMapper;
import com.dietPlan.domain.model.Day;
import com.dietPlan.domain.repository.DayRepository;
import com.dietPlan.web.dto.DayDto;

@Service
public class DayService {

	private DayRepository dayRepository;
	
	public DayService(DayRepository dayRepository) {
		this.dayRepository = dayRepository;
	}
	
	public DayDto addDay(DayDto dayDto) {
		Day dayModel = DayMapper.INSTANCE.toDay(dayDto);
		Day day = dayRepository.save(dayModel);
		return DayMapper.INSTANCE.toDayDto(day);
	}
	
	public DayDto deleteDay(Long dayId) {
		Day dayToBeSoftDeleted = dayRepository.findById(dayId).orElseThrow(() -> new RuntimeException("Day object not found in database in deleteDay method with id = " + dayId));
		dayToBeSoftDeleted.setDeleted(true);
		dayRepository.save(dayToBeSoftDeleted);
		return DayMapper.INSTANCE.toDayDto(dayToBeSoftDeleted);
	}
}
