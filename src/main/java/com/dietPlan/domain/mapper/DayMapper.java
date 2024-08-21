package com.dietPlan.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.dietPlan.domain.model.Day;
import com.dietPlan.domain.model.Food;
import com.dietPlan.web.dto.DayDto;
import com.dietPlan.web.dto.FoodDto;

@Mapper
public interface DayMapper {
	DayMapper INSTANCE = Mappers.getMapper(DayMapper.class);
	
	DayDto toDayDto(Day day);
	
	Day toDay(DayDto dayDto);
	
	@Mapping(target = "id", ignore = true)
	void updateFoodRowFromDto(DayDto dayDto, @MappingTarget Day day);
}
