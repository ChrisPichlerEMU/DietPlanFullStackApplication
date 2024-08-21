package com.dietPlan.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.dietPlan.dto.WeekDto;
import com.dietPlan.models.Week;

@Mapper
public interface WeekMapper {
	WeekMapper INSTANCE = Mappers.getMapper(WeekMapper.class);
	
	WeekDto toWeekDto(Week week);
	
	Week toWeek(WeekDto weekDto);
}
