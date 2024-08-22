package com.dietPlan.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dietPlan.infrastructure.orchestrator.DayOrchestrator;
import com.dietPlan.infrastructure.service.DayService;
import com.dietPlan.web.dto.DayDto;

@RestController
@RequestMapping("/day")
public class DayController {

	private DayService dayService;
	private DayOrchestrator dayOrchestrator;
	
	public DayController(DayService dayService, DayOrchestrator dayOrchestrator) {
		this.dayService = dayService;
		this.dayOrchestrator = dayOrchestrator;
	}
	
	@PostMapping("/addDay")
	public ResponseEntity<DayDto> addDay(@RequestBody DayDto dayDto){
		DayDto savedDayObject = dayService.addDay(dayDto);
		return new ResponseEntity<>(savedDayObject, HttpStatus.CREATED);
	}
	
	@PutMapping("/getDay/{dayId}")
	public ResponseEntity<DayDto> getDayStats(@PathVariable Long dayId){
		DayDto returnedDayObject = dayOrchestrator.getDayStats(dayId);
		return ResponseEntity.ok(returnedDayObject);
	}
	
	@DeleteMapping("/deleteDay/{dayId}")
	public ResponseEntity<DayDto> deleteDay(@PathVariable Long dayId){
		DayDto dayToBeSoftDeleted = dayService.deleteDay(dayId);
		return ResponseEntity.ok(dayToBeSoftDeleted);
	}
	
	@PostMapping("/addFoodsToDay/{dayId}")
	public ResponseEntity<DayDto> addFoodsToDay(@PathVariable Long dayId, @RequestBody List<Long> foodIds){
		DayDto updatedDayObject = dayOrchestrator.addFoodsToDay(dayId, foodIds);
		return new ResponseEntity<>(updatedDayObject, HttpStatus.CREATED);
	}
	
	@PostMapping("/deleteFoodsInDay/{dayId}")
	public ResponseEntity<DayDto> deleteFoodsInDay(@PathVariable Long dayId, @RequestBody List<Long> foodIds){
		DayDto updatedDayObject = dayOrchestrator.deleteFoodsInDay(dayId, foodIds);
		return new ResponseEntity<>(updatedDayObject, HttpStatus.CREATED);
	}
}
