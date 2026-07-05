package com.uti.svcmenu.service.impl;

import com.uti.svcmenu.dto.DishRequest;
import com.uti.svcmenu.dto.DishResponse;
import com.uti.svcmenu.exception.ResourceNotfoundException;
import com.uti.svcmenu.mapper.DishMapper;
import com.uti.svcmenu.model.Dish;
import com.uti.svcmenu.repository.DishRepository;
import com.uti.svcmenu.service.DishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final DishMapper dishMapper;

    // 1. POST /api/menu/dishes (Crear un plato)
    @Override
    @Transactional
    public DishResponse createDish(DishRequest request) {
        log.info("Creando un nuevo plato: {}", request.getName());
        Dish dish = dishMapper.toEntity(request);
        Dish savedDish = dishRepository.save(dish);
        log.info("plato creado exitosamente con id: {}", savedDish.getId());
        return dishMapper.toResponse(savedDish);
    }

    // 2. GET /api/menu/dishes (Listar todos los platos)
    @Override
    @Transactional(readOnly = true)
    public List<DishResponse> getAllDishes() {
        log.info("Obteniendo lista de todos los platos");
        return dishRepository.findAll().stream()
                .map(dishMapper::toResponse)
                .collect(Collectors.toList());
    }

    // 3. GET /api/menu/dishes/{id} (Buscar plato por ID)
    @Override
    @Transactional(readOnly = true)
    public DishResponse getDishById(Long id) {
        log.info("Buscando plato con ID: {}", id);
        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new ResourceNotfoundException(
                        "El plato con id " + id + " no existe en el menú."));
        return dishMapper.toResponse(dish);
    }

    // 4. PUT /api/menu/dishes/{id} (Actualizar un plato)
    @Override
    @Transactional
    public DishResponse updateDish(Long id, DishRequest request) {
        log.info("Actualizando plato con ID: {}", id);
        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new ResourceNotfoundException(
                        "El plato con id " + id + " no existe en el menú."));

        dishMapper.updateEntityFromRequest(request, dish);

        Dish updatedDish = dishRepository.save(dish);

        log.info("plato actualizado exitosamente con id: {}", updatedDish.getId());
        return dishMapper.toResponse(updatedDish);

    }

    // 5. DELETE /api/menu/dishes/{id} (Eliminar un plato)
    @Override
    @Transactional
    public void deleteDish(Long id) {
        log.info("Eliminando plato con ID: {}", id);
        if(!dishRepository.existsById(id)){
            throw new ResourceNotfoundException(
                    "libro no encontrado con id: "+ id
            );
        }
        dishRepository.deleteById(id);
        log.info("plato eliminado exitosamente con id: {}", id);
    }
}