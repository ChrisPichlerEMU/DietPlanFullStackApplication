package com.dietPlan.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.dietPlan.domain.model.Week;
import com.dietPlan.web.dto.WeekDto;

@Mapper
public interface WeekMapper {
	WeekMapper INSTANCE = Mappers.getMapper(WeekMapper.class);
	
	WeekDto toWeekDto(Week week);
	
	Week toWeek(WeekDto weekDto);
}
