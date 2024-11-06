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

import com.dietPlan.infrastructure.service.FoodService;
import com.dietPlan.web.dto.FoodDto;

@RestController
@RequestMapping("/food")
public class FoodController {
	
	private FoodService foodService;
	
	public FoodController(FoodService foodService) {
		this.foodService = foodService;
	}
	//http://localhost:8080/food/addFood
	@PostMapping("/addFood")
	public ResponseEntity<FoodDto> addFood(@RequestBody FoodDto foodDto){
		FoodDto savedFoodObject = foodService.addFood(foodDto);
		return new ResponseEntity<>(savedFoodObject, HttpStatus.CREATED);
	}
	//http://localhost:8080/food/getFood/ID
	@GetMapping("/getFood/{foodId}")
	public ResponseEntity<FoodDto> getFoodStats(@PathVariable Long foodId){
		FoodDto returnedFoodObject = foodService.getFoodStats(foodId);
		return ResponseEntity.ok(returnedFoodObject);
	}
	
	@PutMapping("/editFood/{foodId}")
	public ResponseEntity<FoodDto> editFood(@PathVariable Long foodId, @RequestBody FoodDto foodDto){
		FoodDto foodToBeEdited = foodService.editFood(foodId, foodDto);
		return ResponseEntity.ok(foodToBeEdited);
	}
	
	@DeleteMapping("/deleteFood/{foodId}")
	public ResponseEntity<FoodDto> deleteFood(@PathVariable Long foodId){
		FoodDto foodToBeSoftDeleted = foodService.deleteFood(foodId);
		return ResponseEntity.ok(foodToBeSoftDeleted);
	}
}
