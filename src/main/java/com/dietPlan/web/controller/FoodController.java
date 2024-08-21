package com.dietPlan.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dietPlan.infrastructure.service.DietService;
import com.dietPlan.web.dto.FoodDto;

@RestController
@RequestMapping("/food")
public class FoodController {
	
	private DietService dietService;
	
	public FoodController(DietService dietService) {
		this.dietService = dietService;
	}
	
	@PostMapping("/addFood")
	public ResponseEntity<FoodDto> addFood(@RequestBody FoodDto foodDto){
		FoodDto savedFoodObject = dietService.addFood(foodDto);
		return new ResponseEntity<>(savedFoodObject, HttpStatus.CREATED);
	}
	
	@GetMapping("/getFood/{foodId}")
	public ResponseEntity<FoodDto> getFoodStats(@PathVariable Long foodId){
		FoodDto returnedFoodObject = dietService.getFoodStats(foodId);
		return ResponseEntity.ok(returnedFoodObject);
	}
	
	@PutMapping("/editFood/{foodId}")
	public ResponseEntity<FoodDto> editFood(@PathVariable Long foodId, @RequestBody FoodDto foodDto){
		FoodDto foodToBeEdited = dietService.editFood(foodId, foodDto);
		return ResponseEntity.ok(foodToBeEdited);
	}
	
	@DeleteMapping("/deleteFood/{foodId}")
	public ResponseEntity<FoodDto> deleteFood(@PathVariable Long foodId){
		FoodDto foodToBeSoftDeleted = dietService.deleteFood(foodId);
		return ResponseEntity.ok(foodToBeSoftDeleted);
	}
}
