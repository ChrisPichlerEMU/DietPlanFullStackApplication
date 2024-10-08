package com.dietPlan.domain.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Week {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany(mappedBy = "weekId")
    @JsonIgnore
    private List<Day> daysInList;
    
    private List<Long> dayIdsInDayList = new ArrayList<>();
    
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
    
    public Week() {
    	daysInList = new ArrayList<>();
    }
    
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
    
	public List<Day> getDaysInList() {
		return daysInList;
	}

	public void setDaysInList(List<Day> daysInList) {
		this.daysInList = daysInList;
	}
	
	public List<Long> getDayIdsInDayList() {
		return dayIdsInDayList;
	}
	
	public void setDayIdsInDayList(List<Long> dayIdsInDayList) {
		this.dayIdsInDayList = dayIdsInDayList;
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

	public int getCarbRatio() {
		return carbRatio;
	}

	public void setCarbRatio(int carbRatio) {
		this.carbRatio = carbRatio;
	}

	public int getProteinRatio() {
		return proteinRatio;
	}

	public void setProteinRatio(int proteinRatio) {
		this.proteinRatio = proteinRatio;
	}

	public int getFatRatio() {
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
