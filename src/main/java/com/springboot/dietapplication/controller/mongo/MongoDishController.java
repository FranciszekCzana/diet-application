package com.springboot.dietapplication.controller.mongo;

import com.springboot.dietapplication.model.type.DishType;
import com.springboot.dietapplication.service.mongo.MongoDishService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mongo/dishes")
public class MongoDishController {

    private final MongoDishService dishService;

    public MongoDishController(MongoDishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public List<DishType> getAll() {
        return this.dishService.getAll();
    }

    @GetMapping(path = "/{dishId}")
    public DishType getDishById(@PathVariable("dishId") String dishId) {
        return this.dishService.getDishById(dishId);
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<DishType> insert(@RequestBody DishType dish) {
        this.dishService.insert(dish);
        return ResponseEntity.ok().body(dish);
    }

    @PutMapping(path = "/{dishId}", produces = "application/json")
    ResponseEntity<DishType> update(@RequestBody DishType dish) {
        this.dishService.insert(dish);
        return ResponseEntity.ok().body(dish);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<Void> delete(@PathVariable String id) {
        this.dishService.delete(id);
        return ResponseEntity.ok().build();
    }
}
