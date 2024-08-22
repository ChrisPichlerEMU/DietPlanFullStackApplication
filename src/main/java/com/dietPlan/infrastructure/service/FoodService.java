package com.dietPlan.infrastructure.service;

import org.springframework.stereotype.Service;

import com.dietPlan.domain.mapper.FoodMapper;
import com.dietPlan.domain.model.Food;
import com.dietPlan.domain.repository.FoodRepository;
import com.dietPlan.web.dto.FoodDto;

@Service
public class FoodService {
//addFood(), editFood(), deleteFood(), getFoodStats()
	private FoodRepository foodRepository;
	
	public FoodService(FoodRepository foodRepository) {
		this.foodRepository = foodRepository;
	}
	
	public FoodDto addFood(FoodDto foodDto) {
		Food foodModel = FoodMapper.INSTANCE.toFood(foodDto);
		Food food = foodRepository.save(foodModel);
		return FoodMapper.INSTANCE.toFoodDto(food);
	}
	
	public FoodDto getFoodStats(Long foodId) {
		Food returnedFoodObject = foodRepository.findById(foodId).orElseThrow(() -> new RuntimeException("Food object not found in database in getFoodStats method with id = " + foodId));
		return FoodMapper.INSTANCE.toFoodDto(returnedFoodObject);
	}
	
	public FoodDto editFood(Long foodId, FoodDto foodDto) {
		Food foodToBeEdited = foodRepository.findById(foodId).orElseThrow(() -> new RuntimeException("Food object not found in database in editFood method with id = " + foodId));;
		FoodMapper.INSTANCE.updateFoodRowFromDto(foodDto, foodToBeEdited);
		foodRepository.save(foodToBeEdited);
		return FoodMapper.INSTANCE.toFoodDto(foodToBeEdited);
	}
	
	public FoodDto deleteFood(Long foodId) {
		Food foodToBeSoftDeleted = foodRepository.findById(foodId).orElseThrow(() -> new RuntimeException("Food object not found in database in deleteFood method with id = " + foodId));
		foodToBeSoftDeleted.setDeleted(true);
		foodRepository.save(foodToBeSoftDeleted);
		return FoodMapper.INSTANCE.toFoodDto(foodToBeSoftDeleted);
	}
}
