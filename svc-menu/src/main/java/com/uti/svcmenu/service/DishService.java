package com.uti.svcmenu.service;

import com.uti.svcmenu.dto.DishRequest;
import com.uti.svcmenu.dto.DishResponse;

import java.util.List;

public interface DishService {

    // (Crear un plato)
    DishResponse createDish(DishRequest request);

    // (Listar todos los platos)
    List<DishResponse> getAllDishes();

    //  (Buscar plato por ID)
    DishResponse getDishById(Long id);

    // (Actualizar un plato)
    DishResponse updateDish(Long id, DishRequest request);

    //  (Eliminar un plato)
    void deleteDish(Long id);
}