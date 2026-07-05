package com.uti.svcmenu.mapper;

import com.uti.svcmenu.dto.DishRequest;
import com.uti.svcmenu.dto.DishResponse;
import com.uti.svcmenu.model.Category;
import com.uti.svcmenu.model.Dish;
import org.springframework.stereotype.Component;

@Component
public class DishMapper {
    
    public Dish toEntity(DishRequest request) {
        return Dish.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(Category.valueOf(request.getCategory()))
                .price(request.getPrice())
                .available(true)
                .build();
    }

    public DishResponse toResponse(Dish dish) {
        return DishResponse.builder()
                .id(dish.getId())
                .name(dish.getName())
                .description(dish.getDescription())
                .category(dish.getCategory())
                .price(dish.getPrice())
                .available(dish.getAvailable())
                .build();

    }

    public  void updateEntityFromRequest(DishRequest request, Dish dish) {
        dish.setName(request.getName());
        dish.setDescription(request.getDescription());
        dish.setCategory(Category.valueOf(request.getCategory()));
        dish.setPrice(request.getPrice());
        dish.setAvailable(request.getAvailable());

    }
}
