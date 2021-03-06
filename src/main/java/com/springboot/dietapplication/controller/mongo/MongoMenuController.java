package com.springboot.dietapplication.controller.mongo;

import com.springboot.dietapplication.model.mongo.menu.MongoMenu;
import com.springboot.dietapplication.model.type.MenuSendingType;
import com.springboot.dietapplication.model.type.MenuType;
import com.springboot.dietapplication.service.mongo.MongoMenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mongo/menus")
public class MongoMenuController {

    private final MongoMenuService menuService;

    public MongoMenuController(MongoMenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public List<MenuType> getAll() {
        return this.menuService.getAll();
    }

    @GetMapping(path = "/{menuId}")
    public MenuType getMenuById(@PathVariable("menuId") String menuId) {
        return this.menuService.getMenuById(menuId);
    }

    @GetMapping(path = "/patient/{patientId}")
    public List<MenuType> getMenusByPatientId(@PathVariable("patientId") String patientId) {
        return this.menuService.getMenusByPatientId(patientId);
    }
    
    @PostMapping(produces = "application/json")
    ResponseEntity<MenuType> insertMenu(@RequestBody MenuSendingType menuSendingType) {
        return this.menuService.insert(menuSendingType);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<MenuType> deleteMenu(@PathVariable String id) {
        return this.menuService.delete(id);
    }

}
