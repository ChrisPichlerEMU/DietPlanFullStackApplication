package com.dietPlan.infrastructure.service;

import org.springframework.stereotype.Service;

import com.dietPlan.domain.model.Day;
import com.dietPlan.domain.model.Food;
import com.dietPlan.domain.model.Week;

@Service
public class CalculateStats {
	public void calculateTotalDayStats(Day day) {	
		if(day == null)
			throw new IllegalArgumentException("Day cannot be null in calculateTotalDayStats method in CalculateStats class.");
		
		int totalCalories = 0, totalProtein = 0, totalCarbs = 0, totalSugar = 0, totalFat = 0, totalSaturatedFat = 0, totalSodium = 0, totalPotassium = 0;
		for(Food food : day.getFoods()) {
			if(food.isDeleted())
				continue;
			totalCalories += food.getCalories() * food.getMultiplier();
			totalProtein += food.getProtein() * food.getMultiplier();
			totalCarbs += food.getCarbs() * food.getMultiplier();
			totalSugar += food.getSugar() * food.getMultiplier();
			totalFat += food.getFat() * food.getMultiplier();
			totalSaturatedFat += food.getSaturatedFat() * food.getMultiplier();
			totalSodium += food.getSodium() * food.getMultiplier();
			totalPotassium += food.getPotassium() * food.getMultiplier();
		}
		
		day.setTotalCalories(totalCalories);
		day.setTotalProtein(totalProtein);
		day.setTotalCarbs(totalCarbs);
		day.setTotalSugar(totalSugar);
		day.setTotalFat(totalFat);
		day.setTotalSaturatedFat(totalSaturatedFat);
		day.setTotalSodium(totalSodium);
		day.setTotalPotassium(totalPotassium);
		
		double totalCarbsForRatioCalculation = totalCarbs * 4;
		double totalProteinForRatioCalculation = totalProtein * 4;
		double totalFatForRatioCalculation = totalFat * 9;
		double totalCaloriesForRatioCalculation = totalCarbsForRatioCalculation + totalProteinForRatioCalculation + totalFatForRatioCalculation;
		
		int carbRatio = (int)((totalCarbsForRatioCalculation / totalCaloriesForRatioCalculation) * 100);
		int proteinRatio = (int)((totalProteinForRatioCalculation / totalCaloriesForRatioCalculation) * 100);
		int fatRatio = (int)((totalFatForRatioCalculation / totalCaloriesForRatioCalculation) * 100);
		
		while(carbRatio + proteinRatio + fatRatio < 100) {
			carbRatio = (carbRatio >= proteinRatio && carbRatio >= fatRatio) ? carbRatio + 1 : carbRatio;
			proteinRatio = (proteinRatio > carbRatio && proteinRatio >= fatRatio) ? proteinRatio + 1 : proteinRatio;
			fatRatio = (fatRatio > carbRatio && fatRatio > proteinRatio) ? fatRatio + 1 : fatRatio;
		
		}
		
		day.setCarbRatio(carbRatio);
		day.setProteinRatio(proteinRatio);
		day.setFatRatio(fatRatio);
	}
	
	public void calculateTotalWeekStats(Week week) {
		if(week == null)
			throw new IllegalArgumentException("Day cannot be null in calculateTotalDayStats method in CalculateStats class.");
		
		int totalCalories = 0, totalProtein = 0, totalCarbs = 0, totalSugar = 0, totalFat = 0, totalSaturatedFat = 0, totalSodium = 0, totalPotassium = 0;
		
		for(Day day : week.getDaysInList()) {
			if(day.getFoods() != null) {
				calculateTotalDayStats(day);
				totalCalories += day.getTotalCalories();
				totalProtein += day.getTotalProtein();
				totalCarbs += day.getTotalCarbs();
				totalSugar += day.getTotalSugar();
				totalFat += day.getTotalFat();
				totalSaturatedFat += day.getTotalSaturatedFat();
				totalSodium += day.getTotalSodium();
				totalPotassium += day.getTotalPotassium();
			}
		}
		
		week.setTotalCalories(totalCalories);
		week.setTotalProtein(totalProtein);
		week.setTotalCarbs(totalCarbs);
		week.setTotalSugar(totalSugar);
		week.setTotalFat(totalFat);
		week.setTotalSaturatedFat(totalSaturatedFat);
		week.setTotalSodium(totalSodium);
		week.setTotalPotassium(totalPotassium);
		
		double totalCarbsForRatioCalculation = totalCarbs * 4;
		double totalProteinForRatioCalculation = totalProtein * 4;
		double totalFatForRatioCalculation = totalFat * 9;
		double totalCaloriesForRatioCalculation = totalCarbsForRatioCalculation + totalProteinForRatioCalculation + totalFatForRatioCalculation;
		
		int carbRatio = (int)((totalCarbsForRatioCalculation / totalCaloriesForRatioCalculation) * 100);
		int proteinRatio = (int)((totalProteinForRatioCalculation / totalCaloriesForRatioCalculation) * 100);
		int fatRatio = (int)((totalFatForRatioCalculation / totalCaloriesForRatioCalculation) * 100);
		
		while(carbRatio + proteinRatio + fatRatio < 100) {
			carbRatio = (carbRatio >= proteinRatio && carbRatio >= fatRatio) ? carbRatio + 1 : carbRatio;
			proteinRatio = (proteinRatio > carbRatio && proteinRatio >= fatRatio) ? proteinRatio + 1 : proteinRatio;
			fatRatio = (fatRatio > carbRatio && fatRatio > proteinRatio) ? fatRatio + 1 : fatRatio;
		}
		
		week.setCarbRatio(carbRatio);
		week.setProteinRatio(proteinRatio);
		week.setFatRatio(fatRatio);
	}
}
