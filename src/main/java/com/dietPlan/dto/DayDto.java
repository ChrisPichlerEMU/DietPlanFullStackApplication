package com.dietPlan.dto;

import java.util.List;

import com.dietPlan.models.Food;
import com.dietPlan.models.Week;

public class DayDto {
	private List<FoodDto> foods;
	
	private WeekDto week;
		
	private int totalCalories;
	private int totalProtein;
	private int totalCarbs;
	private int totalSugar;
	private int totalFat;
	private int totalSaturatedFat;
	private int totalSodium;
	private int totalPotassium;
	private int carbRatio;
	private int proteinRatio;
	private int fatRatio;
	private boolean isDeleted = false;

	public List<FoodDto> getFoods() {
		return foods;
	}

	public void setFoods(List<FoodDto> foods) {
		this.foods = foods;
	}
	
	public WeekDto getWeek() {
		return week;
	}
	
	public void setWeek(WeekDto week) {
		this.week = week;
	}

	public int getTotalCalories() {
		return totalCalories;
	}

	public void setTotalCalories(int totalCalories) {
		this.totalCalories = totalCalories;
	}

	public int getTotalProtein() {
		return totalProtein;
	}

	public void setTotalProtein(int totalProtein) {
		this.totalProtein = totalProtein;
	}

	public int getTotalCarbs() {
		return totalCarbs;
	}

	public void setTotalCarbs(int totalCarbs) {
		this.totalCarbs = totalCarbs;
	}

	public int getTotalSugar() {
		return totalSugar;
	}

	public void setTotalSugar(int totalSugar) {
		this.totalSugar = totalSugar;
	}

	public int getTotalFat() {
		return totalFat;
	}

	public void setTotalFat(int totalFat) {
		this.totalFat = totalFat;
	}

	public int getTotalSaturatedFat() {
		return totalSaturatedFat;
	}

	public void setTotalSaturatedFat(int totalSaturatedFat) {
		this.totalSaturatedFat = totalSaturatedFat;
	}

	public int getTotalSodium() {
		return totalSodium;
	}

	public void setTotalSodium(int totalSodium) {
		this.totalSodium = totalSodium;
	}

	public int getTotalPotassium() {
		return totalPotassium;
	}

	public void setTotalPotassium(int totalPotassium) {
		this.totalPotassium = totalPotassium;
	}

	public double getCarbRatio() {
		return carbRatio;
	}

	public void setCarbRatio(int carbRatio) {
		this.carbRatio = carbRatio;
	}

	public double getProteinRatio() {
		return proteinRatio;
	}

	public void setProteinRatio(int proteinRatio) {
		this.proteinRatio = proteinRatio;
	}

	public double getFatRatio() {
		return fatRatio;
	}

	public void setFatRatio(int fatRatio) {
		this.fatRatio = fatRatio;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
