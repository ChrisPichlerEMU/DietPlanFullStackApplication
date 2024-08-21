package com.dietPlan.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Day {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToMany
	@JoinTable(
			name = "day_food",
			joinColumns = @JoinColumn(name = "day_id"),
			inverseJoinColumns = @JoinColumn(name = "food_id")
	)
	private List<Food> foods;
	
	@ManyToOne
	@JoinColumn(name = "week_id")
	@JsonBackReference
	private Week week;
	
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
	
	public Day() {
		this.foods = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public List<Food> getFoods() {
		return foods;
	}

	public void setFoods(List<Food> foods) {
		this.foods = foods;
	}
	
	public Week getWeek() {
		return week;
	}
	
	public void setWeek(Week week) {
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
