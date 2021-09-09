package com.gabrieljuliao.tacocloud.controllers;

import com.gabrieljuliao.tacocloud.model.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping(value = "/design", produces = {"application/json", "text/xml"})
//@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;
    private final TacoRepository tacoRepository;

    public DesignTacoController(IngredientRepository ingredientRepository, TacoRepository tacoRepository) {
        this.ingredientRepository = ingredientRepository;
        this.tacoRepository = tacoRepository;
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Taco>> get(@PathVariable("id") Long id) {
        Optional<Taco> taco = tacoRepository.findById(id);
        if (taco.isPresent()) {
            return new ResponseEntity<>(taco, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/recent")
    public Iterable<Taco> recentTacos() {
        PageRequest page = PageRequest.of(
                0, 12, Sort.by("createdAt").descending());
        return tacoRepository.findAll(page).getContent();
    }

    @GetMapping("/ingredient")
    public Iterable<Ingredient> getIngredients() {
        return ingredientRepository.findAll();
    }

    @PostMapping(consumes = {"application/json", "text/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    public Taco processTaco(@RequestBody Taco taco) {
        return tacoRepository.save(taco);
    }

    @PutMapping()
    public Taco updateTaco(@RequestBody Taco taco) {
        return tacoRepository.save(taco);
    }

    @DeleteMapping(consumes = {"application/json", "text/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody Taco taco) {
        try {
            tacoRepository.delete(taco);
        } catch (EmptyResultDataAccessException e) {
        }

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id) {
        try {
            tacoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
        }

    }

//    @GetMapping
//    public String showDesignForm(Model model) {
//        model.addAttribute("wraps", ingredientRepository.findByType("WRAP"));
//        model.addAttribute("proteins", ingredientRepository.findByType("PROTEIN"));
//        model.addAttribute("veggies", ingredientRepository.findByType("VEGGIE"));
//        model.addAttribute("cheeses", ingredientRepository.findByType("CHEESE"));
//        model.addAttribute("sauces", ingredientRepository.findByType("SAUCE"));
//
//        return "design";
//    }
//
//    @PostMapping
//    public String processDesign(
//            @Valid Taco design, Errors errors,
//            @ModelAttribute Order order) {
//
//        if (errors.hasErrors()) {
//            return "design";
//        }
//
//        Taco saved = tacoRepository.save(design);
//        order.addDesign(saved);
//
//        return "redirect:/orders/current";
//    }
}
