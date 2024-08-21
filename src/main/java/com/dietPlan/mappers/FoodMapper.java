package com.dietPlan.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.dietPlan.dto.FoodDto;
import com.dietPlan.models.Food;

@Mapper
public interface FoodMapper {
	FoodMapper INSTANCE = Mappers.getMapper(FoodMapper.class);
	
	FoodDto toFoodDto(Food food);
	
	Food toFood(FoodDto foodDto);
	
	@Mapping(target = "id", ignore = true)
	void updateFoodRowFromDto(FoodDto foodDto, @MappingTarget Food food);
}
