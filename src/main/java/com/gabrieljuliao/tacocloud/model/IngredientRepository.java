package com.gabrieljuliao.tacocloud.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
   List<Ingredient> findByType(String type);
}
