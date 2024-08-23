package com.dietPlan.infrastructure.orchestrator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.dietPlan.domain.mapper.DayMapper;
import com.dietPlan.domain.model.Day;
import com.dietPlan.domain.model.Food;
import com.dietPlan.domain.repository.DayRepository;
import com.dietPlan.domain.repository.FoodRepository;
import com.dietPlan.infrastructure.service.CalculateStats;
import com.dietPlan.web.dto.DayDto;

@Component
public class DayOrchestrator {
	
	private DayRepository dayRepository;
	private FoodRepository foodRepository;
	private CalculateStats calculateStats;

	public DayOrchestrator(DayRepository dayRepository, FoodRepository foodRepository, CalculateStats calculateStats) {
		this.dayRepository = dayRepository;
		this.foodRepository = foodRepository;
		this.calculateStats = calculateStats;
	}
	
	public DayDto getDayStats(Long dayId) {
		Day returnedDayObject = dayRepository.findById(dayId).orElseThrow(() -> new RuntimeException("Day object not found in database in getDayStats method with id = " + dayId));
		calculateStats.calculateTotalDayStats(returnedDayObject);
		
		dayRepository.save(returnedDayObject);
		
		return DayMapper.INSTANCE.toDayDto(returnedDayObject);
	}
	
	public DayDto addFoodsToDay(Long dayId, List<Long> foodIds) {
		Day dayBeingUpdated = dayRepository.findById(dayId).orElseThrow(() -> new RuntimeException ("Day object not found in database in addFoodsToDay method with id = " + dayId));
		List<Food> foodsAddedToDayObject = new ArrayList<>();
		for(Long foodId : foodIds) {
			Food nextFoodObjectAdded = foodRepository.findById(foodId).orElseThrow(() -> new RuntimeException("Food object not found in database in addFoodsToDay method with id = " + foodId));
			foodsAddedToDayObject.add(nextFoodObjectAdded);
			dayBeingUpdated.getFoodIdsInFoodsList().add(foodId);
		}
		dayBeingUpdated.getFoods().addAll(foodsAddedToDayObject);
		calculateStats.calculateTotalDayStats(dayBeingUpdated);	
		dayRepository.save(dayBeingUpdated);
		return DayMapper.INSTANCE.toDayDto(dayBeingUpdated);
	}
	
	public DayDto deleteFoodsInDay(Long dayId, List<Long> foodIds) {
		Day dayBeingUpdated = dayRepository.findById(dayId).orElseThrow(() -> new RuntimeException ("Day object not found in database in deleteFoodsInDay method with id = " + dayId));
		for(Long foodId : foodIds) {
			Food nextFoodObjectRemoved = foodRepository.findById(foodId).orElseThrow(() -> new RuntimeException("Food object not found in database in deleteFoodsInDay method with id = " + foodId));
			dayBeingUpdated.getFoods().remove(nextFoodObjectRemoved);
			dayBeingUpdated.getFoodIdsInFoodsList().remove(foodId);
		}
		calculateStats.calculateTotalDayStats(dayBeingUpdated);	
		dayRepository.save(dayBeingUpdated);
		return DayMapper.INSTANCE.toDayDto(dayBeingUpdated);
	}
}
