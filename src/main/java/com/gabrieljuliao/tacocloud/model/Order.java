package com.gabrieljuliao.tacocloud.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "Taco_Order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;
    private Date placedAt;
    @NotBlank(message = "Name is Required")
    private String name;
    @NotBlank(message = "Street is Required")
    private String street;
    @NotBlank(message = "City is Required")
    private String city;
    @NotBlank(message = "State is Required")
    private String state;
    @NotBlank(message = "Zip Code is Required")
    private String zip;
    @CreditCardNumber
    private String ccNumber;
    @Pattern(
            regexp = "^(0[1-9]|1[0-2])([\\\\/])([1-9][0-9])$",
            message = "Must be Formatted MM/YY")
    private String ccExpiration;
    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    private String ccCVV;

    @ManyToMany(targetEntity = Taco.class)
    private List<Taco> tacos = new ArrayList<>();
    @ManyToOne
    private User user;

    @PrePersist
    void placedAt() {
        this.placedAt = new Date();
    }

    public void addDesign(Taco design) {
        this.tacos.add(design);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Order order = (Order) o;

        return Objects.equals(orderId, order.orderId);
    }

    @Override
    public int hashCode() {
        return 737800560;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + orderId + ", " +
                "placedAt = " + placedAt + ", " +
                "name = " + name + ", " +
                "street = " + street + ", " +
                "city = " + city + ", " +
                "state = " + state + ", " +
                "zip = " + zip + ", " +
                "ccNumber = " + ccNumber + ", " +
                "ccExpiration = " + ccExpiration + ", " +
                "ccCVV = " + ccCVV + ")";
    }
}
