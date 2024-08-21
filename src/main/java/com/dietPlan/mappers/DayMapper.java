package com.dietPlan.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.dietPlan.dto.DayDto;
import com.dietPlan.dto.FoodDto;
import com.dietPlan.models.Day;
import com.dietPlan.models.Food;

@Mapper
public interface DayMapper {
	DayMapper INSTANCE = Mappers.getMapper(DayMapper.class);
	
	DayDto toDayDto(Day day);
	
	Day toDay(DayDto dayDto);
	
	@Mapping(target = "id", ignore = true)
	void updateFoodRowFromDto(DayDto dayDto, @MappingTarget Day day);
}
