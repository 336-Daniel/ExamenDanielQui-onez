package com.uti.svcmenu.repository;

import com.uti.svcmenu.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface DishRepository extends JpaRepository<Dish, Long> {
}
