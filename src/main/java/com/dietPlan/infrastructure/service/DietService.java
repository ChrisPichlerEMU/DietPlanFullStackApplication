package com.dietPlan.infrastructure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;

import com.dietPlan.domain.mapper.DayMapper;
import com.dietPlan.domain.mapper.FoodMapper;
import com.dietPlan.domain.mapper.WeekMapper;
import com.dietPlan.domain.model.Day;
import com.dietPlan.domain.model.Food;
import com.dietPlan.domain.model.Week;
import com.dietPlan.domain.repository.DayRepository;
import com.dietPlan.domain.repository.FoodRepository;
import com.dietPlan.domain.repository.WeekRepository;
import com.dietPlan.web.dto.*;
import com.dietPlan.web.dto.DayDto;
import com.dietPlan.web.dto.FoodDto;
import com.dietPlan.web.dto.WeekDto;

@Service
//Does the calculating of macro/micro nutrients
public class DietService {
	
	private FoodRepository foodRepository;
	private DayRepository dayRepository;
	private WeekRepository weekRepository;
	private CalculateStats calculateStats;
	
	public DietService(FoodRepository foodRepository, DayRepository dayRepository, WeekRepository weekRepository, CalculateStats calculateStats) {
		this.foodRepository = foodRepository;
		this.dayRepository = dayRepository;
		this.weekRepository = weekRepository;
		this.calculateStats = calculateStats;
	}
	
	public FoodDto addFood(FoodDto foodDto) {
		Food foodModel = FoodMapper.INSTANCE.toFood(foodDto);
		Food food = foodRepository.save(foodModel);
		return FoodMapper.INSTANCE.toFoodDto(food);
	}
	
	public DayDto addDay(DayDto dayDto) {
		Day dayModel = DayMapper.INSTANCE.toDay(dayDto);
		Day day = dayRepository.save(dayModel);
		return DayMapper.INSTANCE.toDayDto(day);
	}
	
	public WeekDto addWeek(WeekDto weekDto) {
		Week weekModel = WeekMapper.INSTANCE.toWeek(weekDto);
		Week week = weekRepository.save(weekModel);
		return WeekMapper.INSTANCE.toWeekDto(week);
	}
	
	public DayDto addFoodsToDay(Long dayId, List<Long> foodIds) {
		Day dayBeingUpdated = dayRepository.findById(dayId).orElseThrow(() -> new RuntimeException ("Day object not found in database in addFoodsToDay method with id = " + dayId));
		List<Food> foodsAddedToDayObject = new ArrayList<>();
		for(Long foodId : foodIds) {
			Food nextFoodObjectAdded = foodRepository.findById(foodId).orElseThrow(() -> new RuntimeException("Food object not found in database in addFoodsToDay method with id = " + foodId));
			foodsAddedToDayObject.add(nextFoodObjectAdded);
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
		}
		calculateStats.calculateTotalDayStats(dayBeingUpdated);	
		dayRepository.save(dayBeingUpdated);
		return DayMapper.INSTANCE.toDayDto(dayBeingUpdated);
	}
	
	public WeekDto addDaysToWeek(Long weekId, List<Long> dayIds) {
		Week weekBeingUpdated = weekRepository.findById(weekId).orElseThrow(() -> new RuntimeException("Week object not found in database in addDaysToWeek method with id = " + weekId));
		List<Day> daysAddedToWeekObject = dayRepository.findAllById(dayIds);
		
		for(Day day : daysAddedToWeekObject) {
			calculateStats.calculateTotalDayStats(day);
			day.setWeek(weekBeingUpdated);
		}
		
		weekBeingUpdated.getDaysInList().addAll(daysAddedToWeekObject);
		getWeekStats(weekId);
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
	
	public FoodDto getFoodStats(Long foodId) {
		Food returnedFoodObject = foodRepository.findById(foodId).orElseThrow(() -> new RuntimeException("Food object not found in database in getFoodStats method with id = " + foodId));
		return FoodMapper.INSTANCE.toFoodDto(returnedFoodObject);
	}
	
	public DayDto getDayStats(Long dayId) {
		Day returnedDayObject = dayRepository.findById(dayId).orElseThrow(() -> new RuntimeException("Day object not found in database in getDayStats method with id = " + dayId));
		calculateStats.calculateTotalDayStats(returnedDayObject);
		
		dayRepository.save(returnedDayObject);
		
		return DayMapper.INSTANCE.toDayDto(returnedDayObject);
	}
	
	public WeekDto getWeekStats(Long weekId) {
		Week returnedWeekObject = weekRepository.findById(weekId).orElseThrow(() -> new RuntimeException("Week object not found in database in getWeekStats method with id = " + weekId));
		
		calculateStats.calculateTotalWeekStats(returnedWeekObject);
		
		weekRepository.save(returnedWeekObject);
		
		return WeekMapper.INSTANCE.toWeekDto(returnedWeekObject);
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
	
	public DayDto deleteDay(Long dayId) {
		Day dayToBeSoftDeleted = dayRepository.findById(dayId).orElseThrow(() -> new RuntimeException("Day object not found in database in deleteDay method with id = " + dayId));
		dayToBeSoftDeleted.setDeleted(true);
		dayRepository.save(dayToBeSoftDeleted);
		return DayMapper.INSTANCE.toDayDto(dayToBeSoftDeleted);
	}
	
	public WeekDto deleteWeek(Long weekId) {
		Week weekToBeSoftDeleted = weekRepository.findById(weekId).orElseThrow(() -> new RuntimeException("Week object not found in database in deleteWeek method with id = " + weekId));
		weekToBeSoftDeleted.setDeleted(true);
		weekRepository.save(weekToBeSoftDeleted);
		return WeekMapper.INSTANCE.toWeekDto(weekToBeSoftDeleted);
	}
}
