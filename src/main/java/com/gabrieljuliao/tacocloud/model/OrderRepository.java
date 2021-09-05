package com.gabrieljuliao.tacocloud.model;

import com.gabrieljuliao.tacocloud.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
  
}
