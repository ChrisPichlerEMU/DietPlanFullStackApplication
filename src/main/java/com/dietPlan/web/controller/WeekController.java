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

import com.dietPlan.infrastructure.orchestrator.WeekOrchestrator;
import com.dietPlan.infrastructure.service.WeekService;
import com.dietPlan.web.dto.WeekDto;

@RestController
@RequestMapping("/week")
public class WeekController {

	private WeekService weekService;
	private WeekOrchestrator weekOrchestrator;
	
	public WeekController(WeekService weekService, WeekOrchestrator weekOrchestrator) {
		this.weekService = weekService;
		this.weekOrchestrator = weekOrchestrator;
	}
	
	@PostMapping("/addWeek")
	public ResponseEntity<WeekDto> addWeek(@RequestBody WeekDto weekDto){
		WeekDto savedWeekObject = weekService.addWeek(weekDto);
		return new ResponseEntity<>(savedWeekObject, HttpStatus.CREATED);
	}
	
	@PutMapping("/getWeek/{weekId}")
	public ResponseEntity<WeekDto> getWeekStats(@PathVariable Long weekId){
		WeekDto returnedWeekObject = weekOrchestrator.getWeekStats(weekId);
		return ResponseEntity.ok(returnedWeekObject);
	}
	
	@DeleteMapping("/deleteWeek/{weekId}")
	public ResponseEntity<WeekDto> deleteWeek(@PathVariable Long weekId){
		WeekDto weekToBeSoftDeleted = weekService.deleteWeek(weekId);
		return ResponseEntity.ok(weekToBeSoftDeleted);
	}
	
	@PostMapping("/addDaysToWeek/{weekId}")
	public ResponseEntity<WeekDto> addDaysToWeek(@PathVariable Long weekId, @RequestBody List<Long> dayIds){
		WeekDto updatedWeekObject = weekOrchestrator.addDaysToWeek(weekId, dayIds);
		return new ResponseEntity<>(updatedWeekObject, HttpStatus.CREATED);
	}
	
	@PostMapping("/deleteDaysInWeek/{weekId}")
	public ResponseEntity<WeekDto> deleteDaysInWeek(@PathVariable Long weekId, @RequestBody List<Long> dayIds){
		WeekDto updatedWeekObject = weekOrchestrator.deleteDaysInWeek(weekId, dayIds);
		return new ResponseEntity<>(updatedWeekObject, HttpStatus.CREATED);
	}
}
