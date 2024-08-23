package com.dietPlan.web.dto;

public class FoodDto {
	private Long id;
	private String name;
    private int calories;
    private int protein;
    private int carbs;
    private int sugar;
    private int fat;
    private int saturatedFat;
    private int sodium;
    private int potassium;
    private double multiplier = 1.0;
    private boolean isDeleted = false;
    
    public Long getId() {
    	return id;
    }
    
    public void setId(Long id) {
    	this.id = id;    
    }
    
    public String getName() {
    	return name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public int getSugar() {
        return sugar;
    }

    public void setSugar(int sugar) {
        this.sugar = sugar;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getSaturatedFat() {
        return saturatedFat;
    }

    public void setSaturatedFat(int saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public int getPotassium() {
        return potassium;
    }

    public void setPotassium(int potassium) {
        this.potassium = potassium;
    }
    
    public double getMultiplier() {
    	return multiplier;
    }
    
    public void setMultiplier(double multiplier) {
    	this.multiplier = multiplier;
    }

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
