package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.menu.DayMeal;
import com.springboot.dietapplication.repository.DayMealRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/daymeals")
public class DayMealController {
    private DayMealRepository dayMealRepository;

    public DayMealController(DayMealRepository dayMealRepository) {
        this.dayMealRepository = dayMealRepository;
    }

    @GetMapping
    public List<DayMeal> getAll() {
        return this.dayMealRepository.findAll();
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<DayMeal> insertDayMeal(@RequestBody DayMeal dayMeal) throws NoSuchFieldException {
        dayMealRepository.save(dayMeal);
        return ResponseEntity.ok().body(null);
    }

    @PutMapping(path = "/{dayMealId}", produces = "application/json")
    ResponseEntity<DayMeal> updateDayMeal(@RequestBody DayMeal dayMeal) {
        dayMealRepository.save(dayMeal);
        return ResponseEntity.ok().body(dayMeal);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<DayMeal> deleteDayMeal(@PathVariable String id) {
        dayMealRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}