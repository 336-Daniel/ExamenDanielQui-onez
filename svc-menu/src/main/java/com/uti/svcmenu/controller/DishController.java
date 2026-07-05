package com.uti.svcmenu.controller;

import com.uti.svcmenu.dto.DishRequest;
import com.uti.svcmenu.dto.DishResponse;
import com.uti.svcmenu.service.DishService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu/dishes")
@RequiredArgsConstructor
@Slf4j
public class DishController {

    private final DishService dishService;

    // 1. POST /api/menu/dishes (Crear plato)
    @PostMapping
    public ResponseEntity<DishResponse> createDish(@Valid @RequestBody DishRequest request) {
        log.info("POST /api/menu/dishes Creando el plato: {}", request.getName());
        DishResponse createdDish = dishService.createDish(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDish);
    }

    // 2. GET /api/menu/dishes (Listar todos)
    @GetMapping
    public ResponseEntity<List<DishResponse>> getAllDishes() {
        log.info("GET /api/menu/dishes - Obteniendo todos los platos");
        return ResponseEntity.ok(dishService.getAllDishes());
    }

    // 3. GET /api/menu/dishes/{id} (Buscar por ID)
    @GetMapping("/{id}")
    public ResponseEntity<DishResponse> getDishById(@PathVariable Long id) {
        log.info("GET /api/menu/dishes/{} - Obteniedo plato por id: {} ", id,id);
        return ResponseEntity.ok(dishService.getDishById(id));
    }

    // 4. PUT /api/menu/dishes/{id} (Actualizar)
    @PutMapping("/{id}")
    public ResponseEntity<DishResponse> updateDish(@PathVariable Long id, @Valid @RequestBody DishRequest request) {
        log.info("PUT /api/menu/dishes/{} - Plato actualizado", id);
        return ResponseEntity.ok(dishService.updateDish(id,request));
    }

    // 5. DELETE /api/menu/dishes/{id} (Eliminar -> 204 No Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable Long id) {
        log.info("DELETE /api/menu/dishes/{} - Plato borrado", id);
        dishService.deleteDish(id);
        return ResponseEntity.noContent().build();
    }
}