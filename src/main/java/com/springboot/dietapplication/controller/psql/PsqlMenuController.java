package com.springboot.dietapplication.controller.psql;

import com.springboot.dietapplication.model.type.MenuSendingType;
import com.springboot.dietapplication.model.type.MenuType;
import com.springboot.dietapplication.service.psql.PsqlMenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/psql/menus")
public class PsqlMenuController {

    private final PsqlMenuService menuService;

    public PsqlMenuController(PsqlMenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public List<MenuType> getAll() {
        return this.menuService.getAll();
    }

    @GetMapping(path = "/{menuId}")
    public MenuType getMenuById(@PathVariable("menuId") long menuId) {
        return this.menuService.getMenuById(menuId);
    }

    @GetMapping(path = "/patient/{patientId}")
    public List<MenuType> getMenusByPatientId(@PathVariable("patientId") long patientId) {
        return this.menuService.getMenusByPatientId(patientId);
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<MenuType> insertMenu(@RequestBody MenuSendingType menuSendingType) {
        return this.menuService.insert(menuSendingType);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<MenuType> deleteMenu(@PathVariable long id) {
        return this.menuService.delete(id);
    }

}
