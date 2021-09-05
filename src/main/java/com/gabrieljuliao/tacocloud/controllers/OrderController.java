package com.gabrieljuliao.tacocloud.controllers;

import com.gabrieljuliao.tacocloud.model.Order;
import com.gabrieljuliao.tacocloud.model.OrderRepository;
import com.gabrieljuliao.tacocloud.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;


@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
  
  private OrderRepository orderRepository;

  public OrderController(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }
  
  @GetMapping("/current")
  public String orderForm(@AuthenticationPrincipal User user, Model model)
  {
    model.addAttribute("order", user);
    return "/orders/orderForm";
  }

  @PostMapping
  public String processOrder(@Valid Order order, Errors errors,
                             SessionStatus sessionStatus, @AuthenticationPrincipal User user) {
    if (errors.hasErrors()) {
      return "orders/orderForm";
    }
    order.setUser(user);
    orderRepository.save(order);
    sessionStatus.setComplete();
    
    return "redirect:/";
  }

}
