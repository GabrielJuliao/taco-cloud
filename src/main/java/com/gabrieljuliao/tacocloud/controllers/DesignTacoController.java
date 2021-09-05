package com.gabrieljuliao.tacocloud.controllers;

import com.gabrieljuliao.tacocloud.model.IngredientRepository;
import com.gabrieljuliao.tacocloud.model.Order;
import com.gabrieljuliao.tacocloud.model.Taco;
import com.gabrieljuliao.tacocloud.model.TacoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/design")
@SessionAttributes("order")
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

    @GetMapping
    public String showDesignForm(Model model) {
//        spring data custom jpa
        model.addAttribute("wraps", ingredientRepository.findByType("WRAP"));
        model.addAttribute("proteins", ingredientRepository.findByType("PROTEIN"));
        model.addAttribute("veggies", ingredientRepository.findByType("VEGGIE"));
        model.addAttribute("cheeses", ingredientRepository.findByType("CHEESE"));
        model.addAttribute("sauces", ingredientRepository.findByType("SAUCE"));

        return "design";
    }

    @PostMapping
    public String processDesign(
            @Valid Taco design, Errors errors,
            @ModelAttribute Order order) {

        if (errors.hasErrors()) {
            return "design";
        }

        Taco saved = tacoRepository.save(design);
        order.addDesign(saved);

        return "redirect:/orders/current";
    }
}
