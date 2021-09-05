package com.gabrieljuliao.tacocloud.controllers;

import com.gabrieljuliao.tacocloud.model.Ingredient;
import com.gabrieljuliao.tacocloud.model.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class IngredientByIdConverter implements Converter<String, Optional<Ingredient>> {

  private final IngredientRepository ingredientRepo;

  @Autowired
  public IngredientByIdConverter(IngredientRepository ingredientRepo) {
    this.ingredientRepo = ingredientRepo;
  }
  
  @Override
  public Optional<Ingredient> convert(String id) {
    return ingredientRepo.findById(id);
  }

}
